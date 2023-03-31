package com.example.project0011;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
//import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class IndexActivity extends AppCompatActivity implements SensorEventListener {

     BottomNavigationView bot_nav_view;
     //HospitalFragment hospitalFragment=new HospitalFragment();

    //private TextView stepcounter;
    private float x1,y1,x2,y2;
    private TextView stepdetector,dist,cal;
    private SensorManager sensorManager;
    //private Sensor mstepcounter;
    private Sensor mstepdetector;
    //int stepcount=0;
    int stepdetect;
    private ImageButton imagebutton,imageButtonforHistory,imageButtonforLifestyle,imagebuttonforActivity;
     SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    DatabaseReference chartref;
    int calories;
    float distance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);


        this.setTitle("");
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_200)));
        bot_nav_view=findViewById(R.id.bot_nav_view);

        if(stepdetect==5000)
        {
            Toast.makeText(this, "Congratulations! You have Completed 5000 steps.", Toast.LENGTH_SHORT).show();
        }

        bot_nav_view.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.homemenu:
                        return true;
                    case R.id.hospitalmenu:
                        //getSupportFragmentManager().beginTransaction().replace(androidx.fragment.R.id.fragment_container_view_tag,HospitalFragment).commit();
                        Intent intent = new Intent(getApplicationContext(),HoapitalActivity.class);
                        startActivity(intent);
                        return true;

                }
                return false;
            }
        });







        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){ //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }



        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
       // stepcounter=findViewById(R.id.stepcounter);
        stepdetector=findViewById(R.id.stepdetector);
        cal=findViewById(R.id.cal);
        dist=findViewById(R.id.dist);

        DecimalFormat decimalFormat=new DecimalFormat("#.##");


       String Cur_date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

//        SimpleDateFormat sdf= new SimpleDateFormat("dd:mm:yy");
//        long dates= new Date();

        //long sdf= new SimpleDateFormat("ddmmyy",Locale.getDefault()).format(new Date());

       sharedPreferences=this.getSharedPreferences("com.example.project0011", Context.MODE_PRIVATE);







        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);

