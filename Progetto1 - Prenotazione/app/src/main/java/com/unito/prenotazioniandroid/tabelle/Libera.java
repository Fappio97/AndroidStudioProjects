package com.unito.prenotazioniandroid.tabelle;


public class Libera extends Tabella {
    private int id;
    private String nome;
    private String cognome;
    private String titolo;
    private String ora;
    private String giorno;

    public Libera(int id, String nome, String cognome, String titolo, String ora, String giorno) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.titolo = titolo;
        this.ora = ora;
        this.giorno = giorno;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getOra() {
        return ora;
    }

    public String getGiorno() {
        return giorno;
    }

    @Override
    public String[] getParametersName() {
        String[] s = {"ID", "Nome", "Cognome", "Titolo", "Ora", "Giorno"};
        return s;
    }

    @Override
    public String[] getKeysName() {
        final String[] keys = {"Id", "Ora", "Giorno"};
        return keys;
    }

    @Override
    public String[] getUpdatableKeys() {
        return null;
    }

    @Override
    public String[] getOrderingKeys() {
        final String[] keys = {"Titolo", "Giorno", "Ora"};
        return keys;
    }
}
