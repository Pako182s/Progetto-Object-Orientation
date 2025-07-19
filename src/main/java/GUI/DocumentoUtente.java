package GUI;

import javax.swing.*;
import Controller.Controller;
import model.UtenteIscritto;
import model.Team;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe GUI per la gestione del documento del team.
 * Permette ai membri del team di visualizzare, modificare e salvare
 * il documento associato al loro team nell'hackathon.
 *

 */
public class DocumentoUtente {
    private JPanel panel1;
    private JTextArea documentotextArea;
    private JButton salvaButton;

    public JFrame frame;

    /**
     * Costruttore della classe DocumentoUtente.
     * Inizializza l'interfaccia per l'editing del documento del team,
     * carica il contenuto esistente e configura il meccanismo di salvataggio.
     *
     * @param frameChiamante il frame parent da cui è stata chiamata questa GUI
     * @param controller il controller per la gestione della logica di business
     */
    public DocumentoUtente(JFrame frameChiamante, Controller controller) {
        frame = new JFrame("Documento Team");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        //ottiene utente e team attualmente autenticato
        UtenteIscritto utente = controller.getUtenteAutenticato();
        Team team = controller.getTeamDiUtente(utente);

        //carica il documento del team e lo mostra nell'area di testo
        documentotextArea.setText(controller.getDocumentoPerTeam(team));

        //salva il documento
        salvaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String testo = documentotextArea.getText();
                controller.salvaDocumentoPerTeam(team, documentotextArea.getText());

                JOptionPane.showMessageDialog(frame, "Documento salvato!");
            }
        });

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                frameChiamante.setVisible(true);
            }
        });
    }
}