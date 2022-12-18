package com.unito.prenotazioniandroid.connection;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public abstract class ServerQuerier extends AsyncTask<String, Void, String> {
    private final String DEBUG_TAG = "debug";
    private final String SERVICE_URL = "http://10.0.2.2:8080/Ripetizioni/";
    private final String CHARSET = "UTF-8";
    private Context context; //TODO handle StaticFieldLeak
    private static URLConnection connection;

    static {
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
    }

    protected ServerQuerier(Context context) {
        this.context = context;
    }

    /*public Context getContext() {
        return context;
    }*/

    protected String getServiceUrl() {
        return SERVICE_URL;
    }

    protected String getCharset() {
        return CHARSET;
    }

    protected abstract String makeQuery() throws UnsupportedEncodingException;

    @Override
    protected String doInBackground(String... urls) {
        /*//parametri dalla excute() params[0] e' l'URL
        try {
            return downloadUrl(getUrl());
        } catch (IOException e) {
            e.printStackTrace();
            return "Unable to retrieve web page. URL may be invalid.";
        }*/
        String responseResult = "";
        try {
            responseResult = getResponseString(makeQuery());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            responseResult = "Unable to retrieve web page. Encoding may be invalid.";
        } catch (MalformedURLException m) {
            m.printStackTrace();
            responseResult = "Unable to retrieve web page. URL may be invalid.";
        } catch (IOException i) {
            i.printStackTrace();
            responseResult = "Unable to retrieve web page. IOException";
        }
        return responseResult;
    }

    private String getResponseString(String query) throws IOException {
        String responseResult;

        String charset = getCharset();
        String url = getServiceUrl() + "Controller";
        connection = new URL(url).openConnection();
        connection.setDoOutput(true); // Triggers POST.
        connection.setRequestProperty("Accept-Charset", charset);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

        Log.d(DEBUG_TAG, query);
        try (OutputStream output = connection.getOutputStream()) {
            output.write(query.getBytes(charset));
        }

        InputStream response = connection.getInputStream();
        Scanner scanner = new Scanner(response);
        String responseString = "";
        while (scanner.hasNext()) {
            responseString += scanner.next();
        }
        responseResult = responseString;
        return responseResult;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("debug", "Contenuto : " + result);
    }

    void makeToast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }
}
