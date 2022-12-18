package com.unito.prenotazioniandroid.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.unito.prenotazioniandroid.R;
import com.unito.prenotazioniandroid.repository.storge.model.Libera;
import com.unito.prenotazioniandroid.repository.storge.model.NetworkState;
import com.unito.prenotazioniandroid.ui.listeners.LibereItemClickListener;
import com.unito.prenotazioniandroid.ui.view.viewholder.LiberaViewHolder;
import com.unito.prenotazioniandroid.ui.view.viewholder.NetworkStateItemViewHolder;

/**
 * Created by Elad on 6/25/2018.
 */

public class LiberePageListAdapter extends PagedListAdapter<Libera, RecyclerView.ViewHolder> {

    private NetworkState networkState;
    private LibereItemClickListener libereItemClickListener;

    public LiberePageListAdapter(LibereItemClickListener libereItemClickListener) {
        super(Libera.DIFF_CALLBACK);
        this.libereItemClickListener = libereItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == R.layout.libera_item) {
            View view = layoutInflater.inflate(R.layout.libera_item, parent, false);
            LiberaViewHolder viewHolder = new LiberaViewHolder(view, libereItemClickListener);
            ;
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
            case R.layout.libera_item:
                ((LiberaViewHolder) holder).bindTo(getItem(position));
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
            return R.layout.libera_item;
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