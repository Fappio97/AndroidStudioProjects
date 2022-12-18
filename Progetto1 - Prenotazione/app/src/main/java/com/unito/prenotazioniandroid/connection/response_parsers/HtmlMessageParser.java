package com.unito.prenotazioniandroid.connection.response_parsers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.unito.prenotazioniandroid.connection.ServerQuerier;

public class HtmlMessageParser extends ResponseParser {
    static private final Class EXPECTED_TYPE = String.class; //TODO should be html text class

    public HtmlMessageParser(String s, Class c) {
        super(s, EXPECTED_TYPE);
    }

    public HtmlMessageParser(ServerQuerier sq, Class c) {
        super(sq, c);
    }

    @Override
    public void parseResult(JsonObject jsonObject, Class c) {
        Gson gson = new Gson();
        JsonElement data = jsonObject.get("data");
        Object msg = gson.fromJson(data, EXPECTED_TYPE);
        setResult(msg);
    }
}
