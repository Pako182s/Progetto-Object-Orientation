package DAO;

import model.Team;
import model.UtenteIscritto;
import java.util.Map;

/**
 * Interfaccia DAO per la gestione dei voti assegnati dai giudici ai team.
 * Fornisce operazioni CRUD per i voti e metodi di aggregazione per calcolare
 * medie e recuperare collezioni di voti.
 *
 */
public interface VotoDAO {

    /**
     * Salva un nuovo voto nel sistema.
     *
     * @param team il team a cui assegnare il voto
     * @param giudice il giudice che assegna il voto
     * @param voto il valore numerico del voto
     * @param commento il commento associato al voto
     * @return true se il voto è stato salvato con successo, false altrimenti
     * @throws IllegalArgumentException se uno dei parametri è null o il voto non è valido
     */
    boolean salvaVoto(Team team, UtenteIscritto giudice, int voto, String commento);

    /**
     * Aggiorna un voto esistente nel sistema.
     *
     * @param team il team di cui aggiornare il voto
     * @param giudice il giudice che ha assegnato il voto
     * @param voto il nuovo valore numerico del voto
     * @param commento il nuovo commento associato al voto
     * @return true se il voto è stato aggiornato con successo, false altrimenti
     * @throws IllegalArgumentException se uno dei parametri è null o il voto non è valido
     */
    boolean aggiornaVoto(Team team, UtenteIscritto giudice, int voto, String commento);

    /**
     * Recupera il voto assegnato da un giudice specifico a un team specifico.
     *
     * @param team il team di cui recuperare il voto
     * @param giudice il giudice che ha assegnato il voto
     * @return il valore del voto se presente, null se non esiste
     * @throws IllegalArgumentException se team o giudice sono null
     */
    Integer getVoto(Team team, UtenteIscritto giudice);

    /**
     * Recupera tutti i voti assegnati da un giudice specifico.
     *
     * @param giudice il giudice di cui recuperare i voti
     * @return una mappa che associa ogni team al voto assegnato dal giudice
     * @throws IllegalArgumentException se giudice è null
     */
    Map<Team, Integer> getVotiDiGiudice(UtenteIscritto giudice);

    /**
     * Recupera tutti i voti ricevuti da un team specifico.
     *
     * @param team il team di cui recuperare i voti
     * @return una mappa che associa ogni giudice al voto assegnato al team
     * @throws IllegalArgumentException se team è null
     */
    Map<UtenteIscritto, Integer> getVotiPerTeam(Team team);

    /**
     * Calcola la media dei voti ricevuti da un team.
     *
     * @param team il team di cui calcolare la media
     * @return la media aritmetica dei voti ricevuti dal team
     * @throws IllegalArgumentException se team è null
     * @throws ArithmeticException se il team non ha ricevuto alcun voto
     */
    double getMediaVotiTeam(Team team);

    /**
     * Verifica se esiste un voto assegnato da un giudice specifico a un team specifico.
     *
     * @param team il team da verificare
     * @param giudice il giudice da verificare
     * @return true se il voto esiste, false altrimenti
     * @throws IllegalArgumentException se team o giudice sono null
     */
    boolean esisteVoto(Team team, UtenteIscritto giudice);

    /**
     * Recupera il commento associato a un voto specifico.
     *
     * @param team il team di cui recuperare il commento
     * @param giudice il giudice che ha assegnato il voto
     * @return il commento associato al voto, null se non presente o se il voto non esiste
     * @throws IllegalArgumentException se team o giudice sono null
     */
    String getCommento(Team team, UtenteIscritto giudice);
}