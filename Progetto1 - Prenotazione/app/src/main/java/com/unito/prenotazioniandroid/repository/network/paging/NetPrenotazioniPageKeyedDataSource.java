package com.unito.prenotazioniandroid.repository.network.paging;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.unito.prenotazioniandroid.repository.PagePrenotazioniRepository;
import com.unito.prenotazioniandroid.repository.PrenotazioniRepository;
import com.unito.prenotazioniandroid.repository.network.api.PrenotazioniAPIInterface;
import com.unito.prenotazioniandroid.repository.network.api.ThePrenotazioniDBAPIClient;
import com.unito.prenotazioniandroid.repository.storge.model.NetworkState;
import com.unito.prenotazioniandroid.repository.storge.model.Prenotazione;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.subjects.ReplaySubject;

import static com.unito.prenotazioniandroid.Constants.ACTION_PRENOTAZIONI_KEY;

/**
 * Created by Elad on 6/25/2018.
 * <p>
 * Responsible for loading the data by page
 */

public class NetPrenotazioniPageKeyedDataSource extends PageKeyedDataSource<String, Prenotazione> {

    private static final String TAG = NetPrenotazioniPageKeyedDataSource.class.getSimpleName();
    private final PrenotazioniAPIInterface prenotazioniService;
    private final MutableLiveData networkState;
    private final ReplaySubject<Prenotazione> prenotazioniObservable;
    private final AtomicInteger pagePrenotazioni;

    NetPrenotazioniPageKeyedDataSource() {
        prenotazioniService = ThePrenotazioniDBAPIClient.getClient();
        networkState = new MutableLiveData();
        prenotazioniObservable = ReplaySubject.create();
        pagePrenotazioni = PagePrenotazioniRepository.getInstance(null).getPagePrenotazioni();
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public ReplaySubject<Prenotazione> getPrenotazioni() {
        return prenotazioniObservable;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, Prenotazione> callback) {
        Log.i(TAG, "Loading Initial Rang, Count " + params.requestedLoadSize);

        networkState.postValue(NetworkState.LOADING);
        Call<ArrayList<Prenotazione>> callBack = prenotazioniService.getPrenotazioni(ACTION_PRENOTAZIONI_KEY, 1);
        //PagePrenotazioniRepository.getInstance(null).getPagePrenotazioni().set(0);
        String url = "URL is " + callBack.request().url().url().toString(); //Alfo
        Log.d("REQ_URL", url); //Alfo
        System.out.println(url); //Alfo
        callBack.enqueue(new Callback<ArrayList<Prenotazione>>() {
            @Override
            public void onResponse(Call<ArrayList<Prenotazione>> call, Response<ArrayList<Prenotazione>> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body(), Integer.toString(1), Integer.toString(2));
                    networkState.postValue(NetworkState.LOADED);
                    response.body().forEach(new Consumer<Prenotazione>() {
                        @Override
                        public void accept(Prenotazione prenotazione) {
                            prenotazioniObservable.onNext(prenotazione);
                        }
                    });
                } else {
                    Log.e("API CALL", response.message());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Prenotazione>> call, Throwable t) {
                String errorMessage;
                if (t.getMessage() == null) {
                    errorMessage = "unknown error";
                    Log.d(TAG, "Unknown error into load Initial on failure");
                } else {
                    errorMessage = t.getMessage();
                }
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                callback.onResult(new ArrayList<Prenotazione>(), Integer.toString(1), Integer.toString(2));
            }
        });
    }


    @Override
    public void loadAfter(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, Prenotazione> callback) {
        Log.i(TAG, "Loading page " + params.key);
        networkState.postValue(NetworkState.LOADING);
        try {
            pagePrenotazioni.set(Integer.parseInt(params.key));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Call<ArrayList<Prenotazione>> callBack = prenotazioniService.getPrenotazioni(ACTION_PRENOTAZIONI_KEY, pagePrenotazioni.get());
        callBack.enqueue(new Callback<ArrayList<Prenotazione>>() {
            @Override
            public void onResponse(Call<ArrayList<Prenotazione>> call, final Response<ArrayList<Prenotazione>> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body(), Integer.toString(pagePrenotazioni.get() +1 ));
                    networkState.postValue(NetworkState.LOADED);
                    response.body().forEach(new Consumer<Prenotazione>() {
                        @Override
                        public void accept(Prenotazione prenotazione) {
                            prenotazioniObservable.onNext(prenotazione);
                        }
                    });
                } else {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    Log.e("API CALL", response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Prenotazione>> call, Throwable t) {
                String errorMessage;
                if (t.getMessage() == null) {
                    errorMessage = "unknown error";
                    Log.d(TAG, "Unknown error into load After on failure");
                } else {
                    errorMessage = t.getMessage();
                }
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                callback.onResult(new ArrayList<Prenotazione>(), Integer.toString(pagePrenotazioni.get()));
            }
        });
    }


    //TODO tentativo aggiornamento 1
    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull final LoadCallback<String, Prenotazione> callback) {
        /*final int page = 1;
        final int nextPage = 1;
        Log.i(TAG, "Loading page " + params.key);
        networkState.postValue(NetworkState.LOADING);
        Call<ArrayList<Prenotazione>> callBack = prenotazioniService.getPrenotazioni(ACTION_PRENOTAZIONI_KEY, page);
        callBack.enqueue(new Callback<ArrayList<Prenotazione>>() {
            @Override
            public void onResponse(Call<ArrayList<Prenotazione>> call, final Response<ArrayList<Prenotazione>> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body(), Integer.toString(nextPage));
                    networkState.postValue(NetworkState.LOADED);
                    response.body().forEach(new Consumer<Prenotazione>() {
                        @Override
                        public void accept(Prenotazione prenotazione) {
                            //if(!PrenotazioniRepository.getInstance(null).getPrenotazioni().getValue().contains(prenotazione))//Alf
                                prenotazioniObservable.onNext(prenotazione);
                        }
                    });
                } else {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    Log.e("API CALL", response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Prenotazione>> call, Throwable t) {
                String errorMessage;
                if (t.getMessage() == null) {
                    errorMessage = "unknown error";
                    Log.d(TAG, "Unknown error into load Before on failure " + t.getClass().getName());//Alf
                    Log.d(TAG, "Unknown error into load Before on failure :stack trace ", t);//Alf
                } else {
                    errorMessage = t.getMessage();
                }
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                callback.onResult(new ArrayList<Prenotazione>(), Integer.toString(page));
            }
        });*/
    }
}
