package com.unito.prenotazioniandroid.repository.network.api;

//import com.unito.prenotazioniandroid.repository.storge.model.Prenotazione;

import com.unito.prenotazioniandroid.repository.storge.model.Prenotazione;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.unito.prenotazioniandroid.Constants.ACTION_KEY_REQUEST_PARAM;
import static com.unito.prenotazioniandroid.Constants.PAGE_REQUEST_PARAM;

public interface PrenotazioniAPIInterface {

    @GET("/Ripetizioni/Controller")
//"."
    Call<ArrayList<Prenotazione>> getPrenotazioni(@Query(ACTION_KEY_REQUEST_PARAM) String actionKey,
                                                  @Query(PAGE_REQUEST_PARAM) int page);
}