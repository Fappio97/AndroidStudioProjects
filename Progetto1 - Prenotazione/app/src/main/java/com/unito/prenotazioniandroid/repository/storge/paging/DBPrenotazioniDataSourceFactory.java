package com.unito.prenotazioniandroid.repository.storge.paging;

import androidx.paging.DataSource;

import com.unito.prenotazioniandroid.repository.storge.PrenotazioneDao;


public class DBPrenotazioniDataSourceFactory extends DataSource.Factory {

    private static final String TAG = DBPrenotazioniDataSourceFactory.class.getSimpleName();
    private DBPrenotazioniPageKeyedDataSource moviesPageKeyedDataSource;

    public DBPrenotazioniDataSourceFactory(PrenotazioneDao dao) {
        moviesPageKeyedDataSource = new DBPrenotazioniPageKeyedDataSource(dao);
    }

    @Override
    public DataSource create() {
        return moviesPageKeyedDataSource;
    }

}