package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controller.Controller;
import model.UtenteIscritto;

public class UtenteGUI {
    private Controller controller;
    private JPanel panel1;
    private JButton esciButton;
    private JLabel benvenutoLabel;
    private JButton infoButton;
    private JButton teamButton;
    private JButton documentoButton;
    private JButton classificaButton;
    private JButton visualizzaTracciaButton;
    public JFrame frame;

    private JFrame frameChiamante;

    public UtenteGUI(JFrame frameChiamante, Controller controller, String nomeUtente) {
        this.frameChiamante = frameChiamante;
        this.controller = controller;

        frame = new JFrame("UtenteGUI");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        benvenutoLabel.setText("Benvenuto, " + nomeUtente + "!");

        frame.pack();

        esciButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });

        //Visualizza le informazioni dell'evento
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HackatonGUI hackatonGUI = new HackatonGUI(frameChiamante, controller);
                hackatonGUI.frame.setVisible(true);

            }
        });


        teamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UtenteIscritto utente = controller.getUtenteAutenticato();
                if (utente == null) {
                    JOptionPane.showMessageDialog(frame, "Errore: nessun utente autenticato.");
                    return;
                }
                TeamGUI teamGUI = new TeamGUI(frame, controller);
                teamGUI.frame.setVisible(true);
                frame.setVisible(false);
            }
        });


        documentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UtenteIscritto utente = controller.getUtenteAutenticato();
                if (utente == null) {
                    JOptionPane.showMessageDialog(frame, "Errore: nessun utente autenticato.");
                    return;
                }

                if (!controller.utenteHaTeam(utente)) {
                    JOptionPane.showMessageDialog(frame, "Devi essere in un team per accedere al documento.");
                    return;
                }

                if (controller.getHackaton().getDataInizio() == null || controller.getHackaton().getDataInizio().after(new java.util.Date())) {
                    JOptionPane.showMessageDialog(frame, "L'evento non è ancora iniziato.");
                    return;
                }

                DocumentoUtente docGUI = new DocumentoUtente(frame, controller);
                docGUI.frame.setVisible(true);

            }
        });

        classificaButton.addActionListener(e -> {
            new ClassificaGUI(frame, controller);
        });

        visualizzaTracciaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String traccia = controller.getProblemaGiudice();

                if (traccia == null || traccia.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame,
                            "Nessuna traccia disponibile al momento.",
                            "Traccia non disponibile",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JTextArea textArea = new JTextArea(traccia);
                    textArea.setEditable(false);
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);

                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new java.awt.Dimension(400, 250));

                    JOptionPane.showMessageDialog(frame,
                            scrollPane,
                            "Traccia assegnata dal giudice",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

    }
}
