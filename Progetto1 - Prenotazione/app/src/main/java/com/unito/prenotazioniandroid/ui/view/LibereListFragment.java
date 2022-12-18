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
import com.unito.prenotazioniandroid.repository.storge.model.Libera;
import com.unito.prenotazioniandroid.repository.storge.model.NetworkState;
import com.unito.prenotazioniandroid.ui.adapter.LiberePageListAdapter;
import com.unito.prenotazioniandroid.ui.listeners.LibereItemClickListener;
import com.unito.prenotazioniandroid.ui.viewmodel.LiberaDetailsViewModel;
import com.unito.prenotazioniandroid.ui.viewmodel.LibereListViewModel;

public class LibereListFragment extends Fragment implements LibereItemClickListener {
    protected LibereListViewModel viewModel;
    private LiberaDetailsViewModel detailsViewModel;

    protected RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prenotazioni, container, false);
        recyclerView = view.findViewById(R.id.prenotazioniRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        viewModel = ViewModelProviders.of(getActivity()).get(LibereListViewModel.class);
        observersRegisters();
        return view;
    }

    private void observersRegisters() {

        final LiberePageListAdapter pageListAdapter = new LiberePageListAdapter(this);
        viewModel.getLibere().observe(this, new Observer<PagedList<Libera>>() {
            @Override
            public void onChanged(PagedList<Libera> pagedList) {
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
        detailsViewModel = ViewModelProviders.of(getActivity()).get(LiberaDetailsViewModel.class);
    }

    @Override
    public void OnItemClick(Libera movie) {
        detailsViewModel.getLibera().postValue(movie);
        if (!detailsViewModel.getLibera().hasActiveObservers()) {
            // Create fragment and give it an argument specifying the article it should show
            LiberaDetailsFragment detailsFragment = new LiberaDetailsFragment();
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
