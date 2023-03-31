package com.example.project0011;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class LifecycleActivity extends AppCompatActivity {

    private TextView vitals,BMI,comment;
    EditText height,weight,sleep;
    DecimalFormat decimalFormat=new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);

        getSupportActionBar().hide();



        vitals=findViewById(R.id.vitals);
        BMI=findViewById(R.id.BMI);
        comment=findViewById(R.id.comment);
        height=findViewById(R.id.hheight);
        weight=findViewById(R.id.wweight);
        sleep=findViewById(R.id.Sleephour);

        loadVAL();

        vitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SleepActivity.class);
                startActivity(intent);

            }
        });

        BMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculateBMI();
            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculateSleep();

            }
        });



    }

    private void loadVAL() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserDataAll");
        databaseReference.child("measurements").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String h,w;
                if(snapshot.exists())
                {
                    h=snapshot.child("height").getValue().toString();
                    w=snapshot.child("weight").getValue().toString();
                    height.setText(h);
                    weight.setText(w);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void CalculateSleep() {
        String w,c;
        //h=height.getText().toString();
        w=sleep.getText().toString();
        if(!w.isEmpty())
        {
            int wi= Integer.parseInt(w);
            //double hi=Double.parseDouble(h);
            //if(hi==0)return;
            //double bmi= wi/(hi*hi);
            if(wi<6)
            {
                c=" (Slept less today)";
            }
            HashMap hashMap= new HashMap<String,String>();
            hashMap.put("hour",w);
            String Cur_date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("UserDataAll");
            databaseReference.child("Sleep").child(FirebaseAuth.getInstance().getUid()).child(Cur_date).setValue(hashMap);



            DatabaseReference ref= FirebaseDatabase.getInstance().getReference("UserDataAll");
            ref.child("Sleep").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int y= (int)snapshot.getChildrenCount();

                    int z=0;
                    if(y==0){z=1;}
                    for (DataSnapshot ds : snapshot.getChildren())
                    {

                        if (y!=0)

                        { String c=ds.child("hour").getValue().toString();
                            z=z+Integer.parseInt(c);
                        }


                    }
                    z=z/y;


                    comment.setText("Average Sleep" + z+ " hours");



                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else
        {
            return;
        }
    }

    private void CalculateBMI() {
        String h,w,c;
        h=height.getText().toString();
        w=weight.getText().toString();
        if(!h.isEmpty()&&!w.isEmpty())
        {
            int wi= Integer.parseInt(w);
            double hi=Double.parseDouble(h);
            if(hi==0)return;
            double bmi= wi/(hi*hi);
            if(bmi<18.0)
            {
                c=" (Underweight)";
            }
            else if(bmi<25.0)
            {
                c="(Optimal)";
            }
            else if (bmi<35.0)
            {
                c="(overweight)";
            }
            else
            {
                c="Risk zone";
            }



            //String b=decimalFormat.format(Double.toString(bmi)) ;
            String b=String.valueOf(decimalFormat.format(bmi)) ;
            //decimalFormat.format(b);
            BMI.setText("BMI : "+b+" "+c);

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserDataAll");
            databaseReference.child("measurements").child(FirebaseAuth.getInstance().getUid()).child("height").setValue(h);
            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("UserDataAll");
            databaseReference1.child("measurements").child(FirebaseAuth.getInstance().getUid()).child("weight").setValue(w);


        }
        else
        {
            return;
        }
    }
}