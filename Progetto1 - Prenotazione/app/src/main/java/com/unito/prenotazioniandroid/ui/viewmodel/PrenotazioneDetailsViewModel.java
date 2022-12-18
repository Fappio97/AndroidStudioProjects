package com.unito.prenotazioniandroid.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.unito.prenotazioniandroid.repository.storge.model.Prenotazione;

public class PrenotazioneDetailsViewModel extends ViewModel {
    final private MutableLiveData prenotazione;

    public PrenotazioneDetailsViewModel() {
        prenotazione = new MutableLiveData<Prenotazione>();
    }

    public MutableLiveData<Prenotazione> getPrenotazione() {
        return prenotazione;
    }
}