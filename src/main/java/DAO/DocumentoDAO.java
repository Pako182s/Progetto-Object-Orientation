package DAO;

import model.Team;
import model.Hackaton;

/**
 * Interfaccia DAO per la gestione dei documenti nel sistema hackathon.
 * Fornisce metodi per gestire i documenti dei team e il problema dell'hackathon.
 */
public interface DocumentoDAO {

    /**
     * Recupera il documento associato a un team specifico.
     *
     * @param team il team di cui recuperare il documento
     * @return il contenuto del documento come stringa, null se non presente
     */
    String getDocumentoTeam(Team team);

    /**
     * Salva o aggiorna il documento di un team.
     *
     * @param team il team per cui salvare il documento
     * @param contenuto il contenuto del documento da salvare
     * @return true se il salvataggio è riuscito, false altrimenti
     */
    boolean salvaDocumentoTeam(Team team, String contenuto);

    /**
     * Verifica se esiste un documento per il team specificato.
     *
     * @param team il team da verificare
     * @return true se esiste un documento per il team, false altrimenti
     */
    boolean esisteDocumentoTeam(Team team);

    /**
     * Recupera il problema dell'hackathon impostato dai giudici.
     *
     * @param hackaton l'hackathon di cui recuperare il problema
     * @return il testo del problema come stringa, null se non presente
     */
    String getProblemaHackathon(Hackaton hackaton);

    /**
     * Salva o aggiorna il problema dell'hackathon.
     *
     * @param hackaton l'hackathon per cui salvare il problema
     * @param problema il testo del problema da salvare
     * @return true se il salvataggio è riuscito, false altrimenti
     */
    boolean salvaProblemaHackathon(Hackaton hackaton, String problema);

    /**
     * Verifica se esiste un problema per l'hackathon specificato.
     *
     * @param hackaton l'hackathon da verificare
     * @return true se esiste un problema per l'hackathon, false altrimenti
     */
    boolean esisteProblemaHackathon(Hackaton hackaton);
}