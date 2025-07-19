package model;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe che rappresenta un hackathon.
 * Contiene tutte le informazioni relative all'evento come date, sede, partecipanti,
 * team iscritti, giudici e configurazioni dell'evento.
 *
 */
public class Hackaton {
    private String titolo;
    private String sede;
    private Date dataInizio;
    private Date dataFine;          // Fine evento
    private int numeroMaxIscritti;
    private int componentMaxTeam;
    private Date inizioIscrizioni;
    private Date fineIscrizioni;    // Chiusura iscrizioni
    private String descrizioneProblema;

    private List<Giudice> giudici = new ArrayList<>();
    private List<Team> teams = new ArrayList<>();

    /**
     * Costruttore per creare un nuovo hackathon.
     *
     * @param titolo il titolo dell'hackathon
     * @param sede la sede dove si svolge l'evento
     * @param dataInizio la data di inizio dell'evento
     * @param dataFine la data di fine dell'evento
     * @param numeroMaxIscritti il numero massimo di partecipanti
     * @param componentMaxTeam il numero massimo di componenti per team
     * @param inizioIscrizioni la data di apertura delle iscrizioni
     * @param fineIscrizioni la data di chiusura delle iscrizioni
     */
    public Hackaton(String titolo, String sede, Date dataInizio, Date dataFine,
                    int numeroMaxIscritti, int componentMaxTeam,
                    Date inizioIscrizioni, Date fineIscrizioni) {
        this.titolo = titolo;
        this.sede = sede;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.numeroMaxIscritti = numeroMaxIscritti;
        this.componentMaxTeam = componentMaxTeam;
        this.inizioIscrizioni = inizioIscrizioni;
        this.fineIscrizioni = fineIscrizioni;
    }

    /**
     * Restituisce il titolo dell'hackathon.
     *
     * @return il titolo dell'evento
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Restituisce la sede dell'hackathon.
     *
     * @return la sede dell'evento
     */
    public String getSede() {
        return sede;
    }

    /**
     * Restituisce la data di inizio dell'hackathon.
     *
     * @return la data di inizio dell'evento
     */
    public Date getDataInizio() {
        return dataInizio;
    }

    /**
     * Restituisce la data di fine dell'hackathon.
     *
     * @return la data di fine dell'evento
     */
    public Date getDataFine() {
        return dataFine;
    }

    /**
     * Restituisce il numero massimo di iscritti all'hackathon.
     *
     * @return il numero massimo di partecipanti
     */
    public int getNumeroMaxIscritti() {
        return numeroMaxIscritti;
    }

    /**
     * Restituisce il numero massimo di componenti per team.
     *
     * @return il numero massimo di membri per team
     */
    public int getComponentMaxTeam() {
        return componentMaxTeam;
    }

    /**
     * Restituisce la data di inizio delle iscrizioni.
     *
     * @return la data di apertura delle iscrizioni
     */
    public Date getInizioIscrizioni() {
        return inizioIscrizioni;
    }

    /**
     * Restituisce la data di fine delle iscrizioni.
     *
     * @return la data di chiusura delle iscrizioni
     */
    public Date getFineIscrizioni() {
        return fineIscrizioni;
    }

    /**
     * Restituisce la descrizione del problema dell'hackathon.
     *
     * @return la descrizione del problema da risolvere
     */
    public String getDescrizioneProblema() {
        return descrizioneProblema;
    }

    /**
     * Imposta la data di fine delle iscrizioni.
     *
     * @param fineIscrizioni la nuova data di chiusura delle iscrizioni
     */
    public void setFineIscrizioni(Date fineIscrizioni) {
        this.fineIscrizioni = fineIscrizioni;
    }

    /**
     * Imposta la data di inizio delle iscrizioni.
     *
     * @param inizioIscrizioni la nuova data di apertura delle iscrizioni
     */
    public void setInizioIscrizioni(Date inizioIscrizioni) {
        this.inizioIscrizioni = inizioIscrizioni;
    }

    /**
     * Imposta la data di inizio dell'hackathon.
     *
     * @param dataInizio la nuova data di inizio dell'evento
     */
    public void setInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    /**
     * Imposta la data di fine dell'hackathon.
     *
     * @param dataFine la nuova data di fine dell'evento
     */
    public void setFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    /**
     * Imposta la descrizione del problema dell'hackathon.
     *
     * @param descrizioneProblema la nuova descrizione del problema
     */
    public void setDescrizioneProblema(String descrizioneProblema) {
        this.descrizioneProblema = descrizioneProblema;
    }

    /**
     * Aggiunge un team alla lista dei partecipanti dell'hackathon.
     *
     * @param team il team da aggiungere all'evento
     */
    public void aggiungiTeam(Team team) {
        teams.add(team);
    }

    /**
     * Aggiunge un giudice alla lista dei giudici dell'hackathon.
     *
     * @param g il giudice da aggiungere all'evento
     */
    public void aggiungiGiudice(Giudice g) {
        giudici.add(g);
    }

    /**
     * Imposta il numero massimo di iscritti all'hackathon.
     *
     * @param numeroMaxIscritti il nuovo numero massimo di partecipanti
     */
    public void setNumeroMaxIscritti(int numeroMaxIscritti) {
        this.numeroMaxIscritti = numeroMaxIscritti;
    }

    /**
     * Imposta il numero massimo di componenti per team.
     *
     * @param componentMaxTeam il nuovo numero massimo di membri per team
     */
    public void setComponentMaxTeam(int componentMaxTeam) {
        this.componentMaxTeam = componentMaxTeam;
    }
}