package com.unito.prenotazioniandroid.connection;

import android.content.Context;
import android.util.Log;

import com.unito.prenotazioniandroid.connection.response_parsers.HtmlMessageParser;
import com.unito.prenotazioniandroid.connection.response_parsers.ResponseParser;
import com.unito.prenotazioniandroid.data.LoginRepository;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DoLogout extends ServerQuerier {

    public DoLogout(Context context) {
        super(context);
    }

    @Override
    protected String makeQuery() throws UnsupportedEncodingException {
        String charset = getCharset();
        String param1 = "Logout";
        String query = String.format("action=%s",
                URLEncoder.encode(param1, charset));
        return query;
    }

    @Override
    protected void onPostExecute(String result) {
        ResponseParser rp = new HtmlMessageParser(result, null);
        String msg;
        if(rp.hasSucceded()){
            //LoginRepository.getInstance(null).logout();
            msg = "Logout riuscito!";
            //TODO set logout action
        } else {
            msg = "Logout fallito!";
        }
        Log.d("debug", msg);
    }
}
