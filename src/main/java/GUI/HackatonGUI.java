package GUI;

import javax.swing.*;
import Controller.Controller;
import model.Hackaton;

import java.text.SimpleDateFormat;

public class HackatonGUI {

    public static JFrame frame;
    private JPanel panel1;
    private JLabel titoloLabel;
    private JLabel sedeLabel;
    private JLabel durataLabel;
    private JLabel maxIscrittiLabel;
    private JLabel aperturaLabel;
    private JLabel chiusuraLabel;
    private JLabel maxTeamLabel;

    private JFrame frameChiamante;
    private Controller controller;

    public HackatonGUI(JFrame frameChiamante, Controller controller) {
        this.frameChiamante = frameChiamante;
        this.controller = controller;

        frame = new JFrame("Hackathon Info");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();

        aggiornaDatiHackathon();
    }

    private void aggiornaDatiHackathon() {
        Hackaton hackathon = controller.getHackaton();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        String inizio = (hackathon.getDataInizio() == null) ? "Ancora non disponibili" : sdf.format(hackathon.getDataInizio());
        String fine = (hackathon.getDataFine() == null) ? "Ancora non disponibili" : sdf.format(hackathon.getDataFine());
        String apertura = (hackathon.getInizioIscrizioni() == null) ? "Ancora non disponibili" : sdf.format(hackathon.getInizioIscrizioni());
        String chiusura = (hackathon.getFineIscrizioni() == null) ? "Ancora non disponibili" : sdf.format(hackathon.getFineIscrizioni());

        titoloLabel.setText("Titolo: " + hackathon.getTitolo());
        sedeLabel.setText("Sede: " + hackathon.getSede());
        durataLabel.setText("Durata: " + inizio + " - " + fine);
        aperturaLabel.setText("Apertura Iscrizioni: " + apertura);
        chiusuraLabel.setText("Chiusura Iscrizioni: " + chiusura);
        maxIscrittiLabel.setText("Num. Max Iscritti: " + hackathon.getNumeroMaxIscritti());
        maxTeamLabel.setText("Num. Max componenti team: " + hackathon.getComponentMaxTeam());
    }
}
