package com.unito.prenotazioniandroid.repository.network.api;

import android.util.Log;

import androidx.paging.PagedList;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.unito.prenotazioniandroid.Constants;
import com.unito.prenotazioniandroid.repository.PagePrenotazioniRepository;
import com.unito.prenotazioniandroid.repository.PrenotazioniRepository;
import com.unito.prenotazioniandroid.repository.storge.model.Prenotazione;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

//import com.elad.themovie.service.repository.storge.model.Prenotazione;

/**
 * Created by Elad on 6/25/2018.
 */

class PrenotazioniJsonDeserializer implements JsonDeserializer {
    private static String TAG = PrenotazioniJsonDeserializer.class.getSimpleName();
    private final AtomicInteger pagePrenotazioni = PagePrenotazioniRepository.getInstance(null).getPagePrenotazioni();

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<Prenotazione> prenotazioni = new ArrayList<>();
        try {
            JsonObject jsonObject = json.getAsJsonObject();
            Integer lastMax = jsonObject.get("maxPage").getAsInt();
            if (pagePrenotazioni.get() <= lastMax) {//TODO does not cover some cases
                JsonArray prenotazioniJsonArray = jsonObject.getAsJsonArray(Constants.PRENOTAZIONI_ARRAY_DATA_TAG);
                prenotazioni = new ArrayList<>(prenotazioniJsonArray.size());
                for (int i = 0; i < prenotazioniJsonArray.size(); i++) {
                    // adding the converted wrapper to our container
                    Prenotazione dematerialized = context.deserialize(prenotazioniJsonArray.get(i), Prenotazione.class);
                    prenotazioni.add(dematerialized);
                    /*PagedList<Prenotazione> pagedList = PrenotazioniRepository.getInstance(null).getPrenotazioni().getValue(); //TODO tentativo aggiornamento 1
                    if(!pagedList.contains(dematerialized))
                        prenotazioni.add(dematerialized);
                    else {
                        int index = pagedList.indexOf(dematerialized);
                        Prenotazione demOld = pagedList.get(index);
                        if(!dematerialized.equalsWithStatus(demOld)) {
                            //pagedList.remove(index);
                            //pagedList.add(0, dematerialized);
                            demOld.setStato(dematerialized.getStato());
                        }
                    }*/
                }
            }
        } catch (JsonParseException e) {
            Log.e(TAG, String.format("Could not deserialize Prenotazione element: %s", json.toString()));
        }
        return prenotazioni;
    }
}
