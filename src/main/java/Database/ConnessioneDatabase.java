package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe singleton per la gestione della connessione al database PostgreSQL.
 * Implementa il pattern Singleton per garantire che esista una sola istanza
 * della connessione al database nell'applicazione.
 *
 */
public class ConnessioneDatabase {
    private static ConnessioneDatabase instance;
    public Connection connection = null;
    private String nome = "postgres";
    private String password = "0000";
    private String url = "jdbc:postgresql://localhost:5432/db_progetto";
    private String driver = "org.postgresql.Driver";

    /**
     * Costruttore privato che inizializza la connessione al database.
     * Carica il driver PostgreSQL e stabilisce la connessione utilizzando
     * le credenziali configurate.
     *
     * @throws SQLException se si verifica un errore durante la connessione al database
     */
    private ConnessioneDatabase() throws SQLException {
        try {
            Class.forName(this.driver);
            this.connection = DriverManager.getConnection(this.url, this.nome, this.password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Restituisce l'istanza singleton della connessione al database.
     * Se l'istanza non esiste o la connessione è chiusa, ne crea una nuova.
     *
     * @return l'istanza singleton di ConnessioneDatabase
     * @throws SQLException se si verifica un errore durante la creazione della connessione
     */
    public static ConnessioneDatabase getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnessioneDatabase();
        } else if (instance.connection.isClosed()) {
            instance = new ConnessioneDatabase();
        }
        return instance;
    }

    /**
     * Restituisce l'oggetto Connection attuale.
     *
     * @return l'oggetto Connection per il database
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Restituisce la connessione al database, rinnovandola se è chiusa.
     * Verifica se la connessione è null o chiusa e in tal caso crea una nuova istanza.
     *
     * @return l'oggetto Connection attivo per il database
     * @throws SQLException se si verifica un errore durante il rinnovo della connessione
     */
    public Connection getConnectionRinnovaSeChiusa() throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
            instance = new ConnessioneDatabase();
        }
        return this.connection;
    }

}