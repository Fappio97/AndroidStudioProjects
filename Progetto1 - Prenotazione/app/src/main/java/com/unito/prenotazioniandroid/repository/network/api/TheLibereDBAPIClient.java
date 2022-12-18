package com.unito.prenotazioniandroid.repository.network.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.CookieManager;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.unito.prenotazioniandroid.Constants.LIBERE_ARRAY_LIST_CLASS_TYPE;
import static com.unito.prenotazioniandroid.Constants.POPULAR_PRENOTAZIONI_BASE_URL;

public class TheLibereDBAPIClient {

    public static LibereAPIInterface getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // create OkHttpClient and register an interceptor
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).cookieJar(
                new JavaNetCookieJar(CookieManager.getDefault())).build();

        Gson gson = new GsonBuilder()
                // we remove from the response some wrapper tags from our movies array
                .registerTypeAdapter(LIBERE_ARRAY_LIST_CLASS_TYPE, new LibereJsonDeserializer())
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .baseUrl(POPULAR_PRENOTAZIONI_BASE_URL);

        return builder.build().create(LibereAPIInterface.class);
    }
}
