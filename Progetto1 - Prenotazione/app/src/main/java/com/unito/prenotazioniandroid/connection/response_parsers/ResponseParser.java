package com.unito.prenotazioniandroid.connection.response_parsers;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.unito.prenotazioniandroid.Constants;
import com.unito.prenotazioniandroid.connection.ServerQuerier;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class ResponseParser {
    private boolean success;
    private String htmlErrorMsg;
    private Object result;

    public ResponseParser(String s, Class c) {
        scanResponse(s, c);
    }

    public ResponseParser(ServerQuerier sq, Class c) {
        this(getSqResponse(sq), c);
    }

    private static String getSqResponse(ServerQuerier sq) {
        String s = null;
        try {
            s = sq.get(Constants.SERVER_TIMEOOUT_SECONDS, TimeUnit.SECONDS);
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
        return s;
    }

    private void scanResponse(String s, Class c) {
        Gson gson = new Gson();
        Log.d("debug", "Server responded: " + s);
        JsonObject jsonObject = gson.fromJson(s, JsonObject.class);

        if (jsonObject.has("success")) {
            JsonElement success = jsonObject.get("success");
            this.success = success.getAsBoolean();
            String msgError = null;
            if (this.success && jsonObject.has("data")) {
                parseResult(jsonObject, c);
            } else if (jsonObject.has("info")) {
                JsonElement info = jsonObject.get("info");
                msgError =/*"Errore dal server " +*/ gson.fromJson(info, String.class);
                ;
            } else {
                msgError = "Errore nel formato della risposta Json";
            }
            if (msgError != null) {
                htmlErrorMsg = msgError;
                Log.d("debug", msgError);
            }
        }
    }

    public abstract void parseResult(JsonObject jsonObject, Class c);

    public boolean hasSucceded() {
        return success;
    }

    public String getHtmlErrorMsg() {
        return htmlErrorMsg;
    }

    public Object getResult() {
        return result;
    }

    protected void setResult(Object r) {
        this.result = r;
    }
}
