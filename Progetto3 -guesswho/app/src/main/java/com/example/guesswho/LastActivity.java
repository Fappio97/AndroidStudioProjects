package com.example.guesswho;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.sanbot.opensdk.base.BindBaseActivity;
import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.unit.SpeechManager;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.example.guesswho.Questions.clear_questions;

public class LastActivity extends BindBaseActivity {

    private SpeechManager speechManager;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);

        Resources getResources = this.getResources();
        String getPackageName = this.getPackageName();
        TextView final_answer = findViewById(R.id.answer);
        ImageView imageView = findViewById(R.id.imageView);

        clear_questions();

        try {

            APIService service = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
            Call<ResponseBody> callSync = service.finalPerson();
            Response<ResponseBody> response = callSync.execute();
            String apiResponse = null;
            if (response.body() != null) {
                apiResponse = response.body().string();
            }
            JSONObject obj = new JSONObject(apiResponse);
            JSONArray data = obj.getJSONArray("people");

            if (data.length() == 1) {

                //speechManager.startSpeak("The character is: " + data.getJSONObject(0).optString("name"));
                final_answer.setText("The character is: " + data.getJSONObject(0).optString("name"));
                String name = data.getJSONObject(0).optString("name");
                int imgResId = getResources.getIdentifier(name, "drawable", getPackageName);
                imageView.setImageResource(imgResId);

            } else {

                //speechManager.startSpeak("I'm sorry, I didn't guess the character");
                final_answer.setText("I'm sorry, I didn't guess the character");
            }


        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());

        }
    }

    @Override
    protected void onMainServiceConnected() {
        speechManager = (SpeechManager) getUnitManager(FuncConstant.SPEECH_MANAGER);
    }
}
