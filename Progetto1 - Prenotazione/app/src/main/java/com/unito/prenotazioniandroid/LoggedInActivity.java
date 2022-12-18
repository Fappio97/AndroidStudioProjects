package com.unito.prenotazioniandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.unito.prenotazioniandroid.data.LoginRepository;
import com.unito.prenotazioniandroid.ui.view.PrenotazioniActivity;

public class LoggedInActivity extends AppCompatActivity {

    String loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        Toolbar toolbar = findViewById(R.id.toolbar);
        loggedUser = getIntent().getExtras().getString("utenteLoggato");
        setSupportActionBar(toolbar);

        /*int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TableLayout Rl = (TableLayout) findViewById(R.id.table);

        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logged, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            /*Intent intent = new Intent(this, MainActivity.class);//Non so se è proprio il modo giusto
            startActivity(intent);*/
            //new DoLogout(this).execute();
            LoginRepository.getInstance(null).logout();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout(View view){
        //new DoLogout(this).execute();
        LoginRepository.getInstance(null).logout();
        this.finish();
    }

    public void bookingPage(View view) {
        Intent intent = new Intent(this, BookingActivity.class);
        intent.putExtra("loggedUser", loggedUser);
        startActivity(intent);
    }

    public void personalBookingPage(View view) {
        Intent intent = new Intent(this, PrenotazioniActivity.class);
        //intent.putExtra("loggedUser", loggedUser);
        startActivity(intent);
    }
}
