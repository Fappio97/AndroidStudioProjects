package com.example.guesswho;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.sanbot.opensdk.base.BindBaseActivity;
import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.unit.SpeechManager;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;




public class MainActivity extends BindBaseActivity {
    private Context myContext = this;
    ArrayList<Characters> vipsList = new ArrayList<>();
    List<String> yourArray = new ArrayList<>();
    List<String> deleteArray = new ArrayList<>();
    List<String> newData = new ArrayList<>();
    JSONArray data = new JSONArray();
    String keyFeature = "";
    int number_questions;
    private SpeechManager speechManager;
    String giniKey = "";
    Float maxGini = (float) 0.0;
    Gini gini = new Gini();
    Questions q = new Questions();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Resources getResources = this.getResources();
        final String getPackageName = this.getPackageName();
        final TextView questions_input = findViewById(R.id.questions_input);
        final GridView gvCharacters = findViewById(R.id.gvCharacters);
        Button start_button = findViewById(R.id.start_button);
        Button yes_button = findViewById(R.id.yes_button);
        Button no_button = findViewById(R.id.no_button);
        Button unknow_button = findViewById(R.id.unknow_button);

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll()
                    .build();
            StrictMode.setThreadPolicy(policy);

            APIService service = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
            Call<ResponseBody> callSync = service.getPeople();
            Response<ResponseBody> response = callSync.execute();
            String apiResponse = null;
            if (response.body() != null) {
                apiResponse = response.body().string();
            }
            JSONObject obj = new JSONObject(apiResponse);
            data = obj.getJSONArray("people");
            int i;
            for (i = 0; i < data.length(); i += 1) {
                JSONObject jsonObject1 = data.getJSONObject(i);
                yourArray.add(jsonObject1.optString("name"));
            }

            for (String e : yourArray) {
                Characters character = new Characters(e, getResources.getIdentifier(e, "drawable", getPackageName));
                vipsList.add(character);
            }

