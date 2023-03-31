package com.example.project0011;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    private EditText activity,timeEdit;
    private TextView Calc;
    String spec;
    private Button button3;
     int CAl=0;
     int cop=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        activity=findViewById(R.id.ActivityTypey);
        timeEdit=findViewById(R.id.time100);
        Calc=findViewById(R.id.CAlcu);
        button3=findViewById(R.id.button3);

        int X=getIntent().getIntExtra("CALORIE",100);
        CAl+=X;


        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectType();
            }
        });


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mins=timeEdit.getText().toString();

                int min=1;

                if(!mins.isEmpty())
                {min= Integer.parseInt(mins);}

                check(min);


            }
        });



    }

    private void check(int min) {

        if(spec.isEmpty())
        {
            return;
        }
        if(spec.equals("Swimming"))
        {
            CAl=CAl+min*5;

        }
        else if(spec.equals("Sprint"))
        {
            CAl=CAl+min*6;

        }
        else if(spec.equals("Marathon"))
        {
            CAl=CAl+min*3;

        }
        else if(spec.equals("Jogging"))
        {
            CAl=CAl+min*3;

        }
        else
        {
            CAl=CAl+min*4;
        }
        String s=Integer.toString(CAl);
        Calc.setText("Calorie burnt : "+s+" cal");

        String Cur_date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        TotalCalGraph calg= new TotalCalGraph();
        calg.setDate(Cur_date);



        DatabaseReference Ref= FirebaseDatabase.getInstance().getReference("UserDataAll");
        Ref.child("TotalCal").child(FirebaseAuth.getInstance().getUid()).child(Cur_date)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            String str=snapshot.child("cal").getValue().toString();
                            if(!str.isEmpty())
                            {
                                cop=Integer.parseInt(str);
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        CAl+=cop;


        calg.setCal(CAl);



        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("UserDataAll");
        databaseReference.child("TotalCal").child(FirebaseAuth.getInstance().getUid()).child(Cur_date).setValue(calg)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(TimerActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TimerActivity.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    private void SelectType() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Activity Type").setItems(ActivityType.AcType, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String C=ActivityType.AcType [which];
                activity.setText(C);
                spec=C;


            }
        }).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        String Cur_date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        DatabaseReference Ref= FirebaseDatabase.getInstance().getReference("UserDataAll");
        Ref.child("TotalCal").child(FirebaseAuth.getInstance().getUid()).child(Cur_date)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            String str=snapshot.child("cal").getValue().toString();
                            if(!str.isEmpty())
                            {
                                cop=Integer.parseInt(str);
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        CAl=cop;
    }
}