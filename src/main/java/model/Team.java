import java.util.List;
import java.util.ArrayList;

public class Team {
    private String nomeTeam;
    private List<Concorrente> concorrenti = new ArrayList<>();
    private List<Documento> documenti = new ArrayList<>();

    public Team(String nomeTeam) {
        this.nomeTeam = nomeTeam;
    }

    public void aggiungiConcorrente(Concorrente c) {
        concorrenti.add(c);
    }

    public void aggiungiDocumento(Documento d) {
        documenti.add(d);
    }

    public String getNomeTeam() {
        return nomeTeam;
    }

    public List<Concorrente> getConcorrenti() {
        return concorrenti;
    }
}
