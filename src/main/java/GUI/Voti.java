package GUI;

import Controller.Controller;
import model.Team;
import model.UtenteIscritto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Classe GUI per l'assegnazione dei voti ai team da parte dei giudici.
 * Permette ai giudici di selezionare un team e assegnare un voto numerico
 * con validazione dell'input e controllo dei voti duplicati.
 */
public class Voti extends JFrame {
    private JPanel panel1;
    private JComboBox<String> comboBox1;
    private JButton assegnaVotoButton;

    private Controller controller;
    private UtenteIscritto giudice;

    /**
     * Costruttore della classe Voti.
     * Inizializza l'interfaccia per l'assegnazione dei voti, popola la combo box
     * con i team disponibili e configura il meccanismo di votazione.
     *
     * @param parent il frame parent di questa finestra
     * @param controller il controller per la gestione dei voti
     */
    public Voti(JFrame parent, Controller controller) {
        this.controller = controller;
        this.giudice = controller.getUtenteAutenticato();

        setTitle("Assegna Voto");
        setContentPane(panel1);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        pack();
        setVisible(true);

        List<Team> teams = controller.getListaTeam();
        for (Team team : teams) {
            comboBox1.addItem(team.getNomeTeam());
        }

        assegnaVotoButton.addActionListener(e -> {
            String nomeTeam = (String) comboBox1.getSelectedItem();
            Team teamSelezionato = teams.stream()
                    .filter(t -> t.getNomeTeam().equals(nomeTeam))
                    .findFirst().orElse(null);

            if (teamSelezionato == null) {
                JOptionPane.showMessageDialog(this, "Team non valido.");
                return;
            }

            String input = JOptionPane.showInputDialog(this, "Inserisci voto (1-10):");
            try {
                int voto = Integer.parseInt(input);
                if (voto < 1 || voto > 10) throw new NumberFormatException();

                boolean successo = controller.assegnaVoto(teamSelezionato, giudice, voto);
                if (successo) {
                    JOptionPane.showMessageDialog(this, "Voto assegnato con successo.");
                } else {
                    JOptionPane.showMessageDialog(this, "Hai già votato questo team!");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Inserisci un numero da 1 a 10.");
            }
        });
    }
}