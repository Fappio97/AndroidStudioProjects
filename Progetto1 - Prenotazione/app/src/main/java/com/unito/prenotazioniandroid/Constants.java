package com.unito.prenotazioniandroid;

import com.unito.prenotazioniandroid.repository.storge.model.Libera;
import com.unito.prenotazioniandroid.repository.storge.model.Prenotazione;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Constants {
    // network
    public static final String PRENOTAZIONI_ARRAY_DATA_TAG = "data";//"results"
    public static final Type PRENOTAZIONE_ARRAY_LIST_CLASS_TYPE = (new ArrayList<Prenotazione>()).getClass();
    public static final Type LIBERE_ARRAY_LIST_CLASS_TYPE = (new ArrayList<Libera>()).getClass();
    public static final String POPULAR_PRENOTAZIONI_BASE_URL = "http://10.0.2.2:8080/";//Controller?action=PersonalBookings //"https://api.themoviedb.org/3/movie/popular/";
    public static final long SERVER_TIMEOOUT_SECONDS = 2;
    private static final String IMAGE_URL_PREFIX = "https://image.tmdb.org/t/p/";
    public static final String SMALL_IMAGE_URL_PREFIX = IMAGE_URL_PREFIX + "w300";
    public static final String BIG_IMAGE_URL_PREFIX = IMAGE_URL_PREFIX + "w500";
    public static final String ACTION_KEY_REQUEST_PARAM = "action";
    public static final String LANGUAGE_REQUEST_PARAM = "language";
    public static final String FORM_SELECTION_PARAM = "f_s";
    public static final String PAGE_REQUEST_PARAM = "page";
    public static final String ACTION_PRENOTAZIONI_KEY = "PersonalBookings";
    public static final String ACTION_LIBERE_KEY = "Prenotazioni";
    public static final String LANGUAGE = "en";
    public static final int LOADING_PAGE_SIZE = 10;//20

    // DB
    public static final String DATA_BASE_NAME = "Prenotazioni.db";
    public static final int NUMBERS_OF_THREADS = 3;
}