//        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null)
//        {
//            mstepcounter=sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
//            isCounterSensorPresent=true;
//        }
//        else
//        {
//            Toast.makeText(this, "Counter Sensor Unavailable", Toast.LENGTH_SHORT).show();
//            isCounterSensorPresent=false;
//        }




        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)!=null)
        {
            mstepdetector=sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            sensorManager.registerListener(this,mstepdetector,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {
            Toast.makeText(this, "Detector Sensor Unavailable", Toast.LENGTH_SHORT).show();
        }




        imagebutton= findViewById(R.id.imageButton);
        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ContentActivity.class);
                startActivity(intent);
            }
        });

        imageButtonforHistory= findViewById(R.id.imageButtonforHistory);
        imageButtonforHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),HistoryActivity.class);
                startActivity(intent);
            }
        });
        imageButtonforLifestyle=findViewById(R.id.imageButtonforlifestyle);
        imageButtonforLifestyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),LifecycleActivity.class);
                startActivity(intent);
            }
        });

        imagebuttonforActivity=findViewById(R.id.imageButtonforActivity);
        imagebuttonforActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),TimerActivity.class);
                intent.putExtra("CALORIE",calories);
                startActivity(intent);

            }
        });



        if(!sharedPreferences.contains(Cur_date)) {



            sharedPreferences.edit().putInt(Cur_date, 0).apply();
            stepdetect = 0;
            calories= (int) (stepdetect * 0.04);
            distance=stepdetect* 0.0008F;
            cal.setText(String.valueOf(calories));
            dist.setText(String.valueOf(decimalFormat.format(distance)));



        }
        else
        {
            stepdetect=sharedPreferences.getInt(Cur_date,1);
            stepdetector.setText(String.valueOf(stepdetect));
            calories= (int) (stepdetect * 0.04);
            //distance 1 Step = 0.0008 Kilometre
            distance=stepdetect*.0008F;
            cal.setText(String.valueOf(calories));
            dist.setText(String.valueOf(decimalFormat.format(distance)));
        }





        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();

        Data data=new Data();
        graph_steps g = new graph_steps();



        if(user != null){
            String uid = user.getUid();
            databaseReference= FirebaseDatabase.getInstance().getReference().child("Data");
            data.setDistance(distance);
            data.setCalorie(calories);
            data.setSteps(stepdetect);
            data.setDate(Cur_date);
            databaseReference.child(uid).child(Cur_date).setValue(data);

            chartref=FirebaseDatabase.getInstance().getReference("GraphStep");
            g.setDate(Cur_date);
            g.setSteps(stepdetect);
            chartref.child(uid).child(Cur_date).setValue(g);
        }





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
            String Cur_date= new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault()).format(new Date());
            FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
            Data data=new Data();

            if(user != null){
                String uid = user.getUid();
                databaseReference= FirebaseDatabase.getInstance().getReference().child("Data");
                chartref=FirebaseDatabase.getInstance().getReference("GraphStep");
//                databaseReference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(IndexActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
                data.setDistance(distance);
                data.setCalorie(calories);
                data.setSteps(stepdetect);
                data.setDate(Cur_date);
                databaseReference.child(uid).child(Cur_date).setValue(data);



                 graph_steps g= new graph_steps();
                g.setDate(Cur_date);
                g.setSteps(stepdetect);
                chartref.child(uid).child(Cur_date).setValue(g);
            }


            SharedPreferences.Editor ed = sharedPreferences.edit();
            ed.remove(Cur_date);
            ed.apply();

            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }



        @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
            DecimalFormat decimalFormat=new DecimalFormat("#.##");


            if(mstepdetector == sensorEvent.sensor)
        {
            String Cur_date= new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault()).format(new Date());
            if(sharedPreferences.contains(Cur_date))
            {
                stepdetect=sharedPreferences.getInt(Cur_date,1);
                stepdetect= (int) (stepdetect+sensorEvent.values[0]);
                stepdetector.setText(String.valueOf(stepdetect));
                sharedPreferences.edit().putInt(Cur_date,stepdetect).apply();
                calories= (int) (stepdetect * 0.04);
                //distance 1 Step = 0.0008 Kilometre
                distance=stepdetect*.0008F;
                cal.setText(String.valueOf(calories));
                dist.setText(String.valueOf(decimalFormat.format(distance)));



            }
            else
            {
                sharedPreferences.edit().putInt(Cur_date,0).apply();
                stepdetect=0;
                stepdetector.setText(String.valueOf(stepdetect));
                calories= (int) (stepdetect * 0.04);
                //distance 1 Step = 0.0008 Kilometre
                distance=stepdetect*.0008F;
                cal.setText(String.valueOf(calories));
                dist.setText(String.valueOf(decimalFormat.format(distance)));
            }



            FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
            Data data=new Data();

            if(user != null){
                String uid = user.getUid();
                databaseReference= FirebaseDatabase.getInstance().getReference().child("Data");
                chartref=FirebaseDatabase.getInstance().getReference("GraphStep");
//                databaseReference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(IndexActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
                data.setDistance(distance);
                data.setCalorie(calories);
                data.setSteps(stepdetect);
                data.setDate(Cur_date);
                databaseReference.child(uid).child(Cur_date).setValue(data);





                graph_steps g= new graph_steps();
                g.setDate(Cur_date);
                g.setSteps(stepdetect);
                chartref.child(uid).child(Cur_date).setValue(g);
            }


        }

//        else if(mstepcounter == sensorEvent.sensor)
//        {
//            stepcount= (int) sensorEvent.values[0];
//            stepcounter.setText(String.valueOf(stepcount));
//        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1>x2){
                Intent i = new Intent(IndexActivity.this, HoapitalActivity.class);
                startActivity(i);
            }

//                else if(x1 >x2){
//                Intent i = new Intent(MainActivity.this, SwipeRight.class);
//                startActivity(i);
//            }
            break;
        }
        return false;
    }


}