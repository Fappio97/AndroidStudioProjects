package com.unito.prenotazioniandroid.repository.storge;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.unito.prenotazioniandroid.repository.storge.model.Prenotazione;
import com.unito.prenotazioniandroid.repository.storge.paging.DBPrenotazioniDataSourceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.unito.prenotazioniandroid.Constants.DATA_BASE_NAME;
import static com.unito.prenotazioniandroid.Constants.NUMBERS_OF_THREADS;


/**
 * The Room database that contains the Users table
 */
@Database(entities = {Prenotazione.class}, version = 1)
public abstract class PrenotazioniDatabase extends RoomDatabase {

    private static PrenotazioniDatabase instance;

    public abstract PrenotazioneDao prenotazioneDao();

    private static final Object sLock = new Object();
    private LiveData<PagedList<Prenotazione>> prenotazioniPaged;

    public static PrenotazioniDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        PrenotazioniDatabase.class, DATA_BASE_NAME)
                        .build();
                instance.init();

            }
            return instance;
        }
    }

    private void init() {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(Integer.MAX_VALUE).setPageSize(Integer.MAX_VALUE).build();
        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);
        DBPrenotazioniDataSourceFactory dataSourceFactory = new DBPrenotazioniDataSourceFactory(prenotazioneDao());
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        prenotazioniPaged = livePagedListBuilder.setFetchExecutor(executor).build();
    }

    public LiveData<PagedList<Prenotazione>> getPrenotazioni() {
        return prenotazioniPaged;
    }

    public void deletePrenotazioni(){
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                prenotazioneDao().deleteAllPrenotazioni();
            }
        });
    }
}