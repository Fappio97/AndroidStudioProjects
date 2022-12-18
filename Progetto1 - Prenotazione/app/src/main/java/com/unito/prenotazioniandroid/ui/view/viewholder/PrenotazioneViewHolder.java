package com.unito.prenotazioniandroid.ui.view.viewholder;
//package com.elad.themovie.ui.view.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.unito.prenotazioniandroid.R;
import com.unito.prenotazioniandroid.repository.storge.model.Prenotazione;
import com.unito.prenotazioniandroid.ui.listeners.PrenotazioniItemClickListener;

import static com.unito.prenotazioniandroid.Constants.SMALL_IMAGE_URL_PREFIX;


public class PrenotazioneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Prenotazione prenotazione;
    private ObservableField<Prenotazione> prenotazioneObservableField;
    private TextView titleTextView;
    private TextView giornoEOraTextView;
    private TextView docenteTextView;
    private TextView statoTextView;
    private ImageView thumbnailImageView;
    private Button confirmButton;
    private Button debookButton;
    private PrenotazioniItemClickListener prenotazioniItemClickListener;

    public PrenotazioneViewHolder(View view, PrenotazioniItemClickListener prenotazioniItemClickListener) {
        super(view);
        this.titleTextView = view.findViewById(R.id.title);
        this.giornoEOraTextView = view.findViewById(R.id.giornoEOra);
        this.docenteTextView = view.findViewById(R.id.docente);
        this.statoTextView = view.findViewById(R.id.stato);
        //this.thumbnailImageView = view.findViewById(R.id.thumbnail);
        /*View buttonParent = view.findViewById(R.id.buttonParent);
        this.confirmButton = buttonParent.findViewById(R.id.conferma);
        this.debookButton = buttonParent.findViewById(R.id.disdici);*/
        this.prenotazioniItemClickListener = prenotazioniItemClickListener;
        view.setOnClickListener(this);

    }

    public void bindTo(Prenotazione prenotazione) {
        this.prenotazione = prenotazione;
//        this.prenotazioneObservableField = new ObservableField<>(prenotazione);
        refreshFragment();
        /*prenotazioneObservableField.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                refreshFragment();
            }
        });*/
    }

    private void refreshFragment(){
        titleTextView.setText(prenotazione.getTitolo());
        int ora = Integer.parseInt(prenotazione.getOra());
        giornoEOraTextView.setText(String.format("%s %d.00-%d.00", prenotazione.getGiorno(), ora, ++ora));
        docenteTextView.setText(String.format("%s %s", prenotazione.getNome(), prenotazione.getCognome()));
        statoTextView.setText(prenotazione.getStato());
    }

    private boolean buttonsAreNotNull(Button confirmButton, Button debookButton) {
        String msg = "";
        if(confirmButton == null)
            msg += "confirmButton is null ";
        if(debookButton == null)
            msg += "debookButton is null ";
        Log.d(this.getClass().getSimpleName() + " ERROR", msg);
        return confirmButton == null || debookButton == null;
    }

    @Override
    public void onClick(View view) {
        if (prenotazioniItemClickListener != null) {
            prenotazioniItemClickListener.OnItemClick(prenotazione); // call the onClick in the OnItemClickListener
        }
    }

}