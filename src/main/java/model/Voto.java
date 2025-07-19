package model;

import java.time.LocalDateTime;

/**
 * Classe che rappresenta un voto assegnato da un giudice a un team.
 * Contiene le informazioni sul team votato, il giudice che ha assegnato il voto,
 * il valore numerico del voto e il timestamp della votazione.
 *
 */
public class Voto {
    private Team team;
    private UtenteIscritto giudice;  // giudice è un utente iscritto con ruolo "giudice"
    private int valoreVoto;
    private LocalDateTime dataVoto;

    /**
     * Costruttore per creare un nuovo voto con timestamp automatico.
     * La data del voto viene impostata automaticamente al momento corrente.
     *
     * @param team il team a cui è assegnato il voto
     * @param giudice il giudice che assegna il voto
     * @param valoreVoto il valore numerico del voto
     */
    public Voto(Team team, UtenteIscritto giudice, int valoreVoto) {
        this.team = team;
        this.giudice = giudice;
        this.valoreVoto = valoreVoto;
        this.dataVoto = LocalDateTime.now();
    }

    /**
     * Costruttore per creare un voto con timestamp specificato.
     * Utile per ricreare voti da database o per test.
     *
     * @param team il team a cui è assegnato il voto
     * @param giudice il giudice che assegna il voto
     * @param valoreVoto il valore numerico del voto
     * @param dataVoto il timestamp specifico del voto
     */
    public Voto(Team team, UtenteIscritto giudice, int valoreVoto, LocalDateTime dataVoto) {
        this.team = team;
        this.giudice = giudice;
        this.valoreVoto = valoreVoto;
        this.dataVoto = dataVoto;
    }

    /**
     * Restituisce il team associato al voto.
     *
     * @return il team votato
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Imposta il team associato al voto.
     *
     * @param team il team da associare al voto
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * Restituisce il giudice che ha assegnato il voto.
     *
     * @return il giudice che ha votato
     */
    public UtenteIscritto getGiudice() {
        return giudice;
    }

    /**
     * Imposta il giudice che ha assegnato il voto.
     *
     * @param giudice il giudice da associare al voto
     */
    public void setGiudice(UtenteIscritto giudice) {
        this.giudice = giudice;
    }

    /**
     * Restituisce il valore numerico del voto.
     *
     * @return il valore del voto
     */
    public int getValoreVoto() {
        return valoreVoto;
    }

    /**
     * Imposta il valore numerico del voto.
     *
     * @param valoreVoto il valore del voto da impostare
     */
    public void setValoreVoto(int valoreVoto) {
        this.valoreVoto = valoreVoto;
    }

    /**
     * Restituisce la data e ora in cui è stato assegnato il voto.
     *
     * @return il timestamp del voto
     */
    public LocalDateTime getDataVoto() {
        return dataVoto;
    }

    /**
     * Imposta la data e ora del voto.
     *
     * @param dataVoto il timestamp da impostare
     */
    public void setDataVoto(LocalDateTime dataVoto) {
        this.dataVoto = dataVoto;
    }

    /**
     * Restituisce una rappresentazione testuale del voto.
     *
     * @return una stringa con le informazioni principali del voto
     */
    @Override
    public String toString() {
        return "Voto{" +
                "team=" + team.getNomeTeam() +
                ", giudice=" + giudice.getMail() +
                ", valoreVoto=" + valoreVoto +
                ", dataVoto=" + dataVoto +
                '}';
    }
}