package com.unito.prenotazioniandroid.tabelle;

public abstract class Tabella {
    public abstract String[] getParametersName();//{return null;};

    public abstract String[] getKeysName();

    public abstract String[] getUpdatableKeys();

    public abstract String[] getOrderingKeys();

    boolean implementsTabella() {
        boolean isCorrect = true;
        for (String s : getParametersName()) {
            try {
                this.getClass().getMethod("get" + s);
            } catch (NoSuchMethodException e) {
                isCorrect = false;
            }
        }
        return isCorrect;
    }

    //public Method getDeclaredMethod(String s){return null;};
}
