package model;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

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


    public String getTitolo() {
        return titolo;
    }

    public String getSede() {
        return sede;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public int getNumeroMaxIscritti() {
        return numeroMaxIscritti;
    }

    public int getComponentMaxTeam() {
        return componentMaxTeam;
    }

    public Date getInizioIscrizioni() {
        return inizioIscrizioni;
    }

    public Date getFineIscrizioni() {
        return fineIscrizioni;
    }

    public String getDescrizioneProblema() {
        return descrizioneProblema;
    }

    // --- Setter ---
    public void setFineIscrizioni(Date fineIscrizioni) {
        this.fineIscrizioni = fineIscrizioni;
    }

    public void setInizioIscrizioni(Date inizioIscrizioni) {
        this.inizioIscrizioni = inizioIscrizioni;
    }

    public void setInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public void setFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public void setDescrizioneProblema(String descrizioneProblema) {
        this.descrizioneProblema = descrizioneProblema;
    }

    // --- Metodi per aggiunta team/giudici ---
    public void aggiungiTeam(Team team) {
        teams.add(team);
    }

    public void aggiungiGiudice(Giudice g) {
        giudici.add(g);
    }
    public void setNumeroMaxIscritti(int numeroMaxIscritti) {
        this.numeroMaxIscritti = numeroMaxIscritti;
    }

    public void setComponentMaxTeam(int componentMaxTeam) {
        this.componentMaxTeam = componentMaxTeam;
    }
}

