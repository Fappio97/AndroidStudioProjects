package com.unito.prenotazioniandroid.tabelle;

public class Docente extends Tabella {
    private String nome;
    private String cognome;
    private int id;


    public Docente(String nome, String cognome, int id) {
        this.nome = nome;
        this.cognome = cognome;
        this.id = id;
    }


    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return nome + " " + cognome + " " + id;
    }

    public String[] getParametersName() {
        final String[] s = {"Nome", "Cognome", "Id"};
        return s;
    }

    @Override
    public String[] getKeysName() {
        final String[] keys = {"Id"};
        return keys;
    }

    @Override
    public String[] getUpdatableKeys() {
        return this.getParametersName();
    }

    @Override
    public String[] getOrderingKeys() {
        final String[] s = {"Cognome", "Nome"};
        return s;
    }

}
