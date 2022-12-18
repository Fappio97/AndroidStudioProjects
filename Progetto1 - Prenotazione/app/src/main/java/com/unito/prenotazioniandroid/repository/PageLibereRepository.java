package com.unito.prenotazioniandroid.repository;

import android.content.Context;

import java.util.concurrent.atomic.AtomicInteger;

public class PageLibereRepository {
    private static PageLibereRepository instance;
    private AtomicInteger page;

    private PageLibereRepository(Context context) {
        page = new AtomicInteger(0);
    }

    public static PageLibereRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PageLibereRepository(context);
        }
        return instance;
    }

    public AtomicInteger getPageLibere() {
        return page;
    }
}
