package GUI;

import Controller.Controller;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe GUI per l'interfaccia del giudice dell'hackathon.
 * Fornisce accesso alle funzionalità specifiche del giudice come
 * modifica del problema, votazione dei team, visualizzazione della classifica
 * e consultazione dei documenti.
 *
 */
class GiudiceGUI {
    private JPanel panel1;
    private JButton esciButton;
    private JButton modificaProblemaButton;
    private JLabel labelDataChiusura;  // Etichetta per mostrare data chiusura
    private JButton votiButton;
    private JButton classificaButton;
    private JButton visualizzaDocumentiButton;
    public JFrame frame;

    private Controller controller;

    /**
     * Costruttore della classe GiudiceGUI.
     * Inizializza l'interfaccia grafica per il giudice, configura i pulsanti
     * e gestisce la logica di abilitazione/disabilitazione delle funzionalità
     * in base alla data di chiusura delle iscrizioni.
     *
     * @param frameChiamante il frame parent da cui è stata chiamata questa GUI
     * @param controller il controller per la gestione della logica di business
     * @param nomeUtente il nome del giudice autenticato
     */
    public GiudiceGUI(JFrame frameChiamante, Controller controller, String nomeUtente) {
        this.controller = controller;

        frame = new JFrame("Area Giudice - " + nomeUtente);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();


        esciButton.addActionListener(e -> {
            frameChiamante.setVisible(true);
            frame.dispose();
        });

        //mostra la data di chiusura iscrizioni accanto al textField
        Date dataChiusura = controller.getHackaton().getFineIscrizioni();
        String dataTesto;
        //controlla se la data è disponibile, così da formattarla e mostrarla
        if (dataChiusura != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            dataTesto = sdf.format(dataChiusura);
        } else {
            dataTesto = "Data chiusura non impostata";
        }
        labelDataChiusura.setText("Fine modifica: " + dataTesto);

        //controlla se la modifica è permessa in base alla fine delle iscrizioni
        boolean modificaConsentita = (dataChiusura == null || new Date().before(dataChiusura));
        modificaProblemaButton.setEnabled(modificaConsentita);


        modificaProblemaButton.addActionListener(e -> {
            new ModificaProblema(frame, controller);
        });

        votiButton.addActionListener(e -> {
            new Voti(frame, controller); // Assumendo che Voti sia un JFrame
        });

        classificaButton.addActionListener(e -> {
            new ClassificaGUI(frame, controller);
        });

        visualizzaDocumentiButton.addActionListener(e -> {
            new VisualizzaDocGUI(frame, controller);
        });



    }
}