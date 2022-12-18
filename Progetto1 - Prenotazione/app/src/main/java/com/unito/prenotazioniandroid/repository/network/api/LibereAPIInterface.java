package com.unito.prenotazioniandroid.repository.network.api;

//import com.unito.prenotazioniandroid.repository.storge.model.Libera;

import com.unito.prenotazioniandroid.repository.storge.model.Libera;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.unito.prenotazioniandroid.Constants.ACTION_KEY_REQUEST_PARAM;
import static com.unito.prenotazioniandroid.Constants.FORM_SELECTION_PARAM;
import static com.unito.prenotazioniandroid.Constants.PAGE_REQUEST_PARAM;

public interface LibereAPIInterface {

    @GET("/Ripetizioni/Controller")
//"."
    Call<ArrayList<Libera>> getLibere(@Query(ACTION_KEY_REQUEST_PARAM) String actionKey,
                                      @Query(FORM_SELECTION_PARAM) String f_s_json,
                                      @Query(PAGE_REQUEST_PARAM) int page);
}