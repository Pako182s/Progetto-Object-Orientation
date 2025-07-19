package model;

/**
 * Classe che rappresenta un utente iscritto all'hackathon.
 * Contiene le informazioni base dell'utente come nome, password, email
 * e ruolo nell'evento (utente, giudice, organizzatore).
 *
 */
public class UtenteIscritto {
    protected String nome;
    protected String password;
    protected String mail;
    private String ruolo;  // NON inizializzare qui!

    /**
     * Costruttore per creare un nuovo utente iscritto.
     * Il ruolo viene automaticamente impostato come "utente".
     *
     * @param n il nome dell'utente
     * @param p la password dell'utente
     * @param m l'email dell'utente
     */
    public UtenteIscritto(String n, String p, String m) {
        nome = n;
        password = p;
        mail = m;
        ruolo = "utente";  // Inizializza solo nel costruttore
    }

    /**
     * Costruttore vuoto per creare un utente senza parametri.
     * Il ruolo viene automaticamente impostato come "utente".
     */
    public UtenteIscritto() {
        ruolo = "utente";  // Inizializza anche nel costruttore vuoto
    }

    /**
     * Restituisce il nome dell'utente.
     *
     * @return il nome dell'utente
     */
    public String getNome() {
        return nome;
    }

    /**
     * Restituisce la password dell'utente.
     *
     * @return la password dell'utente
     */
    public String getPassword() {
        return password;
    }

    /**
     * Restituisce l'email dell'utente.
     *
     * @return l'email dell'utente
     */
    public String getMail() {
        return mail;
    }

    /**
     * Imposta l'email dell'utente.
     *
     * @param mail la nuova email da impostare
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Imposta il nome dell'utente.
     *
     * @param nome il nuovo nome da impostare
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce il ruolo dell'utente nell'hackathon.
     * I ruoli possibili sono: "utente", "giudice", "organizzatore".
     *
     * @return il ruolo dell'utente
     */
    public String getRuolo() {
        return ruolo;
    }

    /**
     * Imposta il ruolo dell'utente nell'hackathon.
     *
     * @param ruolo il nuovo ruolo da assegnare all'utente
     */
    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }
}