package com.unito.prenotazioniandroid.connection;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class DownloadWebpageTask extends AsyncTask<String, Void, String> {
    private final String DEBUG_TAG = "debug";
    private final String SERVICE_URL = "http://10.0.2.2:8080/Ripetizioni/";

    @Override
    protected String doInBackground(String... urls) {
        //parametri dalla excute() params[0] e' l'URL
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }

    // onPostExecute mostrera' I risultati del Task, nel nostro caso I primi caratteri della pagina richiesta
    @Override
    protected void onPostExecute(String result) {
        //TextView contenuto = (TextView) findViewById(R.id.content);//Not in use
        //contenuto.setText(result);
        Log.d(DEBUG_TAG, "Contenuto : " + result);
        cancel(true);
    }
    // Se l’ Activity viene distrutta, l’Asynctask sopravvive ma non ha piu’ riferimento a lei.

    protected String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // setta alcuni parametri (vedi esempio completo)
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "Response code = " + response);
            is = conn.getInputStream();
            Scanner scanner = new Scanner(is);
            //String contentAsString = Html.toHtml(new SpannedString(scanner.nextLine()));//readIt(is, len);
            String contentAsString = "";
            while (scanner.hasNext()) {
                contentAsString += scanner.next();
            }
            return contentAsString;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    protected String getServiceUrl() {
        return SERVICE_URL;
    }
}
