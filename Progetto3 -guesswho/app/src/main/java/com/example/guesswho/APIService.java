package com.example.guesswho;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;


interface APIService {

        @GET("/people")
        Call<ResponseBody> getPeople();

        @GET("/filtered_data")
        Call<ResponseBody> finalPerson();

        @GET("/questions")
        Call<ResponseBody> getQuestions();

        @DELETE("/questions/{key}")
        Call<ResponseBody> removeQuestions(@Path("key") String key);

        @GET("questions/clear")
        Call<ResponseBody> clearQuestions();

        @DELETE("/people/{button_value}/{feature}/{maximun}/{data}")
        Call<ResponseBody> deleteUsers(@Path("button_value") String button_value, @Path("feature") String feature, @Path("maximun") String maximun, @Path("data") List<String> data);

}



