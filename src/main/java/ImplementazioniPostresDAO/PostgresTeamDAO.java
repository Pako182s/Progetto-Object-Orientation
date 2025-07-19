package ImplementazioniPostresDAO;

import DAO.TeamDAO;
import DAO.UtenteIscrittoDAO;
import Database.ConnessioneDatabase;
import model.Team;
import model.UtenteIscritto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione PostgreSQL del DAO per la gestione dei team.
 * Gestisce team, membri e inviti nel database PostgreSQL con operazioni CRUD complete
 * e funzionalità avanzate per la gestione degli inviti tra utenti e team.
 *

 */
public class PostgresTeamDAO implements TeamDAO {

    private UtenteIscrittoDAO utenteDAO = new PostgresUtenteIscrittoDAO();

    /**
     * Trova un team per nome nel database.
     *
     * @param nomeTeam il nome del team da cercare
     * @return l'oggetto Team con i membri caricati, null se non trovato
     */
    @Override
    public Team findByNome(String nomeTeam) {
        String query = "SELECT * FROM team WHERE nome_team = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, nomeTeam);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapTeamConMembri(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Recupera tutti i team presenti nel database.
     *
     * @return lista di tutti i team con i rispettivi membri, lista vuota se nessuno presente
     */
    @Override
    public List<Team> findAll() {
        List<Team> teams = new ArrayList<>();
        String query = "SELECT * FROM team";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                teams.add(mapTeamConMembri(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }

    /**
     * Trova tutti i team di cui un utente è membro.
     *
     * @param utente l'utente di cui cercare i team
     * @return lista dei team a cui l'utente appartiene
     */
    @Override
    public List<Team> findByMembro(UtenteIscritto utente) {
        List<Team> teams = new ArrayList<>();
        String query = """
                SELECT t.* FROM team t
                JOIN team_membri tm ON t.nome_team = tm.nome_team
                WHERE tm.mail_utente = ?
                """;
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, utente.getMail());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    teams.add(mapTeamConMembri(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }

    /**
     * Salva un nuovo team nel database con tutti i suoi membri.
     * Utilizza una transazione per garantire la consistenza dei dati.
     *
     * @param team l'oggetto Team da salvare
     * @return true se il salvataggio è riuscito, false altrimenti
     */
    @Override
    public boolean save(Team team) {
        Connection conn = null;
        try {
            conn = ConnessioneDatabase.getInstance().connection;
            conn.setAutoCommit(false);

            // Salva il team
            String queryTeam = "INSERT INTO team (nome_team) VALUES (?)";
            try (PreparedStatement ps = conn.prepareStatement(queryTeam)) {
                ps.setString(1, team.getNomeTeam());
                ps.executeUpdate();
            }

            // Salva i membri del team
            String queryMembri = "INSERT INTO team_membri (nome_team, mail_utente) VALUES (?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(queryMembri)) {
                for (UtenteIscritto membro : team.getConcorrenti()) {
                    ps.setString(1, team.getNomeTeam());
                    ps.setString(2, membro.getMail());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * Aggiorna la composizione dei membri di un team esistente.
     * Rimuove tutti i membri esistenti e aggiunge quelli attuali.
     *
     * @param team l'oggetto Team con la composizione aggiornata
     * @return true se l'aggiornamento è riuscito, false altrimenti
     */
    @Override
    public boolean update(Team team) {
        Connection conn = null;
        try {
            conn = ConnessioneDatabase.getInstance().connection;
            conn.setAutoCommit(false);

            // Rimuovi tutti i membri esistenti
            String deleteMembri = "DELETE FROM team_membri WHERE nome_team = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteMembri)) {
                ps.setString(1, team.getNomeTeam());
                ps.executeUpdate();
            }

            // Aggiungi i nuovi membri
            String insertMembri = "INSERT INTO team_membri (nome_team, mail_utente) VALUES (?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertMembri)) {
                for (UtenteIscritto membro : team.getConcorrenti()) {
                    ps.setString(1, team.getNomeTeam());
                    ps.setString(2, membro.getMail());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * Cancella un team dal database.
     * Rimuove prima i membri per rispettare i vincoli di foreign key.
     *
     * @param team l'oggetto Team da cancellare
     * @return true se la cancellazione è riuscita, false altrimenti
     */
    @Override
    public boolean delete(Team team) {
        Connection conn = null;
        try {
            conn = ConnessioneDatabase.getInstance().connection;
            conn.setAutoCommit(false);

            // Rimuovi prima i membri (per rispettare i vincoli di foreign key)
            String deleteMembri = "DELETE FROM team_membri WHERE nome_team = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteMembri)) {
                ps.setString(1, team.getNomeTeam());
                ps.executeUpdate();
            }

            // Rimuovi il team
            String deleteTeam = "DELETE FROM team WHERE nome_team = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteTeam)) {
                ps.setString(1, team.getNomeTeam());
                ps.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * Aggiunge un membro a un team esistente.
     *
     * @param nomeTeam il nome del team a cui aggiungere il membro
     * @param utente l'utente da aggiungere al team
     * @return true se l'aggiunta è riuscita, false altrimenti
     */
    @Override
    public boolean aggiungiMembroAlTeam(String nomeTeam, UtenteIscritto utente) {
        String query = "INSERT INTO team_membri (nome_team, mail_utente) VALUES (?, ?)";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, nomeTeam);
            ps.setString(2, utente.getMail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Rimuove un membro da un team.
     *
     * @param nomeTeam il nome del team da cui rimuovere il membro
     * @param utente l'utente da rimuovere dal team
     * @return true se la rimozione è riuscita, false altrimenti
     */
    @Override
    public boolean rimuoviMembroDalTeam(String nomeTeam, UtenteIscritto utente) {
        String query = "DELETE FROM team_membri WHERE nome_team = ? AND mail_utente = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, nomeTeam);
            ps.setString(2, utente.getMail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Salva un invito di un team verso un utente.
     *
     * @param team il team che invia l'invito
     * @param invitato l'utente che riceve l'invito
     * @return true se l'invito è stato salvato, false altrimenti
     */
    @Override
    public boolean salvaInvito(Team team, UtenteIscritto invitato) {
        String query = "INSERT INTO inviti_team (nome_team, mail_utente_invitato) VALUES (?, ?)";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, team.getNomeTeam());
            ps.setString(2, invitato.getMail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Rimuove un invito specifico.
     *
     * @param team il team che aveva inviato l'invito
     * @param invitato l'utente che aveva ricevuto l'invito
     * @return true se l'invito è stato rimosso, false altrimenti
     */
    @Override
    public boolean rimuoviInvito(Team team, UtenteIscritto invitato) {
        String query = "DELETE FROM inviti_team WHERE nome_team = ? AND mail_utente_invitato = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, team.getNomeTeam());
            ps.setString(2, invitato.getMail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Recupera tutti gli inviti ricevuti da un utente.
     *
     * @param utente l'utente di cui recuperare gli inviti
     * @return lista dei team che hanno inviato inviti all'utente
     */
    @Override
    public List<Team> getInvitiPerUtente(UtenteIscritto utente) {
        List<Team> teams = new ArrayList<>();
        String query = "SELECT nome_team FROM inviti_team WHERE mail_utente_invitato = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, utente.getMail());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nomeTeam = rs.getString("nome_team");
                    Team team = findByNome(nomeTeam);
                    if (team != null) {
                        teams.add(team);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }

    /**
     * Verifica se esiste un invito specifico tra un team e un utente.
     *
     * @param team il team che ha inviato l'invito
     * @param invitato l'utente che ha ricevuto l'invito
     * @return true se l'invito esiste, false altrimenti
     */
    @Override
    public boolean esisteInvito(Team team, UtenteIscritto invitato) {
        String query = "SELECT 1 FROM inviti_team WHERE nome_team = ? AND mail_utente_invitato = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, team.getNomeTeam());
            ps.setString(2, invitato.getMail());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Rimuove tutti gli inviti ricevuti da un utente.
     * Utile quando un utente entra in un team e deve rifiutare automaticamente tutti gli altri inviti.
     *
     * @param utente l'utente di cui rimuovere tutti gli inviti
     */
    @Override
    public void rimuoviTuttiInvitiUtente(UtenteIscritto utente) {
        String query = "DELETE FROM inviti_team WHERE mail_utente_invitato = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, utente.getMail());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo privato per mappare un ResultSet a un oggetto Team con i suoi membri.
     * Carica automaticamente tutti i membri del team dal database.
     *
     * @param rs il ResultSet contenente i dati del team
     * @return l'oggetto Team con tutti i membri caricati
     * @throws SQLException se si verifica un errore durante la lettura dei dati
     */
    private Team mapTeamConMembri(ResultSet rs) throws SQLException {
        String nomeTeam = rs.getString("nome_team");
        Team team = new Team(nomeTeam);

        // Carica i membri del team
        String queryMembri = "SELECT mail_utente FROM team_membri WHERE nome_team = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(queryMembri)) {
            ps.setString(1, nomeTeam);
            try (ResultSet rsMembri = ps.executeQuery()) {
                while (rsMembri.next()) {
                    String emailMembro = rsMembri.getString("mail_utente");
                    UtenteIscritto membro = utenteDAO.findByMail(emailMembro);
                    if (membro != null) {
                        team.aggiungiConcorrente(membro);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return team;
    }
}