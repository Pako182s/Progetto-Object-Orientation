package DAO;

import model.Hackaton;
import java.util.List;

/**
 * Interfaccia DAO per la gestione degli hackathon nel sistema.
 * Fornisce operazioni CRUD (Create, Read, Update, Delete) per gli oggetti Hackaton.
 */
public interface HackatonDAO {

    /**
     * Cerca un hackathon per titolo.
     *
     * @param titolo il titolo dell'hackathon da cercare
     * @return l'hackathon trovato, null se non presente
     */
    Hackaton findByTitolo(String titolo);

    /**
     * Recupera tutti gli hackathon presenti nel sistema.
     *
     * @return la lista di tutti gli hackathon, lista vuota se non ce ne sono
     */
    List<Hackaton> findAll();

    /**
     * Salva un nuovo hackathon nel sistema.
     *
     * @param h l'hackathon da salvare
     * @return true se il salvataggio è riuscito, false altrimenti
     */
    boolean save(Hackaton h);

    /**
     * Aggiorna un hackathon esistente nel sistema.
     *
     * @param h l'hackathon da aggiornare
     * @return true se l'aggiornamento è riuscito, false altrimenti
     */
    boolean update(Hackaton h);

    /**
     * Elimina un hackathon dal sistema.
     *
     * @param h l'hackathon da eliminare
     * @return true se l'eliminazione è riuscita, false altrimenti
     */
    boolean delete(Hackaton h);
}