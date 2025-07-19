package GUI;

import Controller.Controller;

import javax.swing.*;

/**
 * Classe GUI per la modifica del problema dell'hackathon.
 * Permette ai giudici di visualizzare e modificare il testo del problema
 * che sarà presentato ai team partecipanti.
 *

 */
public class ModificaProblema {
    private JButton salvaButton;
    private JTextArea ProblemaTextArea;
    private JPanel panel1;

    private JFrame frame;

    /**
     * Costruttore della classe ModificaProblema.
     * Inizializza l'interfaccia per la modifica del problema dell'hackathon,
     * carica il testo esistente e configura il meccanismo di salvataggio.
     *
     * @param parentFrame il frame parent di questa finestra
     * @param controller il controller per la gestione del problema
     */
    public ModificaProblema(JFrame parentFrame, Controller controller) {
        frame = new JFrame("Modifica Problema");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(parentFrame);
        frame.setVisible(true);

        // Carica il problema
        ProblemaTextArea.setText(controller.getProblemaGiudice());

        salvaButton.addActionListener(e -> {
            controller.setProblemaGiudice(ProblemaTextArea.getText());
            JOptionPane.showMessageDialog(frame, "Problema salvato correttamente!");
        });
    }
}