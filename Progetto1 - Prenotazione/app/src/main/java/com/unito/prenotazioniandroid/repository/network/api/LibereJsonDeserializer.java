package com.unito.prenotazioniandroid.repository.network.api;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.unito.prenotazioniandroid.Constants;
import com.unito.prenotazioniandroid.repository.PageLibereRepository;
import com.unito.prenotazioniandroid.repository.storge.model.Libera;

import java.lang.reflect.Type;
import java.util.ArrayList;

//import com.elad.themovie.service.repository.storge.model.Libera;

/**
 * Created by Elad on 6/25/2018.
 */

class LibereJsonDeserializer implements JsonDeserializer {
    private static String TAG = LibereJsonDeserializer.class.getSimpleName();

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<Libera> libere = new ArrayList<>();
        try {
            JsonObject jsonObject = json.getAsJsonObject();
            Integer lastMax = jsonObject.get("maxPage").getAsInt();
            if (PageLibereRepository.getInstance(null).getPageLibere().get() <= lastMax) {//TODO does not cover some cases
                JsonArray libereJsonArray = jsonObject.getAsJsonArray(Constants.PRENOTAZIONI_ARRAY_DATA_TAG);
                libere = new ArrayList<>(libereJsonArray.size());
                for (int i = 0; i < libereJsonArray.size(); i++) {
                    // adding the converted wrapper to our container
                    Libera dematerialized = context.deserialize(libereJsonArray.get(i), Libera.class);
                    //if(!LibereRepository.getInstance(null).getLibere().getValue().contains(dematerialized))
                    libere.add(dematerialized);
                }
            }
            assert libere != null;
        } catch (JsonParseException e) {
            Log.e(TAG, String.format("Could not deserialize Libera element: %s", json.toString()));
        }
        return libere;
    }
}
