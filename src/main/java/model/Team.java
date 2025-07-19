package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta un team partecipante all'hackathon.
 * Gestisce le informazioni del team, i suoi membri e i documenti associati.
 *
 */
public class Team {
    private String nomeTeam;
    private List<UtenteIscritto> concorrenti = new ArrayList<>();
    private List<Documento> documenti = new ArrayList<>();

    /**
     * Costruttore per creare un nuovo team.
     *
     * @param nomeTeam il nome identificativo del team
     */
    public Team(String nomeTeam) {
        this.nomeTeam = nomeTeam;
    }

    /**
     * Aggiunge un concorrente al team.
     *
     * @param u l'utente da aggiungere come membro del team
     */
    public void aggiungiConcorrente(UtenteIscritto u) {
        concorrenti.add(u);
    }

    /**
     * Aggiunge un documento al team.
     *
     * @param d il documento da associare al team
     */
    public void aggiungiDocumento(Documento d) {
        documenti.add(d);
    }

    /**
     * Restituisce il nome del team.
     *
     * @return il nome identificativo del team
     */
    public String getNomeTeam() {
        return nomeTeam;
    }

    /**
     * Restituisce la lista dei concorrenti del team.
     *
     * @return la lista degli utenti che fanno parte del team
     */
    public List<UtenteIscritto> getConcorrenti() {
        return concorrenti;
    }

    /**
     * Restituisce la lista dei membri del team.
     * Metodo alias per getConcorrenti() per compatibilità.
     *
     * @return la lista degli utenti che fanno parte del team
     */
    public List<UtenteIscritto> getMembri() {
        return getConcorrenti();
    }

}