package com.unito.prenotazioniandroid.repository.storge.paging;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.unito.prenotazioniandroid.repository.storge.PrenotazioneDao;
import com.unito.prenotazioniandroid.repository.storge.model.Prenotazione;

import java.util.List;

/**
 * Created by Elad on 6/25/2018.
 */

public class DBPrenotazioniPageKeyedDataSource extends PageKeyedDataSource<String, Prenotazione> {

    public static final String TAG = DBPrenotazioniPageKeyedDataSource.class.getSimpleName();
    private final PrenotazioneDao prenotazioneDao;

    public DBPrenotazioniPageKeyedDataSource(PrenotazioneDao dao) {
        prenotazioneDao = dao;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, Prenotazione> callback) {
        Log.i(TAG, "Loading Initial Rang, Count " + params.requestedLoadSize);
        List<Prenotazione> prenotaziones = prenotazioneDao.getPrenotazioni();
        if (prenotaziones.size() != 0) {
            callback.onResult(prenotaziones, "0", "1");
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, Prenotazione> callback) {
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Prenotazione> callback) {
    }
}
