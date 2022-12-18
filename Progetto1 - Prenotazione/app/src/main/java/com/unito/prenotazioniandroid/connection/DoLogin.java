package com.unito.prenotazioniandroid.connection;

import android.content.Context;
import android.util.Log;

import com.unito.prenotazioniandroid.connection.response_parsers.HtmlMessageParser;
import com.unito.prenotazioniandroid.connection.response_parsers.ResponseParser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DoLogin extends ServerQuerier {
    private String username;
    private String password;

    public DoLogin(Context context, String username, String password) {
        super(context);
        this.username = username;
        this.password = password;
    }

    @Override
    protected String makeQuery() throws UnsupportedEncodingException {
        String charset = getCharset();
        String param1 = "Auth";
        String param2 = this.username;
        String param3 = this.password;
        String query = String.format("action=%s&utente=%s&password=%s",
                URLEncoder.encode(param1, charset),
                URLEncoder.encode(param2, charset),
                URLEncoder.encode(param3, charset));
        return query;
    }

    @Override
    protected void onPostExecute(String result) {
        ResponseParser rp = new HtmlMessageParser(result, null);
        String msg;
        if (rp.hasSucceded()) {
            msg = "Login riuscito!";
        } else {
            msg = "Login fallito!";
        }
        Log.d("debug", msg);
    }
}
