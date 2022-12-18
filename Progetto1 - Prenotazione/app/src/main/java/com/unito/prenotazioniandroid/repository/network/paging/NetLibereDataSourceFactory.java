package com.unito.prenotazioniandroid.repository.network.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.unito.prenotazioniandroid.repository.storge.model.Libera;

import rx.subjects.ReplaySubject;

/*
    Responsible for creating the DataSource so we can give it to the PagedList.
 */
public class NetLibereDataSourceFactory extends DataSource.Factory {

    private static final String TAG = NetLibereDataSourceFactory.class.getSimpleName();
    private MutableLiveData<NetLiberePageKeyedDataSource> networkStatus;
    private NetLiberePageKeyedDataSource liberePageKeyedDataSource;

    public NetLibereDataSourceFactory() {
        this.networkStatus = new MutableLiveData<>();
        liberePageKeyedDataSource = new NetLiberePageKeyedDataSource();
    }


    @Override
    public DataSource create() {
        networkStatus.postValue(liberePageKeyedDataSource);
        return liberePageKeyedDataSource;
    }

    public MutableLiveData<NetLiberePageKeyedDataSource> getNetworkStatus() {
        return networkStatus;
    }

    public ReplaySubject<Libera> getLibere() {
        return liberePageKeyedDataSource.getLibere();
    }

}