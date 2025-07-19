package DAO;

import model.UtenteIscritto;
import java.util.List;

/**
 * Interfaccia DAO per la gestione degli utenti iscritti nel sistema hackathon.
 * Fornisce operazioni CRUD (Create, Read, Update, Delete) per gli oggetti UtenteIscritto.
 */
public interface UtenteIscrittoDAO {

    /**
     * Cerca un utente iscritto per email.
     *
     * @param mail l'email dell'utente da cercare
     * @return l'utente trovato, null se non presente
     */
    UtenteIscritto findByMail(String mail);

    /**
     * Recupera tutti gli utenti iscritti presenti nel sistema.
     *
     * @return la lista di tutti gli utenti iscritti, lista vuota se non ce ne sono
     */
    List<UtenteIscritto> findAll();

    /**
     * Salva un nuovo utente iscritto nel sistema.
     *
     * @param utente l'utente da salvare
     * @return true se il salvataggio è riuscito, false altrimenti
     */
    boolean save(UtenteIscritto utente);

    /**
     * Aggiorna un utente iscritto esistente nel sistema.
     *
     * @param utente l'utente da aggiornare
     * @return true se l'aggiornamento è riuscito, false altrimenti
     */
    boolean update(UtenteIscritto utente);

    /**
     * Elimina un utente iscritto dal sistema.
     *
     * @param utente l'utente da eliminare
     * @return true se l'eliminazione è riuscita, false altrimenti
     */
    boolean delete(UtenteIscritto utente);
}