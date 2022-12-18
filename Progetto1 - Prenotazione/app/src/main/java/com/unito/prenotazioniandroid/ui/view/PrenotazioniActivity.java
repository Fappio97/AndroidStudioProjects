package com.unito.prenotazioniandroid.ui.view;
//package com.elad.themovie.ui.view;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.unito.prenotazioniandroid.Constants;
import com.unito.prenotazioniandroid.R;
import com.unito.prenotazioniandroid.connection.DoConfirm;
import com.unito.prenotazioniandroid.connection.DoDebook;
import com.unito.prenotazioniandroid.connection.ServerQuerier;
import com.unito.prenotazioniandroid.connection.response_parsers.HtmlMessageParser;
import com.unito.prenotazioniandroid.repository.PrenotazioniRepository;
import com.unito.prenotazioniandroid.repository.network.api.PrenotazioniAPIInterface;
import com.unito.prenotazioniandroid.repository.network.api.ThePrenotazioniDBAPIClient;
import com.unito.prenotazioniandroid.repository.storge.model.NetworkState;
import com.unito.prenotazioniandroid.repository.storge.model.Prenotazione;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;


public class PrenotazioniActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prenotazioni);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragmentsContainer) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            PrenotazioniListFragment prenotazioniListFragment = new PrenotazioniListFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            prenotazioniListFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragmentsContainer' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentsContainer, prenotazioniListFragment).commit();
        }

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_container);
        final PrenotazioniActivity outerThis = this;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final PrenotazioniRepository prenotazioniRepository = PrenotazioniRepository.getInstance(getApplicationContext());
                prenotazioniRepository.refresh();
                prenotazioniRepository.getNetworkState().observe(outerThis, new Observer<NetworkState>() {
                    @Override
                    public void onChanged(NetworkState networkState) {
                        NetworkState.Status status = prenotazioniRepository.getNetworkState().getValue().getStatus();
                        if(!status.equals(NetworkState.Status.RUNNING))
                            swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
        /*final ATask aTask = new ATask(getApplicationContext(), swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!aTask.getStatus().equals(AsyncTask.Status.PENDING))
                    aTask.execute();
                swipeRefreshLayout.setRefreshing(false);
            }
        });*/
        /*swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        PrenotazioniRepository.getInstance(getApplicationContext()).getPrenotazioni().getValue().getDataSource().invalidate();
                        PrenotazioniAPIInterface prenotazioniService = ThePrenotazioniDBAPIClient.getClient();
                        Call<ArrayList<Prenotazione>> callBack = prenotazioniService.getPrenotazioni(ACTION_KEY, 1);
                        ArrayList<Prenotazione> prenotazioni = null;
                        try {
                            prenotazioni = callBack.execute().body();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        for (Prenotazione dematerialized : prenotazioni) {
                            PagedList<Prenotazione> pagedList = PrenotazioniRepository.getInstance(null).getPrenotazioni().getValue();
                            if (!pagedList.contains(dematerialized))
                                prenotazioni.add(dematerialized);
                            else {
                                int index = pagedList.indexOf(dematerialized);
                                Prenotazione demOld = pagedList.get(index);
                                if (!dematerialized.equalsWithStatus(demOld)) {
                                    //pagedList.remove(index);
                                    //pagedList.add(0, dematerialized);
                                    demOld.setStato(dematerialized.getStato());
                                }
                            }
                        }
                        //PrenotazioniRepository.getInstance(getApplicationContext()).getPrenotazioni().getValue().getDataSource().invalidate();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                };
                thread.run();
            }
        });*/


    }

    /*//TODO tentativo aggiornamento 2
    static class ATask extends AsyncTask<Void, Void, ArrayList<Prenotazione>>{
        private SwipeRefreshLayout swipeRefreshLayout;
        private Context context;

        public ATask(Context context, SwipeRefreshLayout swipeRefreshLayout){
            this.swipeRefreshLayout = swipeRefreshLayout;
            this.context = context;
        }


        @Override
        protected ArrayList<Prenotazione> doInBackground(Void... calls) {
            PrenotazioniRepository.getInstance(context).getPrenotazioni().getValue().getDataSource().invalidate();
            PrenotazioniAPIInterface prenotazioniService = ThePrenotazioniDBAPIClient.getClient();
            Call<ArrayList<Prenotazione>> callBack = prenotazioniService.getPrenotazioni(Constants.ACTION_PRENOTAZIONI_KEY, 1);
            ArrayList<Prenotazione> prenotazioni = null;
            try {
                return callBack.execute().body();
            } catch (IOException e) {
                return new ArrayList<>();
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Prenotazione> prenotazioni) {
            for (Prenotazione dematerialized : prenotazioni) {
                PagedList<Prenotazione> pagedList = PrenotazioniRepository.getInstance(null).getPrenotazioni().getValue();
                if (!pagedList.contains(dematerialized))
                    prenotazioni.add(dematerialized);
                else {
                    int index = pagedList.indexOf(dematerialized);
                    Prenotazione demOld = pagedList.get(index);
                    if (!dematerialized.equalsWithStatus(demOld)) {
                        //pagedList.remove(index);
                        //pagedList.add(0, dematerialized);
                        demOld.setStato(dematerialized.getStato());
                    }
                }
            }
            //PrenotazioniRepository.getInstance(getApplicationContext()).getPrenotazioni().getValue().getDataSource().invalidate();
        }
    };*/

    public void debook(View view) {
        Log.d(this.getClass().getSimpleName(), view.toString());
        Object tag = view.getTag();
        printDebug(tag);
        if (tag instanceof Prenotazione) {
            Prenotazione prenotazione = (Prenotazione) tag;
            DoDebook doDebook = new DoDebook(getBaseContext(), prenotazione);
            doDebook.execute();
            setChanged(view, prenotazione, doDebook, "cancellata");
        }
    }

    public void confirm(View view) {
        Object tag = view.getTag();
        printDebug(tag);
        if (tag instanceof Prenotazione) {
            Prenotazione prenotazione = (Prenotazione) tag;
            DoConfirm doConfirm = new DoConfirm(getBaseContext(), prenotazione);
            doConfirm.execute();
            setChanged(view, prenotazione, doConfirm, "effettuata");
        }
    }

    private void setChanged(View view, Prenotazione prenotazione, ServerQuerier doConfirm, String changedStatus) {
        HtmlMessageParser responseParser = new HtmlMessageParser(doConfirm, String.class);
        if (responseParser.hasSucceded()) {
            prenotazione.setStato(changedStatus);
            setView(view, changedStatus);
        }
    }

    private void setView(View view, String changedStatus) {
        View rootView = view.getRootView();
        rootView.findViewById(R.id.disdici).setVisibility(View.GONE);
        rootView.findViewById(R.id.conferma).setVisibility(View.GONE);
        TextView stato = rootView.findViewById(R.id.stato);
        stato.setText(changedStatus);
    }

    private void printDebug(Object tag) {
        if (tag != null) {
            Class aClass = tag.getClass();
            boolean isInstanceOf = aClass.equals(Prenotazione.class);
            String tagValue = "empty";
            if (isInstanceOf)
                tagValue = ((Prenotazione) tag).toString();
            Log.d(this.getClass().getSimpleName(), "Tag Class is " + aClass.getSimpleName() + " value " + tagValue);
        } else {
            Log.d(this.getClass().getSimpleName(), "Tag is null");
        }
    }
}