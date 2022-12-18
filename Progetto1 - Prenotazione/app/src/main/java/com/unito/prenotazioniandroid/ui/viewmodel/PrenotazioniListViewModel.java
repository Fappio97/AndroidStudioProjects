package com.unito.prenotazioniandroid.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.unito.prenotazioniandroid.repository.PrenotazioniRepository;
import com.unito.prenotazioniandroid.repository.storge.model.NetworkState;
import com.unito.prenotazioniandroid.repository.storge.model.Prenotazione;

public class PrenotazioniListViewModel extends AndroidViewModel {
    private PrenotazioniRepository repository;

    public PrenotazioniListViewModel(@NonNull Application application) {
        super(application);
        repository = PrenotazioniRepository.getInstance(application);
    }

    public LiveData<PagedList<Prenotazione>> getPrenotazioni() {
        return repository.getPrenotazioni();
    }

    public LiveData<NetworkState> getNetworkState() {
        return repository.getNetworkState();
    }

}