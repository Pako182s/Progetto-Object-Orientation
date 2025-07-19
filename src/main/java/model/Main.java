package model;

import Database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

/**
 * Classe principale per il test dell'applicazione hackathon.
 * Contiene il metodo main per verificare la connessione al database
 * e creare oggetti di esempio per testare il funzionamento del sistema.
 *

 */
public class Main {

    /**
     * Metodo principale dell'applicazione.
     * Testa la connessione al database e crea oggetti di esempio
     * per verificare il corretto funzionamento delle classi del modello.
     *
     * @param args argomenti della riga di comando (non utilizzati)
     */
    public static void main(String[] args) {

        try {
            Connection conn = ConnessioneDatabase.getInstance().getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Connessione al database riuscita!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Errore di connessione al database:");
            e.printStackTrace();
        }


        // Il tuo codice esistente:
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