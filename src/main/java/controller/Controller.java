package Controller;

import model.UtenteIscritto;
import model.Hackaton;
import model.Team;

import java.util.*;

public class Controller {
    private ArrayList<UtenteIscritto> utentiIscritti = new ArrayList<>();
    private UtenteIscritto utenteAutenticato;
    private Hackaton hackaton;
    private String problemaGiudice = "";
    private Map<UtenteIscritto, Team> teamPerUtente = new HashMap<>();
    private Map<UtenteIscritto, List<Team>> invitiPerUtente = new HashMap<>();
    private List<Team> listaTeam = new ArrayList<>();
    private Map<Team, String> documentiPerTeam = new HashMap<>();

    //Informazioni dell'hackaton di base, modificabili dall'organizzatore una volta avviato l'evento.
    public Controller() {
        hackaton = new Hackaton(
                "Hackaton1",
                "Napoli",
                null,
                null,
                20,
                3,
                null,
                null
        );

        // Utenti già registrati, utilizzare mail e password per accedere come utente o giudice.
        UtenteIscritto luca = new UtenteIscritto("Luca", "1234", "luca@mail.com");
        UtenteIscritto ilaria = new UtenteIscritto("Ilaria", "1234", "ilaria@mail.com");
        UtenteIscritto fernando = new UtenteIscritto("Fernando", "1234", "fernando@mail.com");
        UtenteIscritto giulia = new UtenteIscritto("Giulia", "1234", "giulia@mail.com");
        UtenteIscritto giudice = new UtenteIscritto("Giudice", "1234", "g@mail.com");
        giudice.setRuolo("giudice");

        utentiIscritti.add(luca);
        utentiIscritti.add(ilaria);
        utentiIscritti.add(fernando);
        utentiIscritti.add(giulia);
        utentiIscritti.add(giudice);
    }

    //Restituisce l'utente registrato alla piattaforma
    public UtenteIscritto getUtenteAutenticato() {
        return utenteAutenticato;
    }

    //confronta mail e password e autentica o meno l'utente all'interno della piattaforma
    public boolean autenticaUtente(String mail, String password) {
        for (UtenteIscritto utente : utentiIscritti) {
            if (utente.getMail().equals(mail) && utente.getPassword().equals(password)) {
                utenteAutenticato = utente;
                return true;
            }
        }
        return false;
    }

    // Registrazione nuovo utente nella piattaforma
    public void setUtenteIscritto(String nome, String password, String mail) {
        UtenteIscritto nuovoUtente = new UtenteIscritto(nome, password, mail);
        utentiIscritti.add(nuovoUtente);
    }

    //Restituisce le informazioni dell'hackaton
    public Hackaton getHackaton() {
        return hackaton;
    }

    //ORGANIZZATORE DELLA PIATTAFORMA. MAIL E PASSWORD PER ACCEDERE
    public boolean isOrganizzatore(String email, String password) {
        return email.equals("admin@hack.it") && password.equals("admin");
    }

    //Lista utenti registrati
    public List<UtenteIscritto> getUtentiRegistrati() {
        return utentiIscritti;
    }

    //setta il ruolo del giudice quando l'organizzatore promuove un utente a giudice
    public void promuoviAGiudice(UtenteIscritto utente) {
        utente.setRuolo("giudice");
    }