            VipAdapter adapter = new VipAdapter(myContext, vipsList);
            gvCharacters.setAdapter(adapter);

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());

        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            number_questions = extras.getInt("number_questions", 0);
        }

        //speechManager.startSpeak("Choose a character and press START");
        questions_input.setText("Choose a character and press START");

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll()
                            .build();
                    StrictMode.setThreadPolicy(policy);

                    APIService service = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
                    Call<ResponseBody> callSync = service.getPeople();
                    Response<ResponseBody> response = callSync.execute();
                    String apiResponse = null;
                    if (response.body() != null) {
                        apiResponse = response.body().string();
                    }
                    JSONObject obj = new JSONObject(apiResponse);
                    data = obj.getJSONArray("people");
                    int data_length = data.length();
                    for (int i = 0; i < data.length(); i += 1) {
                        JSONObject jsonObject1 = data.getJSONObject(i);
                        newData.add(jsonObject1.optString("name"));
                    }
                    HashMap<String, Float> results = gini.characters(data);
                    HashMap<String, Float> entropia_of_each_characteristics = gini.compute_gini(results, data_length);
                    giniKey = gini.maximun_of_gini(entropia_of_each_characteristics);
                    maxGini = entropia_of_each_characteristics.get(giniKey);

                    if (number_questions > 0) {
                        //speechManager.startSpeak("I'm thinking a question");
                        questions_input.setText("Thinking a question...");

                        new Timer().schedule(
                                new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new TimerTask() {
                                            @Override
                                            public void run() {
                                                String prova = q.do_question(giniKey);
                                                //speechManager.startSpeak(question);
                                                questions_input.setText(prova);
                                                Questions.remove_question(giniKey);
                                                number_questions -= 1;
                                            }
                                        });
                                    }
                                }, 7000);
                    } else {
                        Intent intent = new Intent(myContext, LastActivity.class);
                        startActivity(intent);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        yes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int i;
                    for (i = 0; i < data.length(); i += 1) {
                        Iterator<String> keys = data.getJSONObject(i).keys();
                        while (keys.hasNext()) {
                            String currentKey = keys.next();
                            if (data.getJSONObject(i).optString(currentKey).contains(giniKey)) {
                                keyFeature = currentKey;
                            }
                        }
                    }

                    APIService service = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
                    Call<ResponseBody> callSync = service.deleteUsers("yes", keyFeature, giniKey, newData);
                    Response<ResponseBody> response = callSync.execute();
                    String apiResponse = null;
                    if (response.body() != null) {
                        apiResponse = response.body().string();
                    }
                    JSONObject obj = new JSONObject(apiResponse);
                    data = obj.getJSONArray("people");
                    newData.clear();
                    for (int j = 0; j < data.length(); j += 1) {
                        JSONObject jsonObject1 = data.getJSONObject(j);
                        newData.add(jsonObject1.optString("name"));
                    }

                    if (newData.size() == 1) {
                        Intent intent = new Intent(myContext, LastActivity.class);
                        startActivity(intent);
                    }

                    int filtered_data_length = data.length();
                    HashMap<String, Float> results = gini.characters(data);
                    HashMap<String, Float> entropia_of_each_characteristics = gini.compute_gini(results, filtered_data_length);
                    giniKey = gini.maximun_of_gini(entropia_of_each_characteristics);
                    maxGini = entropia_of_each_characteristics.get(giniKey);

                    vipsList.clear();
                    deleteArray.clear();
                    for (i = 0; i < data.length(); i += 1) {
                        JSONObject jsonObject1 = data.getJSONObject(i);
                        deleteArray.add(jsonObject1.optString("name"));
                    }
                    for (String e : deleteArray) {
                        vipsList.add(new Characters(e, getResources.getIdentifier(e, "drawable", getPackageName)));
                    }

                    VipAdapter adapter = new VipAdapter(myContext, vipsList);
                    gvCharacters.setAdapter(adapter);


                    if (number_questions > 0) {
                        if (data.length() == 1) {
                            Intent intent = new Intent(myContext, LastActivity.class);
                            startActivity(intent);
                            onStop();
                        }
                        //speechManager.startSpeak("I'm thinking an another question");
                        questions_input.setText("Thinking another question...");

                        new Timer().schedule(
                                new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new TimerTask() {
                                            @Override
                                            public void run() {
                                                String question = q.do_question(giniKey);
                                                questions_input.setText(question);
                                                Questions.remove_question(giniKey);
                                                number_questions -= 1;
                                            }
                                        });
                                    }
                                }, 7000);
                    } else {
                        Intent intent = new Intent(myContext, LastActivity.class);
                        MainActivity.this.startActivity(intent);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        no_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int i;
                    for (i = 0; i < data.length(); i += 1) {
                        Iterator<String> keys = data.getJSONObject(i).keys();
                        while (keys.hasNext()) {
                            String currentKey = keys.next();
                            if (data.getJSONObject(i).optString(currentKey).contains(giniKey)) {
                                keyFeature = currentKey;
                            }
                        }
                    }

                    APIService service = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
                    Call<ResponseBody> callSync = service.deleteUsers("no", keyFeature, giniKey, newData);
                    Response<ResponseBody> response = callSync.execute();
                    String apiResponse = null;
                    if (response.body() != null) {
                        apiResponse = response.body().string();
                    }
                    JSONObject obj = new JSONObject(apiResponse);
                    data = obj.getJSONArray("people");
                    newData.clear();
                    for (int z = 0; z < data.length(); z += 1) {
                        JSONObject jsonObject1 = data.getJSONObject(z);
                        newData.add(jsonObject1.optString("name"));
                    }

                    if (newData.size() == 1) {
                        Intent intent = new Intent(myContext, LastActivity.class);
                        MainActivity.this.startActivity(intent);
                    }

                    int filtered_data_length = data.length();
                    HashMap<String, Float> results = gini.characters(data);
                    HashMap<String, Float> entropia_of_each_characteristics = gini.compute_gini(results, filtered_data_length);
                    giniKey = gini.maximun_of_gini(entropia_of_each_characteristics);
                    maxGini = entropia_of_each_characteristics.get(giniKey);

                    vipsList.clear();
                    deleteArray.clear();
                    for (i = 0; i < data.length(); i += 1) {
                        JSONObject jsonObject1 = data.getJSONObject(i);
                        deleteArray.add(jsonObject1.optString("name"));
                    }
                    for (String e : deleteArray) {
                        vipsList.add(new Characters(e, getResources.getIdentifier(e, "drawable", getPackageName)));
                    }

                    VipAdapter adapter = new VipAdapter(myContext, vipsList);
                    gvCharacters.setAdapter(adapter);


                    if (number_questions > 0) {
                        if (data.length() == 1) {
                            Intent intent = new Intent(myContext, LastActivity.class);
                            startActivity(intent);
                        }

                        //speechManager.startSpeak("I'm thinking an another question");
                        questions_input.setText("Thinking another question...");

                        new Timer().schedule(
                                new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new TimerTask() {
                                            @Override
                                            public void run() {
                                                String question = q.do_question(giniKey);
                                                questions_input.setText(question);
                                                Questions.remove_question(giniKey);
                                                number_questions -= 1;
                                            }
                                        });
                                    }
                                }, 7000);
                    } else {
                        Intent intent = new Intent(myContext, LastActivity.class);
                        startActivity(intent);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        unknow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int i;
                    for (i = 0; i < data.length(); i += 1) {
                        Iterator<String> keys = data.getJSONObject(i).keys();
                        while (keys.hasNext()) {
                            String currentKey = keys.next();
                            if (data.getJSONObject(i).optString(currentKey).contains(giniKey)) {
                                keyFeature = currentKey;
                            }
                        }
                    }

                    APIService service = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
                    Call<ResponseBody> callSync = service.deleteUsers("unknow", keyFeature, giniKey, newData);
                    Response<ResponseBody> response = callSync.execute();
                    String apiResponse = null;
                    if (response.body() != null) {
                        apiResponse = response.body().string();
                    }
                    JSONObject obj = new JSONObject(apiResponse);
                    data = obj.getJSONArray("people");
                    newData.clear();
                    for (int a = 0; a < data.length(); a += 1) {
                        JSONObject jsonObject1 = data.getJSONObject(a);
                        newData.add(jsonObject1.optString("name"));
                    }

                    if (newData.size() == 1) {
                        Intent intent = new Intent(myContext, LastActivity.class);
                        MainActivity.this.startActivity(intent);
                    }

                    int filtered_data_length = data.length();
                    HashMap<String, Float> results = gini.characters(data);
                    HashMap<String, Float> entropia_of_each_characteristics = gini.compute_gini(results, filtered_data_length);
                    giniKey = gini.maximun_of_gini(entropia_of_each_characteristics);
                    maxGini = entropia_of_each_characteristics.get(giniKey);

                    vipsList.clear();
                    deleteArray.clear();
                    for (i = 0; i < data.length(); i += 1) {
                        JSONObject jsonObject1 = data.getJSONObject(i);
                        deleteArray.add(jsonObject1.optString("name"));
                    }
                    for (String e : deleteArray) {
                        vipsList.add(new Characters(e, getResources.getIdentifier(e, "drawable", getPackageName)));
                    }

                    VipAdapter adapter = new VipAdapter(myContext, vipsList);
                    gvCharacters.setAdapter(adapter);

                    if (number_questions > 0) {
                        if (data.length() == 1) {
                            Intent intent = new Intent(myContext, LastActivity.class);
                            startActivity(intent);
                        }
                        //speechManager.startSpeak("I'm thinking an another question");
                        questions_input.setText("Thinking another question...");
                        new Timer().schedule(
                                new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new TimerTask() {
                                            @Override
                                            public void run() {
                                                String question = q.do_question(giniKey);
                                                questions_input.setText(question);
                                                //speechManager.startSpeak(question);
                                                Questions.remove_question(giniKey);
                                                number_questions -= 1;
                                            }
                                        });
                                    }
                                }, 7000);
                    } else {
                        Intent intent = new Intent(myContext, LastActivity.class);
                        startActivity(intent);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

    }

    @Override
    protected void onMainServiceConnected() {
        speechManager = (SpeechManager) getUnitManager(FuncConstant.SPEECH_MANAGER);
    }

}

class VipAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Characters> vipsList;

    VipAdapter(Context myContext, ArrayList<Characters> vipsList) {
        this.context = myContext;
        this.vipsList = vipsList;
    }

    @Override
    public int getCount() {
        return vipsList.size();
    }

    @Override
    public Object getItem(int position) {
        return vipsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View vipView, ViewGroup parent) {
        vipView = LayoutInflater.from(context).inflate(R.layout.characters_entry, parent, false);
        Characters currentItem = (Characters) getItem(position);
        ImageView imgCharacter = vipView.findViewById(R.id.imgCharacter);
        TextView nameCharacter = vipView.findViewById(R.id.nameCharacter);
        imgCharacter.setImageResource(currentItem.image);
        nameCharacter.setText(currentItem.name);

        return vipView;
    }
}
