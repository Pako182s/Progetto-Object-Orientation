package model;

/**
 * Classe che rappresenta un giudice dell'hackathon.
 * Estende UtenteIscritto aggiungendo funzionalità specifiche per la valutazione
 * come pubblicazione della descrizione del problema, assegnazione voti e commenti.
 *

 */
public class Giudice extends UtenteIscritto {

    /**
     * Pubblica la descrizione del problema che i team dovranno risolvere.
     * Questo metodo permette al giudice di definire e rendere disponibile
     * la traccia dell'hackathon per tutti i partecipanti.
     */
    public void pubblicaDescrizioneProblema() {
        // Implementazione
    }

    /**
     * Assegna un voto a un team partecipante.
     *
     * @param valore il valore numerico del voto da assegnare
     */
    public void assegnaVoto(int valore) {

    }

    /**
     * Aggiunge un commento al documento di un team.
     * Permette al giudice di fornire feedback dettagliato sul lavoro svolto.
     *
     * @param messaggio il commento da aggiungere al documento
     */
    public void commentoDocumento(String messaggio) {

    }
}