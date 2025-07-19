package ImplementazioniPostresDAO;

import DAO.DocumentoDAO;
import Database.ConnessioneDatabase;
import model.Team;
import model.Hackaton;

import java.sql.*;

/**
 * Implementazione PostgreSQL del DAO per la gestione dei documenti.
 * Gestisce i documenti dei team e i problemi dell'hackathon nel database PostgreSQL,
 * fornendo operazioni CRUD complete con gestione automatica di inserimenti e aggiornamenti.
 *

 */
public class PostgresDocumentoDAO implements DocumentoDAO {

    /**
     * Recupera il contenuto del documento di un team dal database.
     *
     * @param team il team di cui recuperare il documento
     * @return il contenuto del documento, stringa vuota se non presente
     */
    @Override
    public String getDocumentoTeam(Team team) {
        String query = "SELECT contenuto FROM documenti_team WHERE nome_team = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, team.getNomeTeam());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("contenuto");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Salva o aggiorna il documento di un team nel database.
     * Se il documento esiste già, viene aggiornato, altrimenti viene inserito.
     *
     * @param team il team a cui associare il documento
     * @param contenuto il contenuto del documento da salvare
     * @return true se l'operazione è riuscita, false altrimenti
     */
    @Override
    public boolean salvaDocumentoTeam(Team team, String contenuto) {
        if (esisteDocumentoTeam(team)) {
            // Aggiorna documento esistente
            String query = "UPDATE documenti_team SET contenuto = ?, data_modifica = CURRENT_TIMESTAMP WHERE nome_team = ?";
            try (Connection conn = ConnessioneDatabase.getInstance().connection;
                 PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, contenuto);
                ps.setString(2, team.getNomeTeam());
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Inserisci nuovo documento
            String query = "INSERT INTO documenti_team (nome_team, contenuto) VALUES (?, ?)";
            try (Connection conn = ConnessioneDatabase.getInstance().connection;
                 PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, team.getNomeTeam());
                ps.setString(2, contenuto);
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Verifica se esiste un documento per il team specificato.
     *
     * @param team il team da verificare
     * @return true se il documento esiste, false altrimenti
     */
    @Override
    public boolean esisteDocumentoTeam(Team team) {
        String query = "SELECT 1 FROM documenti_team WHERE nome_team = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, team.getNomeTeam());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Recupera il testo del problema dell'hackathon dal database.
     *
     * @param hackaton l'hackathon di cui recuperare il problema
     * @return il testo del problema, stringa vuota se non presente
     */
    @Override
    public String getProblemaHackathon(Hackaton hackaton) {
        String query = "SELECT testo_problema FROM problema_hackathon WHERE titolo_hackathon = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, hackaton.getTitolo());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("testo_problema");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Salva o aggiorna il problema dell'hackathon nel database.
     * Se il problema esiste già, viene aggiornato, altrimenti viene inserito.
     *
     * @param hackaton l'hackathon a cui associare il problema
     * @param problema il testo del problema da salvare
     * @return true se l'operazione è riuscita, false altrimenti
     */
    @Override
    public boolean salvaProblemaHackathon(Hackaton hackaton, String problema) {
        if (esisteProblemaHackathon(hackaton)) {
            // Aggiorna problema esistente
            String query = "UPDATE problema_hackathon SET testo_problema = ?, data_modifica = CURRENT_TIMESTAMP WHERE titolo_hackathon = ?";
            try (Connection conn = ConnessioneDatabase.getInstance().connection;
                 PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, problema);
                ps.setString(2, hackaton.getTitolo());
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Inserisci nuovo problema
            String query = "INSERT INTO problema_hackathon (titolo_hackathon, testo_problema) VALUES (?, ?)";
            try (Connection conn = ConnessioneDatabase.getInstance().connection;
                 PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, hackaton.getTitolo());
                ps.setString(2, problema);
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Verifica se esiste un problema per l'hackathon specificato.
     *
     * @param hackaton l'hackathon da verificare
     * @return true se il problema esiste, false altrimenti
     */
    @Override
    public boolean esisteProblemaHackathon(Hackaton hackaton) {
        String query = "SELECT 1 FROM problema_hackathon WHERE titolo_hackathon = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, hackaton.getTitolo());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}