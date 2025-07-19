package ImplementazioniPostresDAO;

import DAO.UtenteIscrittoDAO;
import Database.ConnessioneDatabase;
import model.UtenteIscritto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione PostgreSQL del DAO per la gestione degli utenti iscritti.
 * Fornisce operazioni CRUD complete per gli oggetti UtenteIscritto nel database PostgreSQL,
 * incluse operazioni di ricerca, inserimento, aggiornamento e cancellazione.
 *

 */
public class PostgresUtenteIscrittoDAO implements UtenteIscrittoDAO {

    /**
     * Trova un utente per email nel database.
     * L'email è utilizzata come chiave primaria per l'identificazione dell'utente.
     *
     * @param mail l'email dell'utente da cercare
     * @return l'oggetto UtenteIscritto se trovato, null altrimenti
     */
    @Override
    public UtenteIscritto findByMail(String mail) {
        String query = "SELECT * FROM utenti WHERE mail = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, mail);
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
     * Recupera tutti gli utenti iscritti presenti nel database.
     *
     * @return lista di tutti gli utenti registrati, lista vuota se nessuno presente
     */
    @Override
    public List<UtenteIscritto> findAll() {
        List<UtenteIscritto> list = new ArrayList<>();
        String query = "SELECT * FROM utenti";
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
     * Salva un nuovo utente nel database.
     *
     * @param utente l'oggetto UtenteIscritto da salvare
     * @return true se l'inserimento è riuscito, false altrimenti
     */
    @Override
    public boolean save(UtenteIscritto utente) {
        String query = "INSERT INTO utenti (nome, password, mail, ruolo) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getPassword());
            ps.setString(3, utente.getMail());
            ps.setString(4, utente.getRuolo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Aggiorna un utente esistente nel database.
     * L'utente viene identificato per email (chiave primaria).
     *
     * @param utente l'oggetto UtenteIscritto con i dati aggiornati
     * @return true se l'aggiornamento è riuscito, false altrimenti
     */
    @Override
    public boolean update(UtenteIscritto utente) {
        String query = "UPDATE utenti SET nome = ?, password = ?, ruolo = ? WHERE mail = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getPassword());
            ps.setString(3, utente.getRuolo());
            ps.setString(4, utente.getMail());  // chiave primaria per update è mail
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Cancella un utente dal database.
     *
     * @param utente l'oggetto UtenteIscritto da cancellare
     * @return true se la cancellazione è riuscita, false altrimenti
     */
    @Override
    public boolean delete(UtenteIscritto utente) {
        String query = "DELETE FROM utenti WHERE mail = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, utente.getMail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Metodo privato per mappare un ResultSet a un oggetto UtenteIscritto.
     * Converte i dati del database in un oggetto del modello.
     *
     * @param rs il ResultSet contenente i dati dell'utente
     * @return l'oggetto UtenteIscritto mappato
     * @throws SQLException se si verifica un errore durante la lettura dei dati
     */
    private UtenteIscritto map(ResultSet rs) throws SQLException {
        UtenteIscritto utente = new UtenteIscritto(
                rs.getString("nome"),
                rs.getString("password"),
                rs.getString("mail")
        );
        utente.setRuolo(rs.getString("ruolo"));
        return utente;
    }
}