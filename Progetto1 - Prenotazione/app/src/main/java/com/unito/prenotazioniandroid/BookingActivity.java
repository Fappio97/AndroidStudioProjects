package com.unito.prenotazioniandroid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

import com.unito.prenotazioniandroid.connection.GetPrenotazioniDisponibili;
import com.unito.prenotazioniandroid.connection.response_parsers.ResponsePrenotazioniDisponibiliParser;
import com.unito.prenotazioniandroid.repository.FormSelectionRepository;
import com.unito.prenotazioniandroid.repository.ServerFormSelectionRepository;
import com.unito.prenotazioniandroid.tabelle.Corso;
import com.unito.prenotazioniandroid.tabelle.Docente;
import com.unito.prenotazioniandroid.tabelle.FormSelection;
import com.unito.prenotazioniandroid.tabelle.ServerFormSelections;
import com.unito.prenotazioniandroid.ui.view.LibereActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BookingActivity extends AppCompatActivity {
    private final Object lock = new Object();
    private static final Map<Integer, String> numberM = initHM();
    private final ObservableField<ServerFormSelections> serverFormSelectionObservable = ServerFormSelectionRepository.getInstance(null).getF_s_observable();
    private final FormSelectionRepository.FormSelectionObservable f_s_observable = FormSelectionRepository.getInstance(null).getF_s_observable();

    private static Map<Integer, String> initHM() {
        HashMap<Integer, String> hm = new HashMap<>();
        hm.put(15, "fifteen");
        hm.put(16, "sixteen");
        hm.put(17, "seventeen");
        hm.put(18, "eighteen");
        return hm;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart(){
        super.onStart();
        setContentView(R.layout.activity_booking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String loggedUser = getIntent().getStringExtra("loggedUser");
        TextView tv = (TextView) findViewById(R.id.bookingSelection);
        tv.setText(String.format(getResources().getString(R.string.booking_greeting),
                (loggedUser == null ? getResources().getString(R.string.word_user) : loggedUser)));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
        * ArrayList<String> a=new ArrayList<>();
        a.add("Mercury");
        a.add("Venus");
        a.add("Earth");
        a.add("Mars");
        a.add("Jupiter");
        a.add("Saturn");
        a.add("Uranus");
        a.add("Neptune");

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, R.layout.spinner_row);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.addAll(a);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        a.add("Bubolo");
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter2=new ArrayAdapter<>(this, R.layout.spinner_row);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.addAll(a);
        spinner2.setAdapter(adapter2);*/

        serverFormSelectionObservable.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                setF_S();
            }
        });

        f_s_observable.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                setServerFormSelections();
            }
        });

        setServerFormSelections();
    }

     private void setF_S() {
        synchronized (lock) {
            ServerFormSelections serverFormSelections = serverFormSelectionObservable.get();

            if (serverFormSelections != null) {
                List<String> nomeCognomeDocenti = new ArrayList<>();
                nomeCognomeDocenti.add("Tutti");
                for (Docente d : serverFormSelections.getProfessors()) {
                    nomeCognomeDocenti.add(d.getNome() + " " + d.getCognome());
                }

                List<String> titoloCorsi = new ArrayList<>();
                titoloCorsi.add("Tutti");
                for (Corso c : serverFormSelections.getCourses()) {
                    titoloCorsi.add(c.getTitolo());
                }

                final Spinner sp1 = (Spinner) findViewById(R.id.spinner);
                final Spinner sp2 = (Spinner) findViewById(R.id.spinner2);

                ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, nomeCognomeDocenti);
                adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp1.setAdapter(adp1);

                ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, titoloCorsi);
                adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp2.setAdapter(adp2);

                final List<String> ncDocenti = nomeCognomeDocenti;
                sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                        ServerFormSelections formSelection = serverFormSelectionObservable.get();
                        Integer idInt = null;
                        if (position > 0)
                            idInt = formSelection.getProfessors().get(position - 1).getId();
                        else //TUTTI
                            idInt = null;
                        f_s_observable.setSelectedProf(idInt);
                        Toast.makeText(getBaseContext(), ncDocenti.get(position), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        f_s_observable.setSelectedProf(null);
                    }
                });

                final List<String> tCorsi = titoloCorsi;
                sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                        ServerFormSelections formSelection = serverFormSelectionObservable.get();
                        // TODO Auto-generated method stub
                        String titoloForm = null;
                        if (position > 0)
                            titoloForm = formSelection.getCourses().get(position - 1).getTitolo();
                        else //TUTTI
                            titoloForm = null;
                        f_s_observable.setSelectedCourse(titoloForm);
                        Toast.makeText(getBaseContext(), tCorsi.get(position), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        f_s_observable.setSelectedCourse(null);
                    }
                });

                /*for (final int h : formSelection.getHours()) {
                    final Integer cb = getResource(numberM.get(h));//getResources().getIdentifier(numberM.get(h), CheckBox.class.getSimpleName(), getPackageName() +":layout/content_booking");
                    if (cb != null) {
                        CheckBox checkBox = (CheckBox) findViewById(cb);
                        checkBox.setClickable(true);
                        checkBox.setTextColor(Color.BLACK); //in xml andoid:color="disable_color" //TODO
                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                FormSelection f_s = f_s_observable.get();
                                if (isChecked)
                                    f_s.getCheckedHours().add(h);
                                else
                                    f_s.getCheckedHours().remove(f_s.getCheckedHours().indexOf(h));
                            }
                        });
                    } else
                        Log.d(this.getClass().getSimpleName(), "CheckBox on " + h + " is null");
                }*/

                for(final int hourInt : numberM.keySet()){
                    String hourString = numberM.get(hourInt);
                    Integer cb = getResource(hourString);
                    CheckBox checkBox = findViewById(cb);
                    if(serverFormSelectionObservable.get().getHours().contains(hourInt)){
                        checkBox.setClickable(true);
                        checkBox.setTextColor(Color.BLACK);
                    }
                    checkBox.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            FormSelection f_s = f_s_observable.get();
                            if (isChecked)
                                f_s_observable.add(hourInt);
                            else
                                f_s_observable.remove(hourInt);
                        }
                    });
                }



                /*for (final String d : formSelection.getDays()) {
                    final Integer cb = getResource(d);//getResources().getIdentifier(numberM.get(h), CheckBox.class.getSimpleName(), getPackageName() +":layout/content_booking");
                    if (cb != null) {
                        final CheckBox checkBox = (CheckBox) findViewById(cb);
                        checkBox.setClickable(true);
                        checkBox.setTextColor(Color.BLACK);
                        //checkBox.setTextColor(normal_color); //in xml andoid:color="disable_color" //TODO
                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                FormSelection f_s = f_s_observable.get();
                                if (isChecked)
                                    f_s.getCheckedDays().add(d);
                                else
                                    f_s.getCheckedDays().remove(d);
                            }
                        });
                    } else
                        Log.d(this.getClass().getSimpleName(), "CheckBox on " + d + " is null");
                }*/

                final String[] weekDays = {"Lun", "Mar", "Mer", "Gio", "Ven"};
                for(final String weekDay : weekDays){
                    Integer cb = getResource(weekDay);
                    CheckBox checkBox = findViewById(cb);
                    if(Arrays.asList(serverFormSelectionObservable.get().getDays()).contains(weekDay)){
                        checkBox.setClickable(true);
                        checkBox.setTextColor(Color.BLACK);
                    }
                    checkBox.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            FormSelection f_s = f_s_observable.get();
                            if (isChecked)
                                f_s_observable.add(weekDay);
                            else
                                f_s_observable.remove(weekDay);
                        }
                    });
                }
            }

            //f_s_observable.set(f_s_observable.get());
        }
    }

    private void setServerFormSelections() {
        synchronized (lock) {
            GetPrenotazioniDisponibili getPrenotazioniDisponibili = new GetPrenotazioniDisponibili(getBaseContext(), f_s_observable.get());
            getPrenotazioniDisponibili.execute();
            String s = "";
            try {
                s = getPrenotazioniDisponibili.get(2, TimeUnit.SECONDS);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }

            ResponsePrenotazioniDisponibiliParser parser = new ResponsePrenotazioniDisponibiliParser(s, ServerFormSelections.class);
            if (parser.hasSucceded()) {
                serverFormSelectionObservable.set((ServerFormSelections) parser.getResult());
            } else {
                //DO SOMETHING
            }
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return true;
    }

    private Integer getResource(String resName) {
        Integer layoutId = null;
        try {
            Class res = R.id.class;
            Field field = res.getField(resName);
            layoutId = field.getInt(null);
        } catch (Exception e) {
            Log.e("MyTag", "Failure to get id.", e);
        }
        return layoutId;
    }

    public void postSelection(View view) {
        //TODO controllare l'azione che viene eseguita quando si preme il pulsante, impostare il fragment di activity_booking_libere, etc
        //TODO fare tramite fragment
        Intent intent = new Intent(this, LibereActivity.class);
        this.startActivity(intent);
    }
}

