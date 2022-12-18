package com.unito.prenotazioniandroid.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.unito.prenotazioniandroid.repository.LibereRepository;
import com.unito.prenotazioniandroid.repository.storge.model.Libera;
import com.unito.prenotazioniandroid.repository.storge.model.NetworkState;

public class LibereListViewModel extends AndroidViewModel {
    private LibereRepository repository;

    public LibereListViewModel(@NonNull Application application) {
        super(application);
        repository = LibereRepository.getInstance(application);
    }

    public LiveData<PagedList<Libera>> getLibere() {
        return repository.getLibere();
    }

    public LiveData<NetworkState> getNetworkState() {
        return repository.getNetworkState();
    }

}