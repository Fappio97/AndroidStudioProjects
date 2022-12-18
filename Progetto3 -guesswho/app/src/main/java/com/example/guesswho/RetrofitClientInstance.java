package com.example.guesswho;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitClientInstance {

    static Retrofit getRetrofitInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://chiaravaudano.pythonanywhere.com")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();

        return retrofit;
    }
}
