package com.unito.prenotazioniandroid.repository;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.unito.prenotazioniandroid.tabelle.Corso;
import com.unito.prenotazioniandroid.tabelle.Docente;
import com.unito.prenotazioniandroid.tabelle.ServerFormSelections;

import java.util.ArrayList;
import java.util.List;

public class ServerFormSelectionRepository {
    private static ServerFormSelectionRepository instance;
    private ServerObservable f_s_observable;

    private ServerFormSelectionRepository(Context context) {
        ServerFormSelections f_s = new ServerFormSelections();
        f_s_observable = new ServerObservable(f_s);
    }

    public static ServerFormSelectionRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ServerFormSelectionRepository(context);
        }
        return instance;
    }

    public ServerFormSelections getFormSelection() {
        return getF_s_observable().get();
    }

    public ObservableField<ServerFormSelections> getF_s_observable() {
        return f_s_observable;
    }

    public class ServerObservable extends ObservableField<ServerFormSelections>{
        public ServerObservable(ServerFormSelections serverFormSelections){
            super(serverFormSelections);
        }

        public void setDays(String[] days){
            this.get().setDays(days);
            this.notifyChange();
        }

        public void setProfessors(List<Docente> docenteList){
            this.get().setProfessors(docenteList);
            this.notifyChange();
        }

        public void setDays(List<Corso> corsoList){
            this.get().setCourses(corsoList);
            this.notifyChange();
        }

        public void setHours(ArrayList<Integer> hours){
            this.get().setHours(hours);
            this.notifyChange();
        }
    }
}
