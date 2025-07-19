package ImplementazioniPostresDAO;

import DAO.VotoDAO;
import DAO.UtenteIscrittoDAO;
import Database.ConnessioneDatabase;
import model.Team;
import model.UtenteIscritto;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementazione PostgreSQL del DAO per la gestione dei voti.
 * Gestisce i voti assegnati dai giudici ai team nel database PostgreSQL,
 * fornendo operazioni per salvare, aggiornare e recuperare voti e commenti.
 *

 */
public class PostgresVotoDAO implements VotoDAO {

    private UtenteIscrittoDAO utenteDAO = new PostgresUtenteIscrittoDAO();

    /**
     * Salva un nuovo voto nel database.
     *
     * @param team il team a cui assegnare il voto
     * @param giudice il giudice che assegna il voto
     * @param voto il valore numerico del voto
     * @param commento il commento associato al voto
     * @return true se il voto è stato salvato con successo, false altrimenti
     */
    @Override
    public boolean salvaVoto(Team team, UtenteIscritto giudice, int voto, String commento) {
        String query = "INSERT INTO voti_giudici (nome_team, mail_giudice, voto, commento) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, team.getNomeTeam());
            ps.setString(2, giudice.getMail());
            ps.setInt(3, voto);
            ps.setString(4, commento);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Aggiorna un voto esistente nel database.
     * Aggiorna anche automaticamente il timestamp del voto.
     *
     * @param team il team di cui aggiornare il voto
     * @param giudice il giudice che ha assegnato il voto
     * @param voto il nuovo valore numerico del voto
     * @param commento il nuovo commento associato al voto
     * @return true se il voto è stato aggiornato con successo, false altrimenti
     */
    @Override
    public boolean aggiornaVoto(Team team, UtenteIscritto giudice, int voto, String commento) {
        String query = "UPDATE voti_giudici SET voto = ?, commento = ?, data_voto = CURRENT_TIMESTAMP WHERE nome_team = ? AND mail_giudice = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, voto);
            ps.setString(2, commento);
            ps.setString(3, team.getNomeTeam());
            ps.setString(4, giudice.getMail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Recupera il voto assegnato da un giudice specifico a un team specifico.
     *
     * @param team il team di cui recuperare il voto
     * @param giudice il giudice che ha assegnato il voto
     * @return il valore del voto se presente, null se non esiste
     */
    @Override
    public Integer getVoto(Team team, UtenteIscritto giudice) {
        String query = "SELECT voto FROM voti_giudici WHERE nome_team = ? AND mail_giudice = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, team.getNomeTeam());
            ps.setString(2, giudice.getMail());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("voto");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Recupera tutti i voti assegnati da un giudice specifico.
     *
     * @param giudice il giudice di cui recuperare i voti
     * @return una mappa che associa ogni team al voto assegnato dal giudice
     */
    @Override
    public Map<Team, Integer> getVotiDiGiudice(UtenteIscritto giudice) {
        Map<Team, Integer> voti = new HashMap<>();
        String query = "SELECT nome_team, voto FROM voti_giudici WHERE mail_giudice = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, giudice.getMail());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nomeTeam = rs.getString("nome_team");
                    int voto = rs.getInt("voto");
                    // Qui dovresti caricare il team dal TeamDAO
                    Team team = new Team(nomeTeam);
                    voti.put(team, voto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voti;
    }

    /**
     * Recupera tutti i voti ricevuti da un team specifico.
     *
     * @param team il team di cui recuperare i voti
     * @return una mappa che associa ogni giudice al voto assegnato al team
     */
    @Override
    public Map<UtenteIscritto, Integer> getVotiPerTeam(Team team) {
        Map<UtenteIscritto, Integer> voti = new HashMap<>();
        String query = "SELECT mail_giudice, voto FROM voti_giudici WHERE nome_team = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, team.getNomeTeam());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String mailGiudice = rs.getString("mail_giudice");
                    int voto = rs.getInt("voto");
                    UtenteIscritto giudice = utenteDAO.findByMail(mailGiudice);
                    if (giudice != null) {
                        voti.put(giudice, voto);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voti;
    }

    /**
     * Calcola la media dei voti ricevuti da un team.
     * Utilizza la funzione AVG di SQL per calcolare la media direttamente nel database.
     *
     * @param team il team di cui calcolare la media
     * @return la media aritmetica dei voti ricevuti dal team, 0.0 se non ci sono voti
     */
    @Override
    public double getMediaVotiTeam(Team team) {
        String query = "SELECT AVG(voto) as media FROM voti_giudici WHERE nome_team = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, team.getNomeTeam());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("media");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    /**
     * Verifica se esiste un voto assegnato da un giudice specifico a un team specifico.
     *
     * @param team il team da verificare
     * @param giudice il giudice da verificare
     * @return true se il voto esiste, false altrimenti
     */
    @Override
    public boolean esisteVoto(Team team, UtenteIscritto giudice) {
        String query = "SELECT 1 FROM voti_giudici WHERE nome_team = ? AND mail_giudice = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, team.getNomeTeam());
            ps.setString(2, giudice.getMail());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Recupera il commento associato a un voto specifico.
     *
     * @param team il team di cui recuperare il commento
     * @param giudice il giudice che ha assegnato il voto
     * @return il commento associato al voto, null se non presente o se il voto non esiste
     */
    @Override
    public String getCommento(Team team, UtenteIscritto giudice) {
        String query = "SELECT commento FROM voti_giudici WHERE nome_team = ? AND mail_giudice = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, team.getNomeTeam());
            ps.setString(2, giudice.getMail());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("commento");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}