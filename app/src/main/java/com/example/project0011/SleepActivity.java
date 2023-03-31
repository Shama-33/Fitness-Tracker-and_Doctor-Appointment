package com.example.project0011;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

public class SleepActivity extends AppCompatActivity {

    EditText higherbp,lowerbp,Pulserate;
    TextView bpcom,commentpulse,textView9,textView90,textView91,textView901;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);


        getSupportActionBar().hide();

        higherbp=findViewById(R.id.higherbp);
        lowerbp=findViewById(R.id.lowerbp);
        Pulserate=findViewById(R.id.Pulserate);
        bpcom=findViewById(R.id.bpcom);
        commentpulse=findViewById(R.id.commentpulse);
        textView9=findViewById(R.id.textView9);
        textView90=findViewById(R.id.textView90);
        textView91=findViewById(R.id.textView91);
        textView901=findViewById(R.id.textView901);

        textView9.setMovementMethod(LinkMovementMethod.getInstance());
        textView9.setLinkTextColor(Color.BLUE);

        textView90.setMovementMethod(LinkMovementMethod.getInstance());
        textView90.setLinkTextColor(Color.BLUE);

        textView91.setMovementMethod(LinkMovementMethod.getInstance());
        textView91.setLinkTextColor(Color.BLUE);

        textView901.setMovementMethod(LinkMovementMethod.getInstance());
        textView901.setLinkTextColor(Color.BLUE);



        bpcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculateBP();
            }
        });
        commentpulse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculatePulse();

            }
        });
    }

    private void CalculatePulse() {


        String w,c;
        //h=height.getText().toString();
        w=Pulserate.getText().toString();
        if(!w.isEmpty())
        {
            int wi= Integer.parseInt(w);
            //double hi=Double.parseDouble(h);
            //if(hi==0)return;
            //double bmi= wi/(hi*hi);

            HashMap hashMap= new HashMap<String,String>();
            hashMap.put("min",w);
            String Cur_date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("UserDataAll");
            databaseReference.child("PulseRate").child(FirebaseAuth.getInstance().getUid()).child(Cur_date).setValue(hashMap);



            DatabaseReference ref= FirebaseDatabase.getInstance().getReference("UserDataAll");
            ref.child("PulseRate").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int y= (int)snapshot.getChildrenCount();

                    int z=0;

                    for (DataSnapshot ds : snapshot.getChildren())
                    {

                        if (y!=0)

                        { String c=ds.child("min").getValue().toString();
                            z=z+Integer.parseInt(c);
                        }


                    }
                    if(y==0){y=1;}
                    z=z/y;


                    //commentpulse.setText("Average pulserate " + z+ "  perminute ");

                    if(z<70)
                    {
                        commentpulse.setText("Your pulse rate has been low for the past few days."+" ("+z+" permin)");
                    }
                    else if (z>80)
                    {
                        commentpulse.setText("Your pulse rate has been high for the past few days. "+" ("+z+" permin)");


                    }
                    else
                    {
                        commentpulse.setText("Your pulse rate is optimal."+" ("+z+" permin)");
                    }




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

    private void CalculateBP() {


        String h,w,c;
        h=lowerbp.getText().toString();
        w=higherbp.getText().toString();
        if(!h.isEmpty()&&!w.isEmpty())
        {
            int wi= Integer.parseInt(w);
            int hi=Integer.parseInt(h);
            if(wi==120&&hi==80)
            {
                c=" (Optimal)";
            }
            else if(hi<80)
            {
                c="(Low Blood Pressure)";
            }
            else if (wi>120)
            {
                c="(High Blood Pressure)";
            }
            else
            {
                c="Risk zone";
            }
            bpcom.setText(c);

        }
        else
        {
            return;
        }
    }

}