package model;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Hackaton hackaton = new Hackaton("Hackathon 2025", "Milano", new Date(), new Date(),
                100, 5, new Date(), new Date());


        Organizzatore organizzatore = new Organizzatore();
        organizzatore.nome = "Luca";
        organizzatore.mail = "luca@hack.it";


        Giudice giudice = new Giudice();
        giudice.nome = "Giulia";
        giudice.mail = "giulia@giudice.it";
        giudice.pubblicaDescrizioneProblema();


        UtenteIscritto concorrente1 = new UtenteIscritto("Fernando", "", "fernando@concorrente.it");
        UtenteIscritto concorrente2 = new UtenteIscritto("Ilaria", "", "ilaria@concorrente.it");


        Team team = new Team("CodeHeroes");
        team.aggiungiConcorrente(concorrente1);
        team.aggiungiConcorrente(concorrente2);


        Documento documento = new Documento("App per gestione rifiuti", "Ottima idea!");
        team.aggiungiDocumento(documento);


        hackaton.aggiungiTeam(team);
        hackaton.aggiungiGiudice(giudice);


        giudice.assegnaVoto(9);

        System.out.println("Hackaton: " + hackaton.getTitolo());
        System.out.println("Team: " + team.getNomeTeam());
        System.out.println("Partecipanti: ");
        for (UtenteIscritto u : team.getConcorrenti()) {
            System.out.println("- " + u.getNome());
        }
    }
}