    //avvia le iscrizioni dell'evento impostando le varie date
    public void avviaIscrizioni() {
        Calendar cal = Calendar.getInstance();
        Date apertura = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, 2);
        Date chiusura = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, 2);
        Date inizioEvento = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, 2);
        Date fineEvento = cal.getTime();

        hackaton = new Hackaton(
                hackaton.getTitolo(),
                hackaton.getSede(),
                inizioEvento,
                fineEvento,
                hackaton.getNumeroMaxIscritti(),
                hackaton.getComponentMaxTeam(),
                apertura,
                chiusura
        );
    }

    //Restituisce il problema scritto dai giudici
    public String getProblemaGiudice() {
        return problemaGiudice;
    }

    public void setProblemaGiudice(String problema) {
        this.problemaGiudice = problema;
    }


    //verifica se nella map l'utente ha gia un team
    public boolean utenteHaTeam(UtenteIscritto utente) {
        return teamPerUtente.containsKey(utente);
    }

    public Team getTeamDiUtente(UtenteIscritto utente) {
        return teamPerUtente.get(utente);
    }

    //Lista dei team
    public List<Team> getListaTeam() {
        return listaTeam;
    }

    //Lista utenti
    public List<UtenteIscritto> getUtentiIscritti() {
        return utentiIscritti;
    }

    //Creazione del team
    public boolean creaTeam(String nomeTeam, UtenteIscritto creatore) {
        if (utenteHaTeam(creatore)) return false;

        Team nuovoTeam = new Team(nomeTeam);
        nuovoTeam.aggiungiConcorrente(creatore);
        listaTeam.add(nuovoTeam);
        teamPerUtente.put(creatore, nuovoTeam);
        return true;
    }

    //Permette di invitare nuovi utenti nel team, solo se non fanno già parte di un altro team
    public boolean invitaNelTeam(UtenteIscritto mittente, UtenteIscritto invitato) {
        if (!utenteHaTeam(mittente)) return false;
        if (utenteHaTeam(invitato)) return false;

        Team teamMittente = teamPerUtente.get(mittente);

        invitiPerUtente.putIfAbsent(invitato, new ArrayList<>());
        List<Team> inviti = invitiPerUtente.get(invitato);

        if (inviti.contains(teamMittente)) return false;

        inviti.add(teamMittente);
        return true;
    }

    //Restituisce la lista degli inviti arrivvati all'utente
    public List<Team> getInvitiPerUtente(UtenteIscritto utente) {
        return invitiPerUtente.getOrDefault(utente, new ArrayList<>());
    }


    //Il comando accetta invito, se l'utente già ha un team restituisce false, altrimenti fa entrare l'utente nel team
    public boolean accettaInvito(UtenteIscritto utente, Team team) {
        if (utenteHaTeam(utente)) return false;

        List<Team> inviti = invitiPerUtente.get(utente);
        if (inviti == null || !inviti.contains(team)) return false;

        team.aggiungiConcorrente(utente);
        teamPerUtente.put(utente, team);
        invitiPerUtente.remove(utente);
        return true;
    }


    //Restituisce i documenti dei vari team
    public String getDocumentoPerTeam(Team team) {
        return documentiPerTeam.getOrDefault(team, "");
    }


    public void salvaDocumentoPerTeam(Team team, String documento) {
        documentiPerTeam.put(team, documento);
    }


    //Permette di modifcare le date dell'hackaton da parte dell'organizzatore
    public void aggiornaDateHackathon(Date inizioIscrizioni, Date fineIscrizioni, Date dataInizioEvento, Date dataFineEvento) {
        hackaton.setInizioIscrizioni(inizioIscrizioni);
        hackaton.setFineIscrizioni(fineIscrizioni);
        hackaton.setInizio(dataInizioEvento);
        hackaton.setFine(dataFineEvento);
    }

    //Permette di impostare il numero massimo di iscritti, che di base è impostato a 20
    public void setNumeroMaxIscritti(int maxIscritti) {
        if (maxIscritti > 0) {
            hackaton.setNumeroMaxIscritti(maxIscritti);
        }
    }

    //Permette di impostare il numero massimo di compenenti per ogni team, che di base è impostato a 3
    public void setComponentMaxTeam(int maxComponenti) {
        if (maxComponenti > 0) {
            hackaton.setComponentMaxTeam(maxComponenti);
        }
    }

    //Voti assegnati dai giudici
    private Map<Team, Map<UtenteIscritto, Integer>> votiGiudici = new HashMap<>();

    //Permette di assegnare un voto ai team da parte dei giudici
    public boolean assegnaVoto(Team team, UtenteIscritto giudice, int voto) {
        if (!"giudice".equals(giudice.getRuolo())) return false;
        votiGiudici.putIfAbsent(team, new HashMap<>()); //se non è stato già assegnato il voto
        Map<UtenteIscritto, Integer> votiPerTeam = votiGiudici.get(team);

        if (votiPerTeam.containsKey(giudice)) return false; // voto già assegnato
        votiPerTeam.put(giudice, voto);
        return true;
    }

    //Effettua la media dei voti da implementare poi nella classifica
    public double getMediaVoti(Team team) {
        Map<UtenteIscritto, Integer> voti = votiGiudici.getOrDefault(team, new HashMap<>());
        if (voti.isEmpty()) return 0.0;
        return voti.values().stream().mapToInt(i -> i).average().orElse(0);
    }

    //Classifica
    public Map<Team, Double> getClassificaMedia() {
        Map<Team, Double> media = new HashMap<>();
        for (Team t : listaTeam) {
            media.put(t, getMediaVoti(t));
        }
        return media;
    }


}
