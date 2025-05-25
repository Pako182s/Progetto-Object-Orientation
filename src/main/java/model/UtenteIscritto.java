package model;

public class UtenteIscritto {
    protected String nome;
    protected String password;
    protected String mail;


    public UtenteIscritto(String n, String p, String m) {
        nome = n;
        password = p;
        mail = m;
    }

    public UtenteIscritto() {
    }

    public String getNome() {
        return nome;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    private String ruolo = "utente";

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

}
