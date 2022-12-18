package com.unito.prenotazioniandroid.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.squareup.picasso.Picasso;
import com.unito.prenotazioniandroid.R;
import com.unito.prenotazioniandroid.data.LoginRepository;
import com.unito.prenotazioniandroid.databinding.LiberaFragmentDetailsBinding;
import com.unito.prenotazioniandroid.repository.storge.model.Libera;
import com.unito.prenotazioniandroid.ui.login.LoginActivity;
import com.unito.prenotazioniandroid.ui.viewmodel.LiberaDetailsViewModel;

import static com.unito.prenotazioniandroid.Constants.BIG_IMAGE_URL_PREFIX;

public class LiberaDetailsFragment extends Fragment {

    private LiberaDetailsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate view and obtain an instance of the binding class.
        final LiberaFragmentDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.libera_fragment_details, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(LiberaDetailsViewModel.class);
        final View view = binding.getRoot();
        viewModel.getLibera().observe(this, new Observer<Libera>() {
            @Override
            public void onChanged(Libera libera) {
                binding.setLibera(libera);
            }
        });
        ((TextView) view.findViewById(R.id.stato)).setText("disponibile");
        if(!LoginRepository.getInstance(null).isLoggedIn()){
            Button prenota = view.findViewById(R.id.prenota);
            prenota.setText("Effettua il login per poter prenotare");
            prenota.setClickable(false);
            prenota.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(view.getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
        return view;
    }
}
