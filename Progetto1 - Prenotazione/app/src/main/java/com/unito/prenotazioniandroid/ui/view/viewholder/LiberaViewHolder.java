package com.unito.prenotazioniandroid.ui.view.viewholder;
//package com.elad.themovie.ui.view.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.unito.prenotazioniandroid.R;
import com.unito.prenotazioniandroid.repository.storge.model.Libera;
import com.unito.prenotazioniandroid.ui.listeners.LibereItemClickListener;

import static com.unito.prenotazioniandroid.Constants.SMALL_IMAGE_URL_PREFIX;


public class LiberaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Libera movie;
    private TextView titleTextView;
    private TextView giornoEOraTextView;
    private TextView docenteTextView;
    private TextView statoTextView;
    //private ImageView thumbnailImageView;
    private LibereItemClickListener prenotazioniItemClickListener;

    public LiberaViewHolder(View view, LibereItemClickListener prenotazioniItemClickListener) {
        super(view);
        this.titleTextView = view.findViewById(R.id.title);
        this.giornoEOraTextView = view.findViewById(R.id.giornoEOra);
        this.docenteTextView = view.findViewById(R.id.docente);
        this.statoTextView = view.findViewById(R.id.stato);
        //this.thumbnailImageView = view.findViewById(R.id.thumbnail);
        this.prenotazioniItemClickListener = prenotazioniItemClickListener;

        view.setOnClickListener(this);

    }

    public void bindTo(Libera prenotazione) {
        this.movie = prenotazione;
        titleTextView.setText(prenotazione.getTitolo());
        int ora = Integer.parseInt(prenotazione.getOra());
        giornoEOraTextView.setText(String.format("%s %d.00-%d.00", prenotazione.getGiorno(), ora, ++ora));
        docenteTextView.setText(String.format("%s %s", prenotazione.getNome(), prenotazione.getCognome()));
        //statoTextView.setText(prenotazione.getStato());
        /*if (prenotazione.getId() != null) {
            String poster = SMALL_IMAGE_URL_PREFIX + prenotazione.getId();
            Picasso.get().load(poster).into(thumbnailImageView);
        }*/
    }

    @Override
    public void onClick(View view) {
        if (prenotazioniItemClickListener != null) {
            prenotazioniItemClickListener.OnItemClick(movie); // call the onClick in the OnItemClickListener
        }
    }

}