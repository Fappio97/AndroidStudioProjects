package com.unito.prenotazioniandroid.repository.storge;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.unito.prenotazioniandroid.repository.storge.model.Prenotazione;

import java.util.List;

/**
 * Data Access Object for the prenotazioni table.
 */
@Dao
public interface PrenotazioneDao {

    /**
     * Get the Prenotazioni from the table.
     * -------------------------------
     * Since the DB use as caching, we don't return LiveData.
     * We don't need to get update every time the database update.
     * We using the get query when application start. So, we able to display
     * data fast and in case we don't have connection to work offline.
     *
     * @return the prenotazioni from the table
     */
    @Query("SELECT * FROM prenotazioni")
    List<Prenotazione> getPrenotazioni();

    /**
     * Insert a prenotazione in the database. If the prenotazione already exists, replace it.
     *
     * @param prenotazione the prenotazione to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
//former: IGNORE
    void insertPrenotazione(Prenotazione prenotazione);

    @Query("DELETE FROM prenotazioni")
    abstract void deleteAllPrenotazioni();
}