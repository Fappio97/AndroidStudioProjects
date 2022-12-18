package com.unito.prenotazioniandroid.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.unito.prenotazioniandroid.repository.network.LibereNetwork;
import com.unito.prenotazioniandroid.repository.network.paging.NetLibereDataSourceFactory;
import com.unito.prenotazioniandroid.repository.storge.model.Libera;
import com.unito.prenotazioniandroid.repository.storge.model.NetworkState;

//import com.unito.prenotazioniandroid.repository.storge.LibereDatabase;

//Rielaborazione di https://github.com/EladBenDavid/TMDb/tree/master/app/src/main/java/com/elad/themovie/service/repository
public class LibereRepository {
    private static final String TAG = LibereRepository.class.getSimpleName();
    private static LibereRepository instance;
    final private LibereNetwork network;
    //    final private LibereDatabase database;
    final private MediatorLiveData liveDataMerger;

    private LibereRepository(Context context) {

        NetLibereDataSourceFactory dataSourceFactory = new NetLibereDataSourceFactory();

        network = new LibereNetwork(dataSourceFactory, boundaryCallback);
//        database = LibereDatabase.getInstance(context.getApplicationContext());
        // when we get new prenotazioni from net we set them into the database
        liveDataMerger = new MediatorLiveData<>();
        liveDataMerger.addSource(network.getPagedLibere(), new Observer() {
            @Override
            public void onChanged(Object value) {
                liveDataMerger.setValue(value);
                Log.d(TAG, value.toString());
            }
        });

        // save the prenotazioni into db
        /*dataSourceFactory.getLibere().
                observeOn(Schedulers.io()).
                subscribe(new Action1<Libera>() {
                    @Override
                    public void call(Libera prenotazione) {
                        database.prenotazioneDao().insertLibera(prenotazione);
                    }
                });*/

    }

    private PagedList.BoundaryCallback<Libera> boundaryCallback = new PagedList.BoundaryCallback<Libera>() {
        @Override
        public void onZeroItemsLoaded() {
            /*super.onZeroItemsLoaded();
            liveDataMerger.addSource(database.getLibere(), new Observer() {
                @Override
                public void onChanged(Object value) {
                    liveDataMerger.setValue(value);
                    liveDataMerger.removeSource(database.getLibere());
                }
            });*/
        }
    };

    public static LibereRepository getInstance(Context context) {
        if (instance == null) {
            instance = new LibereRepository(context);
        }
        return instance;
    }

    public LiveData<PagedList<Libera>> getLibere() {
        return liveDataMerger;
    }

    public LiveData<NetworkState> getNetworkState() {
        return network.getNetworkState();
    }
}
