package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.UtenteIscritto;
import Controller.Controller;

/**
 * Classe GUI per la gestione dell'accesso degli utenti al sistema.
 * Fornisce un'interfaccia grafica per l'autenticazione e reindirizza
 * l'utente alla GUI appropriata in base al suo ruolo (organizzatore, giudice, utente).
 *

 */
public class AccessoGUI {
    public JFrame frame;
    private JPanel panel1;
    private JTextField eMailTextField;
    private JPasswordField passwordField1;
    private JButton indietroButton;
    private JButton accediButton;

    private JFrame frameChiamante;
    private Controller controller; //

    /**
     * Costruttore della classe AccessoGUI.
     * Inizializza l'interfaccia grafica e configura i listener per i pulsanti.
     *
     * @param frameChiamante il frame parent da cui è stata chiamata questa GUI
     * @param controller il controller per la gestione della logica di business
     */
    public AccessoGUI(JFrame frameChiamante, Controller controller) {
        this.frameChiamante = frameChiamante;
        this.controller = controller;

        frame = new JFrame("Accesso GUI");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();


        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });

        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = eMailTextField.getText();
                String password = new String(passwordField1.getPassword());

                if (controller.isOrganizzatore(email, password)) {
                    // verifica se è l'organizzatore
                    OrganizzatoreGUI organizzatoreGUI = new OrganizzatoreGUI(frameChiamante, controller);
                    organizzatoreGUI.frame.setVisible(true);
                    frame.dispose();

                } else if (controller.autenticaUtente(email, password)) {
                    // verifica se l'autenticazione ha successo e verifica se è un utente normale o  un giudice
                    UtenteIscritto utente = controller.getUtenteAutenticato();

                    if ("giudice".equalsIgnoreCase(utente.getRuolo())) {
                        //  verifica se il ruolo è giudice
                        GiudiceGUI giudiceGUI = new GiudiceGUI(frameChiamante, controller, utente.getNome());
                        giudiceGUI.frame.setVisible(true);
                        frame.dispose();

                    } else {
                        // altrimenti è un Utente normale
                        UtenteGUI utenteGUI = new UtenteGUI(frameChiamante, controller, utente.getNome());
                        utenteGUI.frame.setVisible(true);
                        frame.dispose();
                    }

                } else {
                    JOptionPane.showMessageDialog(frame, "Email o password errati", "Errore", JOptionPane.ERROR_MESSAGE); //mostra un messaggio di errore, quando email e password sono errati
                }
            }
        });

    }


}