package GUI;

import javax.swing.*;
import Controller.Controller;
import model.UtenteIscritto;

public class OrganizzatoreGUI {
    private JPanel panel1;
    private JComboBox<String> comboBox1;
    private JButton promuoviButton;
    private JButton esciButton;
    private JButton AvviaButton;
    private JButton modificaDateButton;
    private JButton maxComponentiTeamButton;
    private JButton maxIscrittiButton;
    private JButton classificaButton;
    public JFrame frame;
    private Controller controller;

    public OrganizzatoreGUI(JFrame frameChiamante, Controller controller) {
        this.controller = controller;

        frame = new JFrame("Organizzatore");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //Aggiunge gli utenti nella comboBox
        for (UtenteIscritto u : controller.getUtentiRegistrati()) {
            comboBox1.addItem(u.getNome());
        }

        //Promuove a giudice gli utenti
        promuoviButton.addActionListener(e -> {
            int selectedIndex = comboBox1.getSelectedIndex();
            if (selectedIndex >= 0) {
                UtenteIscritto selezionato = controller.getUtentiRegistrati().get(selectedIndex);
                controller.promuoviAGiudice(selezionato);
                JOptionPane.showMessageDialog(frame, selezionato.getNome() + " è stato promosso a giudice.");
            } else {
                JOptionPane.showMessageDialog(frame, "Seleziona un utente da promuovere.");
            }
        });

        //Avvio dell'evento e impostazione automatica delle date
        AvviaButton.addActionListener(e -> {
            controller.avviaIscrizioni();
            JOptionPane.showMessageDialog(frame, "L'Hackaton è iniziato!\nLe date sono state impostate automaticamente.");
        });


        modificaDateButton.addActionListener(e -> {
            new ModificaDateGUI(frame, controller);
        });

        //Pulsante per cambiare il numero massimo di iscritti all'evento
        maxIscrittiButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Inserisci il numero massimo di iscritti:", controller.getHackaton().getNumeroMaxIscritti());
            if (input != null) {
                try {
                    int maxIscritti = Integer.parseInt(input);
                    if (maxIscritti > 0) {
                        controller.setNumeroMaxIscritti(maxIscritti);
                        JOptionPane.showMessageDialog(frame, "Numero massimo iscritti aggiornato a " + maxIscritti);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Inserisci un numero positivo.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Inserisci un numero valido.");
                }
            }
        });

        //Per inserire il numero massimo di componenti per ogni team
        maxComponentiTeamButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Inserisci il numero massimo componenti per team:", controller.getHackaton().getComponentMaxTeam());
            if (input != null) {
                try {
                    int maxComponenti = Integer.parseInt(input);
                    if (maxComponenti > 0) {
                        controller.setComponentMaxTeam(maxComponenti);
                        JOptionPane.showMessageDialog(frame, "Numero massimo componenti team aggiornato a " + maxComponenti);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Inserisci un numero positivo.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Inserisci un numero valido.");
                }
            }
        });


        esciButton.addActionListener(e -> {
            frameChiamante.setVisible(true);
            frame.dispose();
        });

        classificaButton.addActionListener(e -> {
            new ClassificaGUI(frame, controller);
        });
    }
}
