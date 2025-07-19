package Controller;

import DAO.HackatonDAO;
import DAO.UtenteIscrittoDAO;
import DAO.TeamDAO;
import DAO.VotoDAO;
import DAO.DocumentoDAO;
import ImplementazioniPostresDAO.PostgresHackatonDAO;
import ImplementazioniPostresDAO.PostgresUtenteIscrittoDAO;
import ImplementazioniPostresDAO.PostgresTeamDAO;
import ImplementazioniPostresDAO.PostgresVotoDAO;
import ImplementazioniPostresDAO.PostgresDocumentoDAO;
import model.UtenteIscritto;
import model.Hackaton;
import model.Team;

import java.util.*;

/**
 * Classe Controller principale per la gestione dell'hackathon.
 * Coordina le operazioni tra i diversi componenti del sistema,
 * gestendo utenti, team, voti e documenti.
 */
public class Controller {
    private List<UtenteIscritto> utentiIscritti = new ArrayList<>();
    private UtenteIscritto utenteAutenticato;
    private Hackaton hackaton;
    private HackatonDAO hackatonDAO = new PostgresHackatonDAO();
    private UtenteIscrittoDAO utenteDAO = new PostgresUtenteIscrittoDAO();
    private TeamDAO teamDAO = new PostgresTeamDAO();
    private VotoDAO votoDAO = new PostgresVotoDAO();
    private DocumentoDAO documentoDAO = new PostgresDocumentoDAO();
    private String problemaGiudice = "";
    private Map<UtenteIscritto, Team> teamPerUtente = new HashMap<>();
    private List<Team> listaTeam = new ArrayList<>();

    /**
     * Costruttore del Controller.
     * Inizializza il sistema caricando i dati dal database:
     * - Hackathon esistente o ne crea uno nuovo
     * - Utenti registrati dal database
     * - Team esistenti e ricostruisce la mappa utente-team
     * - Problema dell'hackathon dal database
     */
    public Controller() {
        // Carica hackathon dal DB oppure crealo
        hackaton = hackatonDAO.findByTitolo("Hackathon 2025");

        if (hackaton == null) {
            Calendar cal = Calendar.getInstance();
            Date apertura = cal.getTime();

            cal.add(Calendar.DAY_OF_MONTH, 2);
            Date chiusura = cal.getTime();

            cal.add(Calendar.DAY_OF_MONTH, 2);
            Date inizioEvento = cal.getTime();

            cal.add(Calendar.DAY_OF_MONTH, 2);
            Date fineEvento = cal.getTime();

            hackaton = new Hackaton(
                    "Hackathon 2025",
                    "Napoli",
                    inizioEvento,
                    fineEvento,
                    20,
                    3,
                    apertura,
                    chiusura
            );
            hackatonDAO.save(hackaton);
        }

        // Carica utenti dal DB tramite DAO
        utentiIscritti = new ArrayList<>(utenteDAO.findAll());

        // Se vuoi, puoi creare un admin/giudice di default se non esiste
        boolean giudicePresente = utentiIscritti.stream()
                .anyMatch(u -> "giudice".equals(u.getRuolo()));

        if (!giudicePresente) {
            UtenteIscritto giudice = new UtenteIscritto("Giudice", "1234", "g@mail.com");
            giudice.setRuolo("giudice");
            utenteDAO.save(giudice);
            utentiIscritti.add(giudice);
        }

        // Carica i team dal database
        listaTeam = new ArrayList<>(teamDAO.findAll());

        // Ricostruisci la mappa teamPerUtente usando il confronto per email
        teamPerUtente.clear();
        for (Team team : listaTeam) {
            for (UtenteIscritto membroDelTeam : team.getConcorrenti()) {
                // Trova l'utente corrispondente nella lista utentiIscritti
                for (UtenteIscritto utenteInLista : utentiIscritti) {
                    if (utenteInLista.getMail().equals(membroDelTeam.getMail())) {
                        teamPerUtente.put(utenteInLista, team);
                        break;
                    }
                }
            }
        }

        // Debug: stampa la mappa ricostruita
        System.out.println("Mappa teamPerUtente ricostruita:");
        for (Map.Entry<UtenteIscritto, Team> entry : teamPerUtente.entrySet()) {
            System.out.println("Utente: " + entry.getKey().getMail() + " -> Team: " + entry.getValue().getNomeTeam());
        }

        // Carica il problema dal database
        problemaGiudice = documentoDAO.getProblemaHackathon(hackaton);
    }

