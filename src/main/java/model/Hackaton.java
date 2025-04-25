import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Hackaton {
    private String titolo;
    private String sede;
    private Date dataInizio;
    private Date dataFine;
    private int numeroMaxIscritti;
    private int componentMaxTeam;
    private Date inizioIscrizioni;
    private Date fineIscrizioni;
    private String descrizioneProblema;

    private List<Giudice> giudici = new ArrayList<>();
    private List<Team> teams = new ArrayList<>();

    public Hackaton(String titolo, String sede, Date dataInizio, Date dataFine,
                    int numeroMaxIscritti, int componentMaxTeam,
                    Date inizioIscrizioni, Date fineIscrizioni, String descrizioneProblema) {
        this.titolo = titolo;
        this.sede = sede;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.numeroMaxIscritti = numeroMaxIscritti;
        this.componentMaxTeam = componentMaxTeam;
        this.inizioIscrizioni = inizioIscrizioni;
        this.fineIscrizioni = fineIscrizioni;
        this.descrizioneProblema = descrizioneProblema;
    }

    public String getTitolo() {
        return titolo;
    }

    public void aggiungiTeam(Team team) {
        teams.add(team);
    }

    public void aggiungiGiudice(Giudice g) {
        giudici.add(g);
    }
}
