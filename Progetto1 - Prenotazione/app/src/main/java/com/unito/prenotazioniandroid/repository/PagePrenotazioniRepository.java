package com.unito.prenotazioniandroid.repository;

import android.content.Context;

import java.util.concurrent.atomic.AtomicInteger;

public class PagePrenotazioniRepository {
    private static PagePrenotazioniRepository instance;
    private AtomicInteger page;

    private PagePrenotazioniRepository(Context context) {
        page = new AtomicInteger(0);
    }

    public static PagePrenotazioniRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PagePrenotazioniRepository(context);
        }
        return instance;
    }

    public AtomicInteger getPagePrenotazioni() {
        return page;
    }
}
