package model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String nomeTeam;
    private List<UtenteIscritto> concorrenti = new ArrayList<>();
    private List<Documento> documenti = new ArrayList<>();

    public Team(String nomeTeam) {
        this.nomeTeam = nomeTeam;
    }

    public void aggiungiConcorrente(UtenteIscritto u) {
        concorrenti.add(u);
    }

    public void aggiungiDocumento(Documento d) {
        documenti.add(d);
    }

    public String getNomeTeam() {
        return nomeTeam;
    }

    public List<UtenteIscritto> getConcorrenti() {
        return concorrenti;
    }


    public List<UtenteIscritto> getMembri() {
        return getConcorrenti();
    }
}
