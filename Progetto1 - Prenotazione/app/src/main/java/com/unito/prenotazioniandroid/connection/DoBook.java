package com.unito.prenotazioniandroid.connection;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.unito.prenotazioniandroid.connection.response_parsers.HtmlMessageParser;
import com.unito.prenotazioniandroid.connection.response_parsers.ResponseParser;
import com.unito.prenotazioniandroid.repository.storge.model.Libera;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

//import com.unito.prenotazioniandroid.tabelle.Libera;

public class DoBook extends ServerQuerier {
    Libera libera;

    public DoBook(Context c, Libera p) {
        super(c);
        libera = p;
    }

    @Override
    protected String makeQuery() throws UnsupportedEncodingException {
        String charset = getCharset();
        Gson gson = new Gson();
        String param1 = "Book";
        int id = libera.getId();
        String nome = libera.getNome();
        String cognome = libera.getCognome();
        String corso = libera.getTitolo();
        String ora = libera.getOra();
        String giorno = libera.getGiorno();
        String query = String.format("action=%s&id=%s&nome=%s&cognome=%s&corso=%s&ora=%s&giorno=%s",
                URLEncoder.encode(param1, charset),
                URLEncoder.encode(Integer.valueOf(id).toString(), charset),
                URLEncoder.encode(nome, charset),
                URLEncoder.encode(cognome, charset),
                URLEncoder.encode(corso, charset),
                URLEncoder.encode(ora, charset),
                URLEncoder.encode(giorno, charset));
        return query;
    }

    @Override
    protected void onPostExecute(String result) {
        ResponseParser rp = new HtmlMessageParser(result, null);
        String msg;
        Boolean hasSucceded = rp.hasSucceded();
        if (hasSucceded) {
            //TODO remove libera from repository
            msg = (String) rp.getResult();
        } else {
            msg = (String) rp.getHtmlErrorMsg();
        }
        makeToast(msg);
        Log.d("debug", msg);
    }
}
