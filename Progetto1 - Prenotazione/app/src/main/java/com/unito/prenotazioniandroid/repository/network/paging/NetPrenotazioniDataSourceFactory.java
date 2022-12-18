package com.unito.prenotazioniandroid.repository.network.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.unito.prenotazioniandroid.repository.storge.model.Prenotazione;

import rx.subjects.ReplaySubject;

/*
    Responsible for creating the DataSource so we can give it to the PagedList.
 */
public class NetPrenotazioniDataSourceFactory extends DataSource.Factory {

    private static final String TAG = NetPrenotazioniDataSourceFactory.class.getSimpleName();
    private MutableLiveData<NetPrenotazioniPageKeyedDataSource> networkStatus;
    private NetPrenotazioniPageKeyedDataSource prenotazioniPageKeyedDataSource;

    public NetPrenotazioniDataSourceFactory() {
        this.networkStatus = new MutableLiveData<>();
        prenotazioniPageKeyedDataSource = new NetPrenotazioniPageKeyedDataSource();
    }


    @Override
    public DataSource create() {
        networkStatus.postValue(prenotazioniPageKeyedDataSource);
        return prenotazioniPageKeyedDataSource;
    }

    public MutableLiveData<NetPrenotazioniPageKeyedDataSource> getNetworkStatus() {
        return networkStatus;
    }

    public ReplaySubject<Prenotazione> getPrenotazioni() {
        return prenotazioniPageKeyedDataSource.getPrenotazioni();
    }

}