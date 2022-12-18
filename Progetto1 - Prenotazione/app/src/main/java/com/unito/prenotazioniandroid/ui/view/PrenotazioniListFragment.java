package com.unito.prenotazioniandroid.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unito.prenotazioniandroid.R;
import com.unito.prenotazioniandroid.repository.storge.model.NetworkState;
import com.unito.prenotazioniandroid.repository.storge.model.Prenotazione;
import com.unito.prenotazioniandroid.ui.adapter.PrenotazioniPageListAdapter;
import com.unito.prenotazioniandroid.ui.listeners.PrenotazioniItemClickListener;
import com.unito.prenotazioniandroid.ui.viewmodel.PrenotazioneDetailsViewModel;
import com.unito.prenotazioniandroid.ui.viewmodel.PrenotazioniListViewModel;

public class PrenotazioniListFragment extends Fragment implements PrenotazioniItemClickListener {
    protected PrenotazioniListViewModel viewModel;
    private PrenotazioneDetailsViewModel detailsViewModel;

    protected RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prenotazioni, container, false);
        recyclerView = view.findViewById(R.id.prenotazioniRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        viewModel = ViewModelProviders.of(getActivity()).get(PrenotazioniListViewModel.class);
        observersRegisters();
        return view;
    }

    private void observersRegisters() {

        final PrenotazioniPageListAdapter pageListAdapter = new PrenotazioniPageListAdapter(this);
        viewModel.getPrenotazioni().observe(this, new Observer<PagedList<Prenotazione>>() {
            @Override
            public void onChanged(PagedList<Prenotazione> pagedList) {
                pageListAdapter.submitList(pagedList);
            }
        });
        viewModel.getNetworkState().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                pageListAdapter.setNetworkState(networkState);
            }
        });
        recyclerView.setAdapter(pageListAdapter);
        detailsViewModel = ViewModelProviders.of(getActivity()).get(PrenotazioneDetailsViewModel.class);
    }

    @Override
    public void OnItemClick(Prenotazione prenotazione) {
        detailsViewModel.getPrenotazione().postValue(prenotazione);
        if (!detailsViewModel.getPrenotazione().hasActiveObservers()) {
            // Create fragment and give it an argument specifying the article it should show
            PrenotazioneDetailsFragment detailsFragment = new PrenotazioneDetailsFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragmentsContainer, detailsFragment);
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        }
    }
}
