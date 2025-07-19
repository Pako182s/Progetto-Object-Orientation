package GUI;

import Controller.Controller;
import model.Team;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

/**
 * Classe GUI per la visualizzazione della classifica dei team dell'hackathon.
 * Mostra i team ordinati in base alla media dei voti ricevuti,
 * con posizione, nome del team e punteggio medio.
 *

 */
public class ClassificaGUI extends JFrame {
    private JPanel panel1;
    private JTable tabellaClassifica;

    /**
     * Costruttore della classe ClassificaGUI.
     * Inizializza l'interfaccia per la visualizzazione della classifica,
     * recupera i dati dei team e li ordina per media voti in ordine decrescente.
     *
     * @param parent il frame parent di questa finestra
     * @param controller il controller per recuperare i dati della classifica
     */
    public ClassificaGUI(JFrame parent, Controller controller) {
        setTitle("Classifica Team");
        setContentPane(panel1);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        setSize(500, 300); //imposta la dimensione della finestra
        setVisible(true);

        //crea il modello della tabella
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Posizione");
        model.addColumn("Team");
        model.addColumn("Media Voti");

        //ottieme l'elenco dei team con la loro media voti
        Map<Team, Double> classifica = controller.getClassificaMedia();
        List<Map.Entry<Team, Double>> ordinati = new ArrayList<>(classifica.entrySet());
        ordinati.sort((a, b) -> Double.compare(b.getValue(), a.getValue())); // ordine decrescente

        //aggiunge i dati ordinati alla tabella, una riga per team
        int posizione = 1;
        for (Map.Entry<Team, Double> entry : ordinati) {
            model.addRow(new Object[]{
                    posizione++,
                    entry.getKey().getNomeTeam(),
                    String.format("%.2f", entry.getValue()) // media voti con due decimali
            });
        }

        tabellaClassifica.setModel(model);
    }
}