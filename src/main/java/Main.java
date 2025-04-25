//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Crea l'Hackaton
        Hackaton hackaton = new Hackaton("Hackathon 2025", "Milano", new Date(), new Date(),
                100, 5, new Date(), new Date(), "Costruire un'app utile alla comunità");

        // Organizzatore
        Organizzatore organizzatore = new Organizzatore();
        organizzatore.nome = "Luca";
        organizzatore.mail = "luca@hack.it";

        // Giudice
        Giudice giudice = new Giudice();
        giudice.nome = "Giulia";
        giudice.mail = "giulia@giudice.it";
        giudice.pubblicaDescrizioneProblema();

        // Concorrenti
        Concorrente concorrente1 = new Concorrente();
        concorrente1.nome = "Fernando";
        concorrente1.mail = "fernando@concorrente.it";

        Concorrente concorrente2 = new Concorrente();
        concorrente2.nome = "Ilaria";
        concorrente2.mail = "ilaria@concorrente.it";

        // Team
        Team team = new Team("CodeHeroes");
        team.aggiungiConcorrente(concorrente1);
        team.aggiungiConcorrente(concorrente2);

        // Documento
        Documento documento = new Documento("App per gestione rifiuti", "Ottima idea!");
        team.aggiungiDocumento(documento);

        // Hackaton setup
        hackaton.aggiungiTeam(team);
        hackaton.aggiungiGiudice(giudice);

        // Giudice assegna voto
        giudice.assegnaVoto(9);

        System.out.println("Hackaton: " + hackaton.getTitolo());
        System.out.println("Team: " + team.getNomeTeam());
        System.out.println("Partecipanti: ");
        for (Concorrente c : team.getConcorrenti()) {
            System.out.println("- " + c.nome);
        }
    }
}
