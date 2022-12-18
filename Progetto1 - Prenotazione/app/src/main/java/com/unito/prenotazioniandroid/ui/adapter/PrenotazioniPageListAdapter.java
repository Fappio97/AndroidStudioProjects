package com.unito.prenotazioniandroid.ui.adapter;
//package com.elad.themovie.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.unito.prenotazioniandroid.R;
import com.unito.prenotazioniandroid.repository.storge.model.NetworkState;
import com.unito.prenotazioniandroid.repository.storge.model.Prenotazione;
import com.unito.prenotazioniandroid.ui.listeners.PrenotazioniItemClickListener;
import com.unito.prenotazioniandroid.ui.view.viewholder.NetworkStateItemViewHolder;
import com.unito.prenotazioniandroid.ui.view.viewholder.PrenotazioneViewHolder;

/**
 * Created by Elad on 6/25/2018.
 */

public class PrenotazioniPageListAdapter extends PagedListAdapter<Prenotazione, RecyclerView.ViewHolder> {

    private NetworkState networkState;
    private PrenotazioniItemClickListener prenotazioniItemClickListener;

    public PrenotazioniPageListAdapter(PrenotazioniItemClickListener prenotazioniItemClickListener) {
        super(Prenotazione.DIFF_CALLBACK);
        this.prenotazioniItemClickListener = prenotazioniItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == R.layout.prenotazione_item) {
            View view = layoutInflater.inflate(R.layout.prenotazione_item, parent, false);
            PrenotazioneViewHolder viewHolder = new PrenotazioneViewHolder(view, prenotazioniItemClickListener);
            return viewHolder;
        } else if (viewType == R.layout.network_state_item) {
            View view = layoutInflater.inflate(R.layout.network_state_item, parent, false);
            return new NetworkStateItemViewHolder(view);
        } else {
            throw new IllegalArgumentException("unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.prenotazione_item:
                ((PrenotazioneViewHolder) holder).bindTo(getItem(position));
                break;
            case R.layout.network_state_item:
                ((NetworkStateItemViewHolder) holder).bindView(networkState);
                break;
        }
    }

    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.network_state_item;
        } else {
            return R.layout.prenotazione_item;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }
}