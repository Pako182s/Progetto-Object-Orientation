package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controller.Controller;

/**
 * Classe GUI per la registrazione di nuovi utenti all'hackathon.
 * Gestisce il processo di registrazione con validazione dei campi,
 * controllo del numero massimo di iscritti e reindirizzamento
 * alla schermata di accesso dopo la registrazione.
 *

 */
public class RegistratiGUI {

    private Controller controller;
    public JFrame frame;
    private JPanel panel1;
    private JFrame frameChiamante;
    private JTextField textField2;
    private JPasswordField passwordField1;
    private JTextField textField1;
    private JButton registratiButton;
    private JButton indietroButton;

    /**
     * Costruttore della classe RegistratiGUI.
     * Inizializza l'interfaccia di registrazione e configura i listener
     * per la gestione della registrazione e della navigazione.
     *
     * @param frameChiamante il frame parent da cui è stata chiamata questa GUI
     * @param controller il controller per la gestione della logica di registrazione
     */
    public RegistratiGUI(JFrame frameChiamante, Controller controller) {
        this.frameChiamante = frameChiamante;
        this.controller = controller;

        frame = new JFrame("Registrazione");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = textField1.getText().trim();
                String password = new String(passwordField1.getPassword()).trim();
                String email = textField2.getText().trim();

                if (nome.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(frame,
                            "Per favore, compila tutti i campi.",
                            "Attenzione",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                //Controlla il numero massimo di iscritti
                int maxIscritti = controller.getHackaton().getNumeroMaxIscritti();
                long numeroPartecipanti = controller.getUtentiRegistrati().stream()
                        .filter(u -> !"giudice".equalsIgnoreCase(u.getRuolo()))
                        .count();

                if (numeroPartecipanti >= maxIscritti) {
                    JOptionPane.showMessageDialog(frame,
                            "Registrazione non disponibile: posti esauriti.",
                            "Posti esauriti",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                controller.setUtenteIscritto(nome, password, email);
                JOptionPane.showMessageDialog(frame, "Registrazione completata!");

                // Torna alla schermata di accesso
                AccessoGUI accessoGUI = new AccessoGUI(frameChiamante, controller);
                accessoGUI.frame.setVisible(true);
                frame.dispose();
            }
        });

        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });

        //Se viene cliccata la X ritorna automaticamente alla schermata precedente
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
    }
}