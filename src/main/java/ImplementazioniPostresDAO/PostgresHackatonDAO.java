package ImplementazioniPostresDAO;

import DAO.HackatonDAO;
import Database.ConnessioneDatabase;
import model.Hackaton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione PostgreSQL del DAO per la gestione degli hackathon.
 * Fornisce operazioni CRUD complete per gli oggetti Hackaton nel database PostgreSQL,
 * incluse operazioni di ricerca, inserimento, aggiornamento e cancellazione.
 *

 */
public class PostgresHackatonDAO implements HackatonDAO {

    /**
     * Trova un hackathon per titolo nel database.
     *
     * @param titolo il titolo dell'hackathon da cercare
     * @return l'oggetto Hackaton se trovato, null altrimenti
     */
    @Override
    public Hackaton findByTitolo(String titolo) {
        String query = "SELECT * FROM hackaton WHERE titolo = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, titolo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Recupera tutti gli hackathon presenti nel database.
     *
     * @return lista di tutti gli hackathon, lista vuota se nessuno presente
     */
    @Override
    public List<Hackaton> findAll() {
        List<Hackaton> list = new ArrayList<>();
        String query = "SELECT * FROM hackaton";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Salva un nuovo hackathon nel database.
     *
     * @param h l'oggetto Hackaton da salvare
     * @return true se l'inserimento è riuscito, false altrimenti
     */
    @Override
    public boolean save(Hackaton h) {
        String query = """
                INSERT INTO hackaton(
                    titolo, sede, data_inizio, data_fine,
                    numero_max_iscritti, component_max_team,
                    inizio_iscrizioni, fine_iscrizioni, descrizione_problema
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, h.getTitolo());
            ps.setString(2, h.getSede());

            if (h.getDataInizio() != null)
                ps.setDate(3, new java.sql.Date(h.getDataInizio().getTime()));
            else
                ps.setDate(3, null);

            if (h.getDataFine() != null)
                ps.setDate(4, new java.sql.Date(h.getDataFine().getTime()));
            else
                ps.setDate(4, null);

            ps.setInt(5, h.getNumeroMaxIscritti());
            ps.setInt(6, h.getComponentMaxTeam());

            if (h.getInizioIscrizioni() != null)
                ps.setDate(7, new java.sql.Date(h.getInizioIscrizioni().getTime()));
            else
                ps.setDate(7, null);

            if (h.getFineIscrizioni() != null)
                ps.setDate(8, new java.sql.Date(h.getFineIscrizioni().getTime()));
            else
                ps.setDate(8, null);

            ps.setString(9, h.getDescrizioneProblema());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Aggiorna un hackathon esistente nel database.
     * L'hackathon viene identificato per titolo (chiave primaria).
     *
     * @param h l'oggetto Hackaton con i dati aggiornati
     * @return true se l'aggiornamento è riuscito, false altrimenti
     */
    @Override
    public boolean update(Hackaton h) {
        String query = """
                UPDATE hackaton SET
                sede = ?, data_inizio = ?, data_fine = ?,
                numero_max_iscritti = ?, component_max_team = ?,
                inizio_iscrizioni = ?, fine_iscrizioni = ?, descrizione_problema = ?
                WHERE titolo = ?
                """;

        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, h.getSede());

            if (h.getDataInizio() != null)
                ps.setDate(2, new java.sql.Date(h.getDataInizio().getTime()));
            else
                ps.setDate(2, null);

            if (h.getDataFine() != null)
                ps.setDate(3, new java.sql.Date(h.getDataFine().getTime()));
            else
                ps.setDate(3, null);

            ps.setInt(4, h.getNumeroMaxIscritti());
            ps.setInt(5, h.getComponentMaxTeam());

            if (h.getInizioIscrizioni() != null)
                ps.setDate(6, new java.sql.Date(h.getInizioIscrizioni().getTime()));
            else
                ps.setDate(6, null);

            if (h.getFineIscrizioni() != null)
                ps.setDate(7, new java.sql.Date(h.getFineIscrizioni().getTime()));
            else
                ps.setDate(7, null);

            ps.setString(8, h.getDescrizioneProblema());
            ps.setString(9, h.getTitolo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Cancella un hackathon dal database.
     *
     * @param h l'oggetto Hackaton da cancellare
     * @return true se la cancellazione è riuscita, false altrimenti
     */
    @Override
    public boolean delete(Hackaton h) {
        String query = "DELETE FROM hackaton WHERE titolo = ?";

        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, h.getTitolo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Metodo privato per mappare un ResultSet a un oggetto Hackaton.
     * Converte i dati del database in un oggetto del modello.
     *
     * @param rs il ResultSet contenente i dati dell'hackathon
     * @return l'oggetto Hackaton mappato
     * @throws SQLException se si verifica un errore durante la lettura dei dati
     */
    private Hackaton map(ResultSet rs) throws SQLException {
        return new Hackaton(
                rs.getString("titolo"),
                rs.getString("sede"),
                rs.getDate("data_inizio"),
                rs.getDate("data_fine"),
                rs.getInt("numero_max_iscritti"),
                rs.getInt("component_max_team"),
                rs.getDate("inizio_iscrizioni"),
                rs.getDate("fine_iscrizioni")

        );
    }

}