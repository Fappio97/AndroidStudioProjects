package com.unito.prenotazioniandroid.repository;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.unito.prenotazioniandroid.tabelle.FormSelection;

import java.util.ArrayList;

public class FormSelectionRepository {
    private static FormSelectionRepository instance;
    private FormSelectionObservable f_s_observable;

    private FormSelectionRepository(Context context) {
        FormSelection f_s = new FormSelection();
        f_s_observable = new FormSelectionObservable(f_s);
    }

    public static FormSelectionRepository getInstance(Context context) {
        if (instance == null) {
            instance = new FormSelectionRepository(context);
        }
        return instance;
    }

    public FormSelection getFormSelection() {
        return getF_s_observable().get();
    }

    public FormSelectionObservable getF_s_observable() {
        return f_s_observable;
    }

    public class FormSelectionObservable extends ObservableField<FormSelection>{
        public FormSelectionObservable(FormSelection serverFormSelections){
            super(serverFormSelections);
        }

        public void setCheckedHours(ArrayList<Integer> hours){
            this.get().setCheckedHours(hours);
            this.notifyChange();
        }

        public void setSelectedProf(Integer prof){
            this.get().setSelectedProf(prof);
            this.notifyChange();
        }

        public void setCheckedDays(ArrayList<String> days){
            this.get().setCheckedDays(days);
            this.notifyChange();
        }

        public void setSelectedCourse(String course){
            this.get().setSelectedCourse(course);
            this.notifyChange();
        }

        public boolean add(int i){
            boolean add = this.get().getCheckedHours().add(i);
            if(add)
                this.notifyChange();
            return add;
        }

        public boolean add(String i){
            boolean add = this.get().getCheckedDays().add(i);
            if(add)
                this.notifyChange();
            return add;
        }

        public boolean remove(Integer i){
            boolean removed = this.get().getCheckedHours().remove(i);
            if(removed)
                this.notifyChange();
            return removed;
        }

        public boolean remove(String i){
            boolean removed = this.get().getCheckedDays().remove(i);
            if(removed)
                this.notifyChange();
            return removed;
        }
    }
}
