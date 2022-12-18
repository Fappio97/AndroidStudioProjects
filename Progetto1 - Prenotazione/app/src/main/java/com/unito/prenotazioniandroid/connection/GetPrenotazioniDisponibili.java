package com.unito.prenotazioniandroid.connection;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unito.prenotazioniandroid.connection.response_parsers.ResponseParser;
import com.unito.prenotazioniandroid.connection.response_parsers.ResponsePrenotazioniDisponibiliParser;
import com.unito.prenotazioniandroid.tabelle.FormSelection;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GetPrenotazioniDisponibili extends ServerQuerier {
    private FormSelection formSelection;

    public GetPrenotazioniDisponibili(Context context, FormSelection formSelection) {
        super(context);
        if (formSelection == null)
            formSelection = new FormSelection();
        this.formSelection = formSelection;
    }

    @Override
    protected String makeQuery() throws UnsupportedEncodingException {
        String charset = getCharset();
        String param1 = "PrenotazioniDisponibili";
        Gson gson = new GsonBuilder().serializeNulls().create();
        String f_s = gson.toJson(formSelection);
        String query = String.format("action=%s&f_s=%s",
                URLEncoder.encode(param1, charset),
                URLEncoder.encode(f_s, charset));
        return query;
    }

    @Override
    protected void onPostExecute(String result) {
        ResponseParser rp = new ResponsePrenotazioniDisponibiliParser(result, FormSelection.class);
        if (rp.hasSucceded()) {
            Object rpResult = rp.getResult();
            Log.d("debug", "Original response : " + result);
            if (rpResult != null)
                Log.d("debug", "Parsed response " + this.getClass().getSimpleName() + " of type " +
                        rpResult.getClass().getSimpleName() + ": " + new GsonBuilder().serializeNulls().create().toJson(rpResult));
        }
    }
}
