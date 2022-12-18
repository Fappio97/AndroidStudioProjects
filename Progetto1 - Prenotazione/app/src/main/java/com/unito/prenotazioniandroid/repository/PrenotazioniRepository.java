package com.unito.prenotazioniandroid.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.unito.prenotazioniandroid.repository.network.PrenotazioniNetwork;
import com.unito.prenotazioniandroid.repository.network.paging.NetPrenotazioniDataSourceFactory;
import com.unito.prenotazioniandroid.repository.storge.PrenotazioniDatabase;
import com.unito.prenotazioniandroid.repository.storge.model.NetworkState;
import com.unito.prenotazioniandroid.repository.storge.model.Prenotazione;

import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class PrenotazioniRepository {
    private static final String TAG = PrenotazioniRepository.class.getSimpleName();
    private static PrenotazioniRepository instance;
    final private PrenotazioniNetwork network;
    final private PrenotazioniDatabase database;
    final private MediatorLiveData liveDataMerger;

    private PrenotazioniRepository(Context context) {

        NetPrenotazioniDataSourceFactory dataSourceFactory = new NetPrenotazioniDataSourceFactory();

        network = new PrenotazioniNetwork(dataSourceFactory, boundaryCallback);
        database = PrenotazioniDatabase.getInstance(context.getApplicationContext());
        // when we get new prenotazioni from net we set them into the database
        liveDataMerger = new MediatorLiveData<>();
        liveDataMerger.addSource(network.getPagedPrenotazioni(), new Observer() {
            @Override
            public void onChanged(Object value) {
                liveDataMerger.setValue(value);
                Log.d(TAG, value.toString());
            }
        });

        // save the prenotazioni into db
        dataSourceFactory.getPrenotazioni().
                observeOn(Schedulers.io()).
                subscribe(new Action1<Prenotazione>() {
                    @Override
                    public void call(Prenotazione prenotazione) {
                        database.prenotazioneDao().insertPrenotazione(prenotazione);
                    }
                });
    }

    private PagedList.BoundaryCallback<Prenotazione> boundaryCallback = new PagedList.BoundaryCallback<Prenotazione>() {
        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();
            liveDataMerger.addSource(database.getPrenotazioni(), new Observer() {
                @Override
                public void onChanged(Object value) {
                    liveDataMerger.setValue(value);
                    liveDataMerger.removeSource(database.getPrenotazioni());
                }
            });
        }

        @Override
        public void onItemAtFrontLoaded(Prenotazione itemAtFront) {
            super.onItemAtFrontLoaded(itemAtFront);
            //TODO tentativo aggiornamento 3
        }

    };

    public static PrenotazioniRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PrenotazioniRepository(context);
        }
        return instance;
    }

    public LiveData<PagedList<Prenotazione>> getPrenotazioni() {
        return liveDataMerger;
    }

    public LiveData<NetworkState> getNetworkState() {
        return network.getNetworkState();
    }

    public void refresh() {
        database.deletePrenotazioni();
        instance.getPrenotazioni().getValue().getDataSource().invalidate();
    }
}