    /**
     * Restituisce l'utente attualmente autenticato nel sistema.
     *
     * @return l'utente autenticato, null se nessuno è autenticato
     */
    public UtenteIscritto getUtenteAutenticato() {
        return utenteAutenticato;
    }

    /**
     * Autentica un utente verificando email e password.
     *
     * @param mail l'email dell'utente
     * @param password la password dell'utente
     * @return true se l'autenticazione è riuscita, false altrimenti
     */
    public boolean autenticaUtente(String mail, String password) {
        UtenteIscritto utente = utenteDAO.findByMail(mail);
        if (utente != null && utente.getPassword().equals(password)) {
            utenteAutenticato = utente;
            return true;
        }
        return false;
    }

    /**
     * Registra un nuovo utente nel sistema.
     *
     * @param nome il nome dell'utente
     * @param password la password dell'utente
     * @param mail l'email dell'utente
     * @return true se la registrazione è riuscita, false altrimenti
     */
    public boolean setUtenteIscritto(String nome, String password, String mail) {
        UtenteIscritto nuovoUtente = new UtenteIscritto(nome, password, mail);
        boolean saved = utenteDAO.save(nuovoUtente);
        if (saved) {
            utentiIscritti.add(nuovoUtente);
        }
        return saved;
    }

    /**
     * Restituisce l'hackathon attualmente gestito dal sistema.
     *
     * @return l'oggetto Hackaton
     */
    public Hackaton getHackaton() {
        return hackaton;
    }

    /**
     * Verifica se le credenziali fornite corrispondono all'organizzatore.
     *
     * @param email l'email da verificare
     * @param password la password da verificare
     * @return true se le credenziali sono dell'organizzatore, false altrimenti
     */
    public boolean isOrganizzatore(String email, String password) {
        return email.equals("admin@hack.it") && password.equals("admin");
    }

    /**
     * Restituisce la lista di tutti gli utenti registrati nel sistema.
     *
     * @return la lista degli utenti registrati
     */
    public List<UtenteIscritto> getUtentiRegistrati() {
        return utentiIscritti;
    }

    /**
     * Promuove un utente al ruolo di giudice.
     *
     * @param utente l'utente da promuovere
     */
    public void promuoviAGiudice(UtenteIscritto utente) {
        utente.setRuolo("giudice");
        utenteDAO.update(utente); // aggiorna anche nel DB
    }

    /**
     * Avvia le iscrizioni per l'hackathon impostando le date.
     * Le date vengono calcolate automaticamente a partire da oggi.
     */
    public void avviaIscrizioni() {
        Calendar cal = Calendar.getInstance();
        Date apertura = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, 2);
        Date chiusura = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, 2);
        Date inizioEvento = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, 2);
        Date fineEvento = cal.getTime();

        hackaton.setInizioIscrizioni(apertura);
        hackaton.setFineIscrizioni(chiusura);
        hackaton.setInizio(inizioEvento);
        hackaton.setFine(fineEvento);

        hackatonDAO.update(hackaton);
    }

    /**
     * Restituisce il problema dell'hackathon impostato dal giudice.
     *
     * @return il testo del problema
     */
    public String getProblemaGiudice() {
        return problemaGiudice;
    }

    /**
     * Imposta il problema dell'hackathon e lo salva nel database.
     *
     * @param problema il testo del problema da impostare
     */
    public void setProblemaGiudice(String problema) {
        this.problemaGiudice = problema;
        // Salva nel database
        documentoDAO.salvaProblemaHackathon(hackaton, problema);
    }

    /**
     * Verifica se un utente appartiene già a un team.
     *
     * @param utente l'utente da verificare
     * @return true se l'utente appartiene a un team, false altrimenti
     */
    public boolean utenteHaTeam(UtenteIscritto utente) {
        // Prima controlla nella mappa teamPerUtente
        for (Map.Entry<UtenteIscritto, Team> entry : teamPerUtente.entrySet()) {
            if (entry.getKey().getMail().equals(utente.getMail())) {
                return true;
            }
        }

        // Poi cerca direttamente nei team caricati usando l'email
        for (Team team : listaTeam) {
            for (UtenteIscritto membro : team.getConcorrenti()) {
                if (membro.getMail().equals(utente.getMail())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Restituisce il team di appartenenza di un utente.
     *
     * @param utente l'utente di cui cercare il team
     * @return il team dell'utente, null se non appartiene a nessun team
     */
    public Team getTeamDiUtente(UtenteIscritto utente) {
        // Cerca direttamente nei team caricati usando l'email
        for (Team team : listaTeam) {
            for (UtenteIscritto membro : team.getConcorrenti()) {
                if (membro.getMail().equals(utente.getMail())) {
                    return team;
                }
            }
        }
        return null;
    }

    /**
     * Restituisce la lista di tutti i team presenti nell'hackathon.
     *
     * @return la lista dei team
     */
    public List<Team> getListaTeam() {
        return listaTeam;
    }

    /**
     * Crea un nuovo team con il nome specificato e aggiunge il creatore come primo membro.
     *
     * @param nomeTeam il nome del team da creare
     * @param creatore l'utente che crea il team
     * @return true se il team è stato creato con successo, false altrimenti
     */
    public boolean creaTeam(String nomeTeam, UtenteIscritto creatore) {
        if (utenteHaTeam(creatore)) return false;

        Team nuovoTeam = new Team(nomeTeam);
        nuovoTeam.aggiungiConcorrente(creatore);

        // Salva nel database
        boolean salvato = teamDAO.save(nuovoTeam);
        if (salvato) {
            listaTeam.add(nuovoTeam);
            teamPerUtente.put(creatore, nuovoTeam);
            return true;
        }
        return false;
    }

    /**
     * Invia un invito per unirsi a un team.
     *
     * @param mittente l'utente che invia l'invito (deve appartenere a un team)
     * @param invitato l'utente che riceve l'invito (non deve appartenere a un team)
     * @return true se l'invito è stato inviato con successo, false altrimenti
     */
    public boolean invitaNelTeam(UtenteIscritto mittente, UtenteIscritto invitato) {
        if (!utenteHaTeam(mittente)) return false;
        if (utenteHaTeam(invitato)) return false;

        Team teamMittente = getTeamDiUtente(mittente);
        if (teamMittente == null) return false;

        // Verifica se l'invito esiste già nel database
        if (teamDAO.esisteInvito(teamMittente, invitato)) {
            return false;
        }

        // Salva l'invito nel database
        return teamDAO.salvaInvito(teamMittente, invitato);
    }

    /**
     * Restituisce tutti gli inviti ricevuti da un utente.
     *
     * @param utente l'utente di cui cercare gli inviti
     * @return la lista dei team che hanno invitato l'utente
     */
    public List<Team> getInvitiPerUtente(UtenteIscritto utente) {
        // Carica gli inviti dal database
        return teamDAO.getInvitiPerUtente(utente);
    }

    /**
     * Accetta un invito per unirsi a un team.
     *
     * @param utente l'utente che accetta l'invito
     * @param team il team a cui unirsi
     * @return true se l'invito è stato accettato con successo, false altrimenti
     */
    public boolean accettaInvito(UtenteIscritto utente, Team team) {
        if (utenteHaTeam(utente)) return false;

        // Verifica che l'invito esista nel database
        if (!teamDAO.esisteInvito(team, utente)) {
            return false;
        }

        // Aggiungi il membro al team nel database
        boolean aggiunto = teamDAO.aggiungiMembroAlTeam(team.getNomeTeam(), utente);
        if (aggiunto) {
            // Trova il team nella lista locale e aggiornalo
            for (Team t : listaTeam) {
                if (t.getNomeTeam().equals(team.getNomeTeam())) {
                    t.aggiungiConcorrente(utente);
                    teamPerUtente.put(utente, t);

                    // Debug
                    System.out.println("Utente " + utente.getMail() + " aggiunto al team " + t.getNomeTeam());
                    System.out.println("Membri del team ora: " + t.getConcorrenti().size());
                    System.out.println("teamPerUtente contiene ora: " + teamPerUtente.size() + " mappature");
                    break;
                }
            }

            // Rimuovi tutti gli inviti per questo utente dal database
            teamDAO.rimuoviTuttiInvitiUtente(utente);

            return true;
        }
        return false;
    }

    /**
     * Rimuove un membro da un team.
     *
     * @param team il team da cui rimuovere il membro
     * @param utente l'utente da rimuovere
     * @return true se la rimozione è riuscita, false altrimenti
     */
    public boolean rimuoviMembroDalTeam(Team team, UtenteIscritto utente) {
        if (!team.getConcorrenti().contains(utente)) return false;

        boolean rimosso = teamDAO.rimuoviMembroDalTeam(team.getNomeTeam(), utente);
        if (rimosso) {
            team.getConcorrenti().remove(utente);
            teamPerUtente.remove(utente);
            return true;
        }
        return false;
    }

    /**
     * Elimina completamente un team dal sistema.
     *
     * @param team il team da eliminare
     * @return true se l'eliminazione è riuscita, false altrimenti
     */
    public boolean eliminaTeam(Team team) {
        boolean eliminato = teamDAO.delete(team);
        if (eliminato) {
            // Rimuovi dalle mappe locali
            for (UtenteIscritto membro : team.getConcorrenti()) {
                teamPerUtente.remove(membro);
            }
            listaTeam.remove(team);
            return true;
        }
        return false;
    }

    /**
     * Restituisce il documento associato a un team.
     *
     * @param team il team di cui recuperare il documento
     * @return il contenuto del documento, stringa vuota se non presente
     */
    public String getDocumentoPerTeam(Team team) {
        if (team == null) return "";
        // Carica dal database
        return documentoDAO.getDocumentoTeam(team);
    }

    /**
     * Salva un documento per un team specifico.
     *
     * @param team il team per cui salvare il documento
     * @param documento il contenuto del documento da salvare
     */
    public void salvaDocumentoPerTeam(Team team, String documento) {
        if (team == null) return;
        // Salva nel database
        documentoDAO.salvaDocumentoTeam(team, documento);
    }

    /**
     * Aggiorna le date dell'hackathon.
     *
     * @param inizioIscrizioni data di inizio iscrizioni
     * @param fineIscrizioni data di fine iscrizioni
     * @param dataInizioEvento data di inizio evento
     * @param dataFineEvento data di fine evento
     */
    public void aggiornaDateHackathon(Date inizioIscrizioni, Date fineIscrizioni, Date dataInizioEvento, Date dataFineEvento) {
        hackaton.setInizioIscrizioni(inizioIscrizioni);
        hackaton.setFineIscrizioni(fineIscrizioni);
        hackaton.setInizio(dataInizioEvento);
        hackaton.setFine(dataFineEvento);
        hackatonDAO.update(hackaton);
    }

    /**
     * Imposta il numero massimo di iscritti all'hackathon.
     *
     * @param maxIscritti il numero massimo di iscritti (deve essere > 0)
     */
    public void setNumeroMaxIscritti(int maxIscritti) {
        if (maxIscritti > 0) {
            hackaton.setNumeroMaxIscritti(maxIscritti);
            hackatonDAO.update(hackaton);
        }
    }

    /**
     * Imposta il numero massimo di componenti per team.
     *
     * @param maxComponenti il numero massimo di componenti (deve essere > 0)
     */
    public void setComponentMaxTeam(int maxComponenti) {
        if (maxComponenti > 0) {
            hackaton.setComponentMaxTeam(maxComponenti);
            hackatonDAO.update(hackaton);
        }
    }

    /**
     * Assegna un voto a un team da parte di un giudice.
     *
     * @param team il team da valutare
     * @param giudice il giudice che assegna il voto
     * @param voto il voto da assegnare (da 1 a 10)
     * @return true se il voto è stato assegnato con successo, false altrimenti
     */
    public boolean assegnaVoto(Team team, UtenteIscritto giudice, int voto) {
        return assegnaVoto(team, giudice, voto, null);
    }

    /**
     * Assegna un voto e un commento a un team da parte di un giudice.
     *
     * @param team il team da valutare
     * @param giudice il giudice che assegna il voto
     * @param voto il voto da assegnare (da 1 a 10)
     * @param commento il commento opzionale
     * @return true se il voto è stato assegnato con successo, false altrimenti
     */
    public boolean assegnaVoto(Team team, UtenteIscritto giudice, int voto, String commento) {
        if (!"giudice".equals(giudice.getRuolo())) return false;
        if (voto < 1 || voto > 10) return false;

        // Verifica se esiste già un voto
        if (votoDAO.esisteVoto(team, giudice)) {
            // Aggiorna il voto esistente
            return votoDAO.aggiornaVoto(team, giudice, voto, commento);
        } else {
            // Salva un nuovo voto
            return votoDAO.salvaVoto(team, giudice, voto, commento);
        }
    }

    /**
     * Restituisce il voto assegnato da un giudice a un team.
     *
     * @param team il team di cui recuperare il voto
     * @param giudice il giudice che ha assegnato il voto
     * @return il voto assegnato, null se non presente
     */
    public Integer getVotoAssegnato(Team team, UtenteIscritto giudice) {
        return votoDAO.getVoto(team, giudice);
    }

    /**
     * Restituisce il commento assegnato da un giudice a un team.
     *
     * @param team il team di cui recuperare il commento
     * @param giudice il giudice che ha assegnato il commento
     * @return il commento assegnato, null se non presente
     */
    public String getCommentoAssegnato(Team team, UtenteIscritto giudice) {
        return votoDAO.getCommento(team, giudice);
    }

    /**
     * Calcola la media dei voti ricevuti da un team.
     *
     * @param team il team di cui calcolare la media
     * @return la media dei voti del team
     */
    public double getMediaVoti(Team team) {
        return votoDAO.getMediaVotiTeam(team);
    }

    /**
     * Restituisce la classifica dei team basata sulla media dei voti.
     *
     * @return una mappa con i team e le rispettive medie
     */
    public Map<Team, Double> getClassificaMedia() {
        Map<Team, Double> media = new HashMap<>();
        for (Team t : listaTeam) {
            media.put(t, votoDAO.getMediaVotiTeam(t));
        }
        return media;
    }

    /**
     * Metodo di debug per visualizzare informazioni sui team caricati.
     * Stampa sulla console dettagli sui team, membri e mappature.
     */
    public void debugTeamInfo() {
        System.out.println("=== DEBUG TEAM INFO ===");
        System.out.println("Numero di team caricati: " + listaTeam.size());

        for (Team team : listaTeam) {
            System.out.println("Team: " + team.getNomeTeam());
            System.out.println("  Membri: " + team.getConcorrenti().size());
            for (UtenteIscritto membro : team.getConcorrenti()) {
                System.out.println("    - " + membro.getMail());
            }
        }

        System.out.println("Mappa teamPerUtente:");
        for (Map.Entry<UtenteIscritto, Team> entry : teamPerUtente.entrySet()) {
            System.out.println("  " + entry.getKey().getMail() + " -> " + entry.getValue().getNomeTeam());
        }
        System.out.println("=== FINE DEBUG ===");
    }
}