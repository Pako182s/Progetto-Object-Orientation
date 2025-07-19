package DAO;

import model.Team;
import model.UtenteIscritto;
import java.util.List;

/**
 * Interfaccia DAO per la gestione dei team nel sistema hackathon.
 * Fornisce operazioni CRUD per i team e metodi per gestire i membri e gli inviti.
 */
public interface TeamDAO {

    /**
     * Cerca un team per nome.
     *
     * @param nomeTeam il nome del team da cercare
     * @return il team trovato, null se non presente
     */
    Team findByNome(String nomeTeam);

    /**
     * Recupera tutti i team presenti nel sistema.
     *
     * @return la lista di tutti i team, lista vuota se non ce ne sono
     */
    List<Team> findAll();

    /**
     * Trova tutti i team di cui un utente è membro.
     *
     * @param utente l'utente di cui cercare i team
     * @return la lista dei team di cui l'utente è membro, lista vuota se non ce ne sono
     */
    List<Team> findByMembro(UtenteIscritto utente);

    /**
     * Salva un nuovo team nel sistema.
     *
     * @param team il team da salvare
     * @return true se il salvataggio è riuscito, false altrimenti
     */
    boolean save(Team team);

    /**
     * Aggiorna un team esistente nel sistema.
     *
     * @param team il team da aggiornare
     * @return true se l'aggiornamento è riuscito, false altrimenti
     */
    boolean update(Team team);

    /**
     * Elimina un team dal sistema.
     *
     * @param team il team da eliminare
     * @return true se l'eliminazione è riuscita, false altrimenti
     */
    boolean delete(Team team);

    /**
     * Aggiunge un membro a un team esistente.
     *
     * @param nomeTeam il nome del team a cui aggiungere il membro
     * @param utente l'utente da aggiungere al team
     * @return true se l'aggiunta è riuscita, false altrimenti
     */
    boolean aggiungiMembroAlTeam(String nomeTeam, UtenteIscritto utente);

    /**
     * Rimuove un membro da un team.
     *
     * @param nomeTeam il nome del team da cui rimuovere il membro
     * @param utente l'utente da rimuovere dal team
     * @return true se la rimozione è riuscita, false altrimenti
     */
    boolean rimuoviMembroDalTeam(String nomeTeam, UtenteIscritto utente);

    /**
     * Salva un invito per un utente a unirsi a un team.
     *
     * @param team il team che invia l'invito
     * @param invitato l'utente che riceve l'invito
     * @return true se l'invito è stato salvato con successo, false altrimenti
     */
    boolean salvaInvito(Team team, UtenteIscritto invitato);

    /**
     * Rimuove un invito specifico per un utente.
     *
     * @param team il team che ha inviato l'invito
     * @param invitato l'utente che aveva ricevuto l'invito
     * @return true se l'invito è stato rimosso con successo, false altrimenti
     */
    boolean rimuoviInvito(Team team, UtenteIscritto invitato);

    /**
     * Recupera tutti gli inviti ricevuti da un utente.
     *
     * @param utente l'utente di cui recuperare gli inviti
     * @return la lista dei team che hanno invitato l'utente, lista vuota se non ce ne sono
     */
    List<Team> getInvitiPerUtente(UtenteIscritto utente);

    /**
     * Verifica se esiste un invito da un team per un utente specifico.
     *
     * @param team il team che ha inviato l'invito
     * @param invitato l'utente che dovrebbe aver ricevuto l'invito
     * @return true se l'invito esiste, false altrimenti
     */
    boolean esisteInvito(Team team, UtenteIscritto invitato);

    /**
     * Rimuove tutti gli inviti ricevuti da un utente.
     * Utile quando un utente si unisce a un team e tutti gli altri inviti diventano invalidi.
     *
     * @param utente l'utente di cui rimuovere tutti gli inviti
     */
    void rimuoviTuttiInvitiUtente(UtenteIscritto utente);
}