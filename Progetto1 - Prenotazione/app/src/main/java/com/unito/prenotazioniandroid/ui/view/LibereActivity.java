package com.unito.prenotazioniandroid.ui.view;
//package com.elad.themovie.ui.view;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.unito.prenotazioniandroid.R;
import com.unito.prenotazioniandroid.connection.DoBook;
import com.unito.prenotazioniandroid.connection.ServerQuerier;
import com.unito.prenotazioniandroid.connection.response_parsers.HtmlMessageParser;
import com.unito.prenotazioniandroid.repository.storge.model.Libera;

public class LibereActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libere);

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
            LibereListFragment moviesListFragment = new LibereListFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            moviesListFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragmentsContainer' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentsContainer, moviesListFragment).commit();
        }
    }

    public void debook(View view) {
        /*Log.d(this.getClass().getSimpleName(), view.toString());
        Object tag = view.getTag();
        printDebug(tag);
        if(tag instanceof Libera) {
            Libera libera = (Libera) tag;
            DoDebook doDebook = new DoDebook(libera);
            doDebook.execute();
            setChanged(libera, doDebook, "cancellata");
        }*/
    }

    public void confirm(View view) {
        /*Object tag = view.getTag();
        printDebug(tag);
        if(tag instanceof Libera) {
            Libera libera = (Libera) tag;
            DoConfirm doConfirm = new DoConfirm(libera);
            doConfirm.execute();
            setChanged(libera, doConfirm, "effettuata");
        }*/
    }

    public void book(View view) {
        Object tag = view.getTag();
        printDebug(tag);
        if (tag instanceof Libera) {
            Libera libera = (Libera) tag;
            DoBook doConfirm = new DoBook(getBaseContext(), libera);
            doConfirm.execute();
            setChanged(view, libera, doConfirm, "prenotata");
        }
    }

    private void setChanged(View view, Libera libera, ServerQuerier doConfirm, String changedStatus) {
        HtmlMessageParser responseParser = new HtmlMessageParser(doConfirm, String.class); //TODO verificare il funzionamento
        if (responseParser.hasSucceded()) {
            setView(view, changedStatus);
            //libera.setStato(changedStatus);
            //todo change data
        }
    }

    private void setView(View view, String changedStatus) {
        View rootView = view.getRootView();
        rootView.findViewById(R.id.prenota).setVisibility(View.GONE);
        TextView stato = rootView.findViewById(R.id.stato);
        stato.setText(changedStatus);
    }

    private void printDebug(Object tag) {
        if (tag != null) {
            Class aClass = tag.getClass();
            boolean isInstanceOf = aClass.equals(Libera.class);
            String tagValue = "empty";
            if (isInstanceOf)
                tagValue = ((Libera) tag).toString();
            Log.d(this.getClass().getSimpleName(), "Tag Class is " + aClass.getSimpleName() + " value " + tagValue);
        } else {
            Log.d(this.getClass().getSimpleName(), "Tag is null");
        }
    }
}