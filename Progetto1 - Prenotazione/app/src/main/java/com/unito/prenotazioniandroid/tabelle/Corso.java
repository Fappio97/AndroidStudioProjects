package com.unito.prenotazioniandroid.tabelle;

public class Corso extends Tabella {
    private String titolo;

    public Corso(String titolo) {
        this.titolo = titolo;
    }

    public String getTitolo() {
        return titolo;
    }

    @Override
    public String toString() {
        return titolo;
    }

    public String[] getParametersName() {
        final String[] s = {"Titolo"};
        return s;
    }

    @Override
    public String[] getKeysName() {
        final String[] keys = {"Titolo"};
        return keys;
    }

    @Override
    public String[] getUpdatableKeys() {
        return this.getParametersName();
    }

    @Override
    public String[] getOrderingKeys() {
        final String[] keys = {"Titolo"};
        return keys;
    }

}
