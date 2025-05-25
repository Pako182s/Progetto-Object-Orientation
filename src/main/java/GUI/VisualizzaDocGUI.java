package GUI;

import Controller.Controller;
import model.Team;

import javax.swing.*;
import java.util.List;

public class VisualizzaDocGUI extends JFrame {
    private JPanel panel1;
    private JButton visualizzaButton;
    private JComboBox<String> comboBox1;

    private Controller controller;
    private List<Team> teamList;

    public VisualizzaDocGUI(JFrame parent, Controller controller) {
        this.controller = controller;
        this.teamList = controller.getListaTeam();

        setTitle("Visualizza Documenti Team");
        setContentPane(panel1);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        setSize(400, 200);
        setVisible(true);

        comboBox1.removeAllItems();
        for (Team team : teamList) {
            comboBox1.addItem(team.getNomeTeam());
        }

        visualizzaButton.addActionListener(e -> {
            int index = comboBox1.getSelectedIndex();
            if (index >= 0) {
                Team team = teamList.get(index);
                String documento = controller.getDocumentoPerTeam(team);
                if (documento == null || documento.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nessun documento inviato dal team.");
                } else {
                    JTextArea area = new JTextArea(documento);
                    area.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(area);
                    scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
                    JOptionPane.showMessageDialog(this, scrollPane, "Documento di " + team.getNomeTeam(), JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
}
