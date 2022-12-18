package com.unito.prenotazioniandroid.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.unito.prenotazioniandroid.repository.storge.model.Libera;

public class LiberaDetailsViewModel extends ViewModel {
    final private MutableLiveData movie;

    public LiberaDetailsViewModel() {
        movie = new MutableLiveData<Libera>();
    }

    public MutableLiveData<Libera> getLibera() {
        return movie;
    }
}