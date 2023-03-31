package com.example.project0011;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProviderActivity extends AppCompatActivity {

    private ImageView doctor;
    private Button button,button2;
    private EditText Hospital_name,hospitalarea,hospitalcity;
    String S;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        this.setTitle("Provider Interface");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.acbar)));

        doctor=findViewById(R.id.Doctorimage);
        //pro=findViewById(R.id.pro);
        Hospital_name=findViewById(R.id.hospitalname);
        //button=findViewById(R.id.button);
        button=findViewById(R.id.button);
        button2=findViewById(R.id.button2);
        hospitalarea=findViewById(R.id.hospitalarea);
        hospitalcity=findViewById(R.id.hospitalcity);






        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String uid= user.getUid();
        DatabaseReference reff= FirebaseDatabase.getInstance().getReference().child("HospitalName").child(uid);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    Hospital_Names hospital_names=new Hospital_Names();
                    hospital_names.setHosname(snapshot.child("hosname").getValue().toString());
                    String h=hospital_names.getHosname();
                    Hospital_name.setText(h);
                }
                else
                {
                    Hospital_name.setHint("Hospital Name not Entered!!!");
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



//        FirebaseUser userr= FirebaseAuth.getInstance().getCurrentUser();
//        String uidd= userr.getUid();
        DatabaseReference refff= FirebaseDatabase.getInstance().getReference().child("HospitalLocation").child(uid);
        refff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
//                    Hospital_Names hospital_names=new Hospital_Names();
//                    hospital_names.setHosname(snapshot.child("hosname").getValue().toString());
//                    String h=hospital_names.getHosname();
//                    Hospital_name.setText(h);
                    HospitalLocation loc= new HospitalLocation();

                    loc.setCity(snapshot.child("city").getValue().toString());
                    loc.setArea(snapshot.child("area").getValue().toString());
                    String c,a;
                    c=loc.getArea();
                    a=loc.getCity();
                    hospitalcity.setText(a);
                    hospitalarea.setText(c);

                }
                else
                {
                    //Hospital_name.setHint("Hospital Name not Entered!!!");
                    Toast.makeText(ProviderActivity.this, "Enter Location", Toast.LENGTH_SHORT).show();
                    hospitalarea.setError("Enter Area");
                    hospitalcity.setError("Enter City");
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
//        Hospital_Names hospname= new Hospital_Names();
//        String uid= user.getUid();
//        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("HospitalName").child(uid);
//
//            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    hospname.setHosname(snapshot.child("hosname").getValue().toString());
//                     S=hospname.getHosname();
//                    Hospital_name.setText(S);
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });








        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddDoctorActivity.class);
                startActivity(intent);

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetHospitalNameOnce();
            }

        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetHospitalLocation();

            }
        });

    }

    private void SetHospitalLocation() {
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        //Hospital_Names hospname= new Hospital_Names();
        HospitalLocation loc= new HospitalLocation();
        String uid= user.getUid();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("HospitalLocation").child(uid);

        String S=hospitalarea.getText().toString().trim();
        String S1=hospitalcity.getText().toString().trim();
        loc.setArea(S);
        loc.setCity(S1);
        databaseReference.setValue(loc);

        DatabaseReference reff=FirebaseDatabase.getInstance().getReference().child("UserInfo").child(uid);
        reff.child("HospitalCity").setValue(S1);
        reff.child("HospitalArea").setValue(S);
    }

    private void SetHospitalNameOnce() {

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        Hospital_Names hospname= new Hospital_Names();
        String uid= user.getUid();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("HospitalName").child(uid);

            String S=Hospital_name.getText().toString().trim();
            hospname.setHosname(S);
            databaseReference.setValue(hospname);


        DatabaseReference reff=FirebaseDatabase.getInstance().getReference().child("UserInfo").child(uid);
        reff.child("HospitalName").setValue(S);



    }


    //for sign out
//    @Override
//    public boolean onCreateOptionsMenu (Menu menu)
//    {
//        getMenuInflater().inflate(R.menu.content_menu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId()==R.id.SignOutMenuId)
//        {
//            FirebaseAuth.getInstance().signOut();
//            finish();
//            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
//            startActivity(intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.logoutplusprofilemenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logoutmenuid)
        {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.profilemenuid)
        {
            Intent intent = new Intent(getApplicationContext(),ProviderProfileActivity.class);
            startActivity(intent);


        }
        return super.onOptionsItemSelected(item);
    }
}