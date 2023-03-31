package com.example.project0011;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HistoryActivity extends AppCompatActivity {

    private Button buttonlist,buttongrapdsteps,buttongraphcalorie,buttongraphpulserate,buttongraphsleeptime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getSupportActionBar().hide();


        buttonlist=findViewById(R.id.buttonlist);
        buttongrapdsteps=findViewById(R.id.buttongraphsteps);
        buttongraphcalorie=findViewById(R.id.buttongraphscalories);
        buttongraphpulserate=findViewById(R.id.buttongraphpulserate);
        buttongraphsleeptime=findViewById(R.id.buttongraphsleeptime);

        buttonlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ListActivity.class);
                startActivity(intent);
            }
        });

        buttongrapdsteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GraphStepActivity.class);
                startActivity(intent);

            }
        });

        buttongraphcalorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GraphCalorieActivity.class);
                startActivity(intent);
            }
        });
        buttongraphpulserate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GraphPulseRateActivity.class);
                startActivity(intent);

            }
        });
        buttongraphsleeptime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),GraphSleepActivity.class);
                startActivity(intent);



            }
        });
    }
}