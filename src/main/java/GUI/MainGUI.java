package GUI;

import javax.swing.*;
import Controller.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainGUI {
    private Controller controller;
    private JPanel panel1;
    private static JFrame frame;
    private JButton accediButton;
    private JButton registratiButton;
    private JLabel registrazione;

    public static void main(String[] args) {
        frame = new JFrame("MainGUI"); //Crea la nuova finestra Main GUI
        frame.setContentPane(new MainGUI().panel1); //Imposta il contenuto della finestra
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Chiusura della finestra quando clicchi sulla "X"
        frame.pack(); //Utilizza le dimensioni preferite scelte
        frame.setLocationRelativeTo(null); //Adatalla la finestra al centro dello schermo
        frame.setVisible(true); //Rende visibile la finestra in questione
    }

    public MainGUI() {
        controller = new Controller();

        //Accede alla finestra AccediGUI
        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccessoGUI accessoGUI = new AccessoGUI(frame, controller);
                accessoGUI.frame.setVisible(true);
                frame.setVisible(false);



            }
        });

        //Accede alla finestra RegistratiGUI
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistratiGUI RegistratiGUI = new RegistratiGUI(frame, controller);
                RegistratiGUI.frame.setVisible(true);
                frame.setVisible(false);

            }


        });



    }
}

