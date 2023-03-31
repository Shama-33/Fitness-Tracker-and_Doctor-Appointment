package com.example.project0011;

import static com.example.project0011.R.id.JumpCount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ContentActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    private Button button;

    private TextView JumpCount,jumpcal;
    private double previous_magnitude=0;
    private Integer jumpcount=0;

    //@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        mAuth=FirebaseAuth.getInstance();

        this.setTitle("");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_200)));
        JumpCount= (TextView) findViewById(R.id.JumpCount);
        jumpcal= (TextView) findViewById(R.id.jumpcal);
        SensorManager sensormanager= (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor=sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener JUMPCOUNT = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                //gets acceleration in x,y,z axes. we are trying to extract it.
                if(sensorEvent!=null)    //try catch alternative
                {
                    float x_acceleration=sensorEvent.values[0]; //values in array form
                    float y_acceleration=sensorEvent.values[1];
                    float z_acceleration=sensorEvent.values[2];


                    double magnitude = Math.sqrt(x_acceleration*x_acceleration+y_acceleration*y_acceleration+z_acceleration*z_acceleration);
                    double delmag=magnitude-previous_magnitude;
                    previous_magnitude=magnitude;

                    if(delmag>7)
                    {
                        jumpcount++;
                    }
                    JumpCount.setText(jumpcount.toString());
                    int cal;
                    cal= (int) (jumpcount*0.1);
                    jumpcal.setText(String.valueOf(cal));


                }



            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        //specifies which listener its trying to listen to
        sensormanager.registerListener(JUMPCOUNT,sensor,SensorManager.SENSOR_DELAY_NORMAL);

        button=(Button) findViewById(R.id.reset);

        button.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ContentActivity.this, jumpcount.toString()+ " jumps Completed", Toast.LENGTH_SHORT).show();
                jumpcount=0;
            }
        }));



    }
    //to store step count
    //to not lose data while onPause
    //stores fewer amount of data compared to database.otherwise it'll be bulky
    protected void onPause() {

        super.onPause();
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();//clears previous data
        editor.putInt("jumpcount", jumpcount); //key string, int var
        editor.apply();
    }
    protected void onStop() {

        super.onStop();
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();//clears previous data
        editor.putInt("jumpcount", jumpcount); //key string, int var
        editor.apply();
    }
    protected void onResume() {

        super.onResume();
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        //read data
        jumpcount=sharedPreferences.getInt("jumpcount",0); // key of putint, a default value
    }


    //for sign out
    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.content_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.SignOutMenuId)
        {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}