package GUI;

import Controller.Controller;

import javax.swing.*;

public class ModificaProblema {
    private JButton salvaButton;
    private JTextArea ProblemaTextArea;
    private JPanel panel1;

    private JFrame frame;


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
