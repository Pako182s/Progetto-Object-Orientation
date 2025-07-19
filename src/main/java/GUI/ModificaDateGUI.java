package GUI;

import Controller.Controller;

import javax.swing.*;
import java.util.Date;

public class ModificaDateGUI {
    private JPanel panel1;
    private JSpinner spinnerInizioIscrizioni;
    private JSpinner spinnerFineIscrizioni;
    private JSpinner spinnerInizioEvento;
    private JSpinner spinnerFineEvento;
    private JButton salvaButton;
    private JFrame frame;

    public ModificaDateGUI(JFrame parentFrame, Controller controller) {
        frame = new JFrame("Modifica Date Hackathon");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(parentFrame);
        frame.setVisible(true);

        //inizializza le date per iscrizioni ed evento
        spinnerInizioIscrizioni.setModel(new SpinnerDateModel());
        spinnerFineIscrizioni.setModel(new SpinnerDateModel());
        spinnerInizioEvento.setModel(new SpinnerDateModel());
        spinnerFineEvento.setModel(new SpinnerDateModel());


        spinnerInizioIscrizioni.setEditor(new JSpinner.DateEditor(spinnerInizioIscrizioni, "dd/MM/yyyy HH:mm"));
        spinnerFineIscrizioni.setEditor(new JSpinner.DateEditor(spinnerFineIscrizioni, "dd/MM/yyyy HH:mm"));
        spinnerInizioEvento.setEditor(new JSpinner.DateEditor(spinnerInizioEvento, "dd/MM/yyyy HH:mm"));
        spinnerFineEvento.setEditor(new JSpinner.DateEditor(spinnerFineEvento, "dd/MM/yyyy HH:mm"));

        //verifica se esiste un già un hackaton, e utilizza le date per compilare i campi
        if (controller.getHackaton() != null) {
            spinnerInizioIscrizioni.setValue(defaultDate(controller.getHackaton().getInizioIscrizioni()));
            spinnerFineIscrizioni.setValue(defaultDate(controller.getHackaton().getFineIscrizioni()));
            spinnerInizioEvento.setValue(defaultDate(controller.getHackaton().getDataInizio()));
            spinnerFineEvento.setValue(defaultDate(controller.getHackaton().getDataFine()));
        }

        salvaButton.addActionListener(e -> {
            Date inizioIscrizioni = (Date) spinnerInizioIscrizioni.getValue();
            Date fineIscrizioni = (Date) spinnerFineIscrizioni.getValue();
            Date inizioEvento = (Date) spinnerInizioEvento.getValue();
            Date fineEvento = (Date) spinnerFineEvento.getValue();


            if (fineIscrizioni.before(inizioIscrizioni)) {
                JOptionPane.showMessageDialog(frame, "La fine delle iscrizioni non può essere prima dell'inizio.");
                return;
            }

            if (inizioEvento.before(fineIscrizioni)) {
                JOptionPane.showMessageDialog(frame, "L'inizio dell'evento deve essere dopo la chiusura delle iscrizioni.");
                return;
            }

            if (fineEvento.before(inizioEvento)) {
                JOptionPane.showMessageDialog(frame, "La fine dell'evento non può essere prima dell'inizio.");
                return;
            }

            controller.aggiornaDateHackathon(inizioIscrizioni, fineIscrizioni, inizioEvento, fineEvento);
            JOptionPane.showMessageDialog(frame, "Date aggiornate con successo!");
            frame.dispose();
        });
    }
    //Evita i valori nulli, restituisce la data passata, o se è null, la data corrente
    private Date defaultDate(Date date) {
        return date != null ? date : new Date();
    }
}
