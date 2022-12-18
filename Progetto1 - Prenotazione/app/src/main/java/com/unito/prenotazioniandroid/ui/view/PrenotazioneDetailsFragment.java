package com.unito.prenotazioniandroid.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.squareup.picasso.Picasso;
import com.unito.prenotazioniandroid.R;
import com.unito.prenotazioniandroid.databinding.PrenotazioneFragmentDetailsBinding;
import com.unito.prenotazioniandroid.repository.storge.model.Prenotazione;
import com.unito.prenotazioniandroid.ui.viewmodel.PrenotazioneDetailsViewModel;

import static com.unito.prenotazioniandroid.Constants.BIG_IMAGE_URL_PREFIX;

public class PrenotazioneDetailsFragment extends Fragment {

    private PrenotazioneDetailsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate view and obtain an instance of the binding class.
        final PrenotazioneFragmentDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.prenotazione_fragment_details, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(PrenotazioneDetailsViewModel.class);
        final View view = binding.getRoot();
        viewModel.getPrenotazione().observe(this, new Observer<Prenotazione>() {
            @Override
            public void onChanged(final Prenotazione prenotazione) {
                binding.setPrenotazione(prenotazione);
                setButtons(view, prenotazione);
            }
        });
        return view;
    }

    private void setButtons(View view, Prenotazione prenotazione) {
        if(!prenotazione.getStato().equalsIgnoreCase("attiva")){
            view.findViewById(R.id.conferma).setVisibility(View.GONE);
            view.findViewById(R.id.disdici).setVisibility(View.GONE);
        }
    }
}
