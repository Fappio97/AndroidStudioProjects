package com.example.guesswho;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.sanbot.opensdk.base.BindBaseActivity;
import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.unit.SpeechManager;


public class BeginActivity extends BindBaseActivity {
    private Context myContext = this;
    private SpeechManager speechManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);

        final Button start_button = findViewById(R.id.start_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input_questions_number = BeginActivity.this.findViewById(R.id.input_questions_number);
                String number_questions = input_questions_number.getText().toString();
                //speechManager.startSpeak("ciao");

                int myNumber = Integer.parseInt(number_questions);
                Intent intent = new Intent(myContext, MainActivity.class);
                intent.putExtra("number_questions", myNumber);

                startActivity(intent);
            }
        });
    }

   @Override
    protected void onMainServiceConnected() {
        speechManager = (SpeechManager) getUnitManager(FuncConstant.SPEECH_MANAGER);
    }
}
