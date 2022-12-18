package com.unito.prenotazioniandroid.repository.network;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.unito.prenotazioniandroid.repository.network.paging.NetLibereDataSourceFactory;
import com.unito.prenotazioniandroid.repository.network.paging.NetLiberePageKeyedDataSource;
import com.unito.prenotazioniandroid.repository.storge.model.Libera;
import com.unito.prenotazioniandroid.repository.storge.model.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.unito.prenotazioniandroid.Constants.LOADING_PAGE_SIZE;
import static com.unito.prenotazioniandroid.Constants.NUMBERS_OF_THREADS;

/**
 * Created by Elad on 6/25/2018.
 */

public class LibereNetwork {

    final private static String TAG = LibereNetwork.class.getSimpleName();
    final private LiveData<PagedList<Libera>> liberePaged;
    final private LiveData<NetworkState> networkState;

    public LibereNetwork(NetLibereDataSourceFactory dataSourceFactory, PagedList.BoundaryCallback<Libera> boundaryCallback) {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(LOADING_PAGE_SIZE).setPageSize(LOADING_PAGE_SIZE).build();
        networkState = Transformations.switchMap(dataSourceFactory.getNetworkStatus(),
                new Function<NetLiberePageKeyedDataSource, LiveData<NetworkState>>() {
                    @Override
                    public LiveData<NetworkState> apply(NetLiberePageKeyedDataSource p) {
                        return p.getNetworkState();
                    }
                });
        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        liberePaged = livePagedListBuilder.
                setFetchExecutor(executor).
                setBoundaryCallback(boundaryCallback).
                build();

    }


    public LiveData<PagedList<Libera>> getPagedLibere() {
        return liberePaged;
    }


    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

}
