package com.unito.prenotazioniandroid.connection;

import android.content.Context;
import android.util.Log;

import com.unito.prenotazioniandroid.connection.response_parsers.HtmlMessageParser;
import com.unito.prenotazioniandroid.connection.response_parsers.ResponseParser;
import com.unito.prenotazioniandroid.repository.storge.model.Prenotazione;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DoConfirm extends ServerQuerier {
    Prenotazione prenotazione;

    public DoConfirm(Context c, Prenotazione p) {
        super(c);
        prenotazione = p;
    }

    @Override
    protected String makeQuery() throws UnsupportedEncodingException {
        String charset = getCharset();
//        Gson gson = new Gson();
        String param1 = "ChangeBookStatus";
        String op = "Confirm";
        Prenotazione attiva = this.prenotazione;
        int id = attiva.getId();
        String username = attiva.getUsername();
        String corso = attiva.getTitolo();
        String ora = attiva.getOra();
        String giorno = attiva.getGiorno();
        String stato = attiva.getStato();
        String query = String.format("action=%s&op=%s&id=%s&username=%s&corso=%s&ora=%s&giorno=%s&stato=%s",
                URLEncoder.encode(param1, charset),
                URLEncoder.encode(op, charset),
                URLEncoder.encode(Integer.valueOf(id).toString(), charset),
                URLEncoder.encode(username, charset),
                URLEncoder.encode(corso, charset),
                URLEncoder.encode(ora, charset),
                URLEncoder.encode(giorno, charset),
                URLEncoder.encode(stato, charset));

        return query;
    }

    @Override
    protected void onPostExecute(String result) {
        ResponseParser rp = new HtmlMessageParser(result, null);
        String msg;
        Boolean hasSucceded = rp.hasSucceded();
        if (hasSucceded) {
//            PrenotazioniRepository instance = PrenotazioniRepository.getInstance(null);
//            PagedList<Prenotazione> prenotazioni = instance.getPrenotazioni().getValue();
//            int i = prenotazioni.indexOf(prenotazione);
            prenotazione.setStato("effettuata");
//            prenotazioni.set(i, prenotazione);
            msg = (String) rp.getResult();
        } else {
            msg = (String) rp.getHtmlErrorMsg();
        }
        makeToast(msg);
        Log.d("debug", msg);
    }
}
