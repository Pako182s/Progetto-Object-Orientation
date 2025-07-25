package GUI;

import Controller.Controller;
import model.Team;
import model.UtenteIscritto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Classe GUI per la gestione dei team nell'hackathon.
 * Permette agli utenti di creare team, invitare altri partecipanti,
 * accettare inviti e gestire la composizione del proprio team.
 *
 */
public class TeamGUI {
    private JPanel panel1;
    private JButton creaTeamButton;
    private JButton invitaNelTeamButton;
    private JComboBox<String> comboBox1;
    private JLabel TeamLabel;
    private JButton accettaInvitoButton;
    private JComboBox<String> comboBox2;
    private JButton ESCIButton;
    public JFrame frame;

    private Controller controller;
    private JFrame frameChiamante;

    /**
     * Costruttore della classe TeamGUI.
     * Inizializza l'interfaccia per la gestione dei team e configura
     * i listener per tutte le operazioni relative ai team.
     *
     * @param frameChiamante il frame parent da cui è stata chiamata questa GUI
     * @param controller il controller per la gestione della logica dei team
     */
    public TeamGUI(JFrame frameChiamante, Controller controller) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;

        frame = new JFrame("TeamGUI");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();

        aggiornaStatoUI();

        //Crea il team
        creaTeamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeTeam = JOptionPane.showInputDialog(frame, "Inserisci il nome del team:");
                //crea correttamente il team
                if (nomeTeam != null && !nomeTeam.trim().isEmpty()) {
                    boolean creato = controller.creaTeam(nomeTeam.trim(), controller.getUtenteAutenticato());
                    if (creato) {
                        JOptionPane.showMessageDialog(frame, "Team creato con successo!");
                        //altrimenti significa che gia fai parte di un team e non puoi accedere ad un altro team
                    } else {
                        JOptionPane.showMessageDialog(frame, "Non puoi creare un altro team, già ne hai uno.");
                    }
                    aggiornaStatoUI();
                }
            }
        });

        //Pulsante per invitare altri utenti nel team
        invitaNelTeamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Team team = controller.getTeamDiUtente(controller.getUtenteAutenticato());
                if (team != null && team.getMembri().size() >= controller.getHackaton().getComponentMaxTeam()) {
                    JOptionPane.showMessageDialog(frame, "Il tuo team ha già il numero massimo di componenti.");
                    return;
                }

                String nomeUtenteSelezionato = (String) comboBox1.getSelectedItem();
                if (nomeUtenteSelezionato == null) {
                    JOptionPane.showMessageDialog(frame, "Seleziona un utente da invitare.");
                    return;
                }

                UtenteIscritto utenteSelezionato = trovaUtentePerNome(nomeUtenteSelezionato);
                if (utenteSelezionato == null) {
                    JOptionPane.showMessageDialog(frame, "Utente non trovato.");
                    return;
                }

                boolean inviato = controller.invitaNelTeam(controller.getUtenteAutenticato(), utenteSelezionato);
                if (inviato) {
                    JOptionPane.showMessageDialog(frame, "Invito inviato a " + nomeUtenteSelezionato);
                } else {
                    JOptionPane.showMessageDialog(frame, "Impossibile invitare questo utente.");
                }
            }
        });

        //Pulsante per accettare l'invito ed entrare a far parte di un team
        accettaInvitoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeTeamSelezionato = (String) comboBox2.getSelectedItem();
                if (nomeTeamSelezionato == null) {
                    JOptionPane.showMessageDialog(frame, "Seleziona un team da cui accettare l'invito.");
                    return;
                }

                Team teamSelezionato = trovaTeamPerNome(nomeTeamSelezionato);
                if (teamSelezionato == null) {
                    JOptionPane.showMessageDialog(frame, "Team non trovato.");
                    return;
                }

                if (teamSelezionato.getMembri().size() >= controller.getHackaton().getComponentMaxTeam()) {
                    JOptionPane.showMessageDialog(frame, "Il team è già al completo. Non puoi accettare l'invito.");
                    return;
                }

                boolean accettato = controller.accettaInvito(controller.getUtenteAutenticato(), teamSelezionato);
                if (accettato) {
                    JOptionPane.showMessageDialog(frame, "Sei entrato nel team " + nomeTeamSelezionato);
                } else {
                    JOptionPane.showMessageDialog(frame, "Impossibile accettare l'invito.");
                }
                aggiornaStatoUI();
            }
        });

        ESCIButton.addActionListener(e -> {
            frame.dispose();
            frameChiamante.setVisible(true);
        });
    }

    /**
     * Metodo privato per aggiornare lo stato dell'interfaccia utente.
     * Aggiorna le etichette, abilita/disabilita i pulsanti e popola le combo box
     * in base allo stato attuale dell'utente e del suo team.
     */
    private void aggiornaStatoUI() {
        // aggiorna la scritta del team
        if (controller.utenteHaTeam(controller.getUtenteAutenticato())) {
            Team team = controller.getTeamDiUtente(controller.getUtenteAutenticato());
            TeamLabel.setText("Il tuo team è: " + team.getNomeTeam());
            creaTeamButton.setEnabled(false);
            accettaInvitoButton.setEnabled(false);
            comboBox2.setEnabled(false);
        } else {
            TeamLabel.setText("Non fai parte di nessun team");
            creaTeamButton.setEnabled(true);
            accettaInvitoButton.setEnabled(true);
            comboBox2.setEnabled(true);
        }

        //aggiorna comboBox1 (utenti da invitare)
        comboBox1.removeAllItems();
        for (UtenteIscritto utente : controller.getUtentiRegistrati()) {
            if (!utente.equals(controller.getUtenteAutenticato()) && !controller.utenteHaTeam(utente) && "utente".equalsIgnoreCase(utente.getRuolo())) {
                comboBox1.addItem(utente.getNome());
            }
        }

        //aggiorna comboBox2 (inviti ricevuti)
        comboBox2.removeAllItems();
        List<Team> inviti = controller.getInvitiPerUtente(controller.getUtenteAutenticato());
        if (inviti != null) {
            for (Team t : inviti) {
                comboBox2.addItem(t.getNomeTeam());
            }
        }
    }

    /**
     * Metodo di utilità per trovare un utente per nome.
     *
     * @param nome il nome dell'utente da cercare
     * @return l'oggetto UtenteIscritto se trovato, null altrimenti
     */
    private UtenteIscritto trovaUtentePerNome(String nome) {
        for (UtenteIscritto u : controller.getUtentiRegistrati()) {
            if (u.getNome().equals(nome)) return u;
        }
        return null;
    }

    /**
     * Metodo di utilità per trovare un team per nome.
     * Cerca prima negli inviti ricevuti, poi nel team dell'utente corrente.
     *
     * @param nomeTeam il nome del team da cercare
     * @return l'oggetto Team se trovato, null altrimenti
     */
    private Team trovaTeamPerNome(String nomeTeam) {
        List<Team> inviti = controller.getInvitiPerUtente(controller.getUtenteAutenticato());
        if (inviti != null) {
            for (Team t : inviti) {
                if (t.getNomeTeam().equals(nomeTeam)) return t;
            }
        }
        Team mioTeam = controller.getTeamDiUtente(controller.getUtenteAutenticato());
        if (mioTeam != null && mioTeam.getNomeTeam().equals(nomeTeam)) return mioTeam;

        return null;
    }
}