package com.unito.prenotazioniandroid.repository.network.paging;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unito.prenotazioniandroid.repository.FormSelectionRepository;
import com.unito.prenotazioniandroid.repository.PageLibereRepository;
import com.unito.prenotazioniandroid.repository.network.api.LibereAPIInterface;
import com.unito.prenotazioniandroid.repository.network.api.TheLibereDBAPIClient;
import com.unito.prenotazioniandroid.repository.storge.model.Libera;
import com.unito.prenotazioniandroid.repository.storge.model.NetworkState;
import com.unito.prenotazioniandroid.tabelle.FormSelection;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.subjects.ReplaySubject;

import static com.unito.prenotazioniandroid.Constants.ACTION_LIBERE_KEY;

/**
 * Created by Elad on 6/25/2018.
 * <p>
 * Responsible for loading the data by page
 */

public class NetLiberePageKeyedDataSource extends PageKeyedDataSource<String, Libera> {

    private static final String TAG = NetLiberePageKeyedDataSource.class.getSimpleName();
    private final LibereAPIInterface libereService;
    private final MutableLiveData networkState;
    private final ReplaySubject<Libera> libereObservable;

    NetLiberePageKeyedDataSource() {
        libereService = TheLibereDBAPIClient.getClient();
        networkState = new MutableLiveData();
        libereObservable = ReplaySubject.create();
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public ReplaySubject<Libera> getLibere() {
        return libereObservable;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, Libera> callback) {
        Log.i(TAG, "Loading Initial Rang, Count " + params.requestedLoadSize);

        networkState.postValue(NetworkState.LOADING);

        FormSelection f_s = FormSelectionRepository.getInstance(null).getFormSelection();
        Gson gson = new GsonBuilder().serializeNulls().create();
        Call<ArrayList<Libera>> callBack = libereService.getLibere(ACTION_LIBERE_KEY, gson.toJson(f_s), 1);
        String url = "URL is " + callBack.request().url().url().toString(); //Alfo
        Log.d("REQ_URL", url); //Alfo
        System.out.println(url); //Alfo
        callBack.enqueue(new Callback<ArrayList<Libera>>() {
            @Override
            public void onResponse(Call<ArrayList<Libera>> call, Response<ArrayList<Libera>> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body(), Integer.toString(1), Integer.toString(2));
                    networkState.postValue(NetworkState.LOADED);

                    checkResponseNullity(response);

                    response.body().forEach(new Consumer<Libera>() {
                        @Override
                        public void accept(Libera libera) {
                            libereObservable.onNext(libera);
                        }
                    });
                } else {
                    Log.e("API CALL", response.message());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            private void checkResponseNullity(Response<ArrayList<Libera>> response) {
                String s = "";
                if (response == null)
                    s = "Response is null";
                else if (response.body() == null)
                    s = "Response body is null";
                Log.d(this.getClass().getSimpleName() + " NULLITY", s);
            }

            @Override
            public void onFailure(Call<ArrayList<Libera>> call, Throwable t) {
                String errorMessage;
                if (t.getMessage() == null) {
                    errorMessage = "unknown error";
                } else {
                    errorMessage = t.getMessage();
                }
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                callback.onResult(new ArrayList<Libera>(), Integer.toString(1), Integer.toString(2));
            }
        });
    }


    @Override
    public void loadAfter(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, Libera> callback) {
        Log.i(TAG, "Loading page " + params.key);
        networkState.postValue(NetworkState.LOADING);
        final AtomicInteger page = PageLibereRepository.getInstance(null).getPageLibere();
        try {
            page.set(Integer.parseInt(params.key));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        FormSelection f_s = FormSelectionRepository.getInstance(null).getFormSelection();
        Gson gson = new GsonBuilder().serializeNulls().create();
        Call<ArrayList<Libera>> callBack = libereService.getLibere(ACTION_LIBERE_KEY, gson.toJson(f_s), page.get());
        callBack.enqueue(new Callback<ArrayList<Libera>>() {
            @Override
            public void onResponse(Call<ArrayList<Libera>> call, final Response<ArrayList<Libera>> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body(), Integer.toString(page.get() + 1));
                    networkState.postValue(NetworkState.LOADED);
                    response.body().forEach(new Consumer<Libera>() {
                        @Override
                        public void accept(Libera libera) {
                            libereObservable.onNext(libera);
                        }
                    });
                } else {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    Log.e("API CALL", response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Libera>> call, Throwable t) {
                String errorMessage;
                if (t.getMessage() == null) {
                    errorMessage = "unknown error";
                } else {
                    errorMessage = t.getMessage();
                }
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                callback.onResult(new ArrayList<Libera>(), Integer.toString(page.get()));
            }
        });
    }


    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Libera> callback) {

    }
}
