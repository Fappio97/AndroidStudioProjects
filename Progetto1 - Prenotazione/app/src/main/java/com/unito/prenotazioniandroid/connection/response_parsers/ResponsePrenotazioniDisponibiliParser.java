package com.unito.prenotazioniandroid.connection.response_parsers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.unito.prenotazioniandroid.tabelle.ServerFormSelections;

public class ResponsePrenotazioniDisponibiliParser extends ResponseParser {

    public ResponsePrenotazioniDisponibiliParser(String s, Class c) {
        super(s, c);
    }

    @Override
    public void parseResult(JsonObject jsonObject, Class c) {
        jsonObject = jsonObject.getAsJsonObject("data");
        if (jsonObject.has("hours") && jsonObject.has("professors") &&
                jsonObject.has("courses") && jsonObject.has("days")) {
            Gson gson = new Gson();
            ServerFormSelections sfs = gson.fromJson(jsonObject, ServerFormSelections.class);
            setResult(sfs);
        }
    }
}
