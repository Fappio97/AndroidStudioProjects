package com.unito.prenotazioniandroid.connection.response_parsers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.unito.prenotazioniandroid.tabelle.Tabella;

import java.util.ArrayList;
import java.util.List;

public class ResponseTabellaListParser extends ResponseParser {
    private Integer maxPage;

    public ResponseTabellaListParser(String s, Class<? extends Tabella> c) {
        super(s, c);
    }

    @Override
    public void parseResult(JsonObject jsonObject, Class c) {
        List<Tabella> tabellaList = new ArrayList<>();
        Gson gson = new Gson();
        JsonArray data = jsonObject.getAsJsonArray("data");
        for (JsonElement je : data)
            tabellaList.add((Tabella) gson.fromJson(je, c));
        setResult(tabellaList);

        if (jsonObject.has("maxPage")) {
            JsonElement maxPage = jsonObject.get("maxPage");
            Integer i = gson.fromJson(maxPage, Integer.class);
            setMaxPage(i);
        }
    }

    public Integer getMaxPage() {
        return maxPage;
    }

    private void setMaxPage(Integer mp) {
        this.maxPage = mp;
    }
}
