package com.unito.prenotazioniandroid.repository.network;
//package com.elad.themovie.service.repository.network;


import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.unito.prenotazioniandroid.repository.network.paging.NetPrenotazioniDataSourceFactory;
import com.unito.prenotazioniandroid.repository.network.paging.NetPrenotazioniPageKeyedDataSource;
import com.unito.prenotazioniandroid.repository.storge.model.NetworkState;
import com.unito.prenotazioniandroid.repository.storge.model.Prenotazione;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.unito.prenotazioniandroid.Constants.LOADING_PAGE_SIZE;
import static com.unito.prenotazioniandroid.Constants.NUMBERS_OF_THREADS;

/**
 * Created by Elad on 6/25/2018.
 */

public class PrenotazioniNetwork {

    final private static String TAG = PrenotazioniNetwork.class.getSimpleName();
    final private LiveData<PagedList<Prenotazione>> moviesPaged;
    final private LiveData<NetworkState> networkState;

    public PrenotazioniNetwork(NetPrenotazioniDataSourceFactory dataSourceFactory, PagedList.BoundaryCallback<Prenotazione> boundaryCallback) {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(LOADING_PAGE_SIZE).setPageSize(LOADING_PAGE_SIZE).build();
        networkState = Transformations.switchMap(dataSourceFactory.getNetworkStatus(),
                new Function<NetPrenotazioniPageKeyedDataSource, LiveData<NetworkState>>() {
                    @Override
                    public LiveData<NetworkState> apply(NetPrenotazioniPageKeyedDataSource p) {
                        return p.getNetworkState();
                    }
                });
        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        moviesPaged = livePagedListBuilder.
                setFetchExecutor(executor).
                setBoundaryCallback(boundaryCallback).
                build();

    }


    public LiveData<PagedList<Prenotazione>> getPagedPrenotazioni() {
        return moviesPaged;
    }


    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

}
