package com.example.project0011;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProviderProfileActivity extends AppCompatActivity {
    TextView name,email,phone,userID,accounttype,hosname,hosarea,hoscity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_profile);

        getSupportActionBar().hide();

        name=findViewById(R.id.nameProv);
        email=findViewById(R.id.emailProv);
        phone=findViewById(R.id.phoneProv);
        userID=findViewById(R.id.userIDUserProv);
        accounttype=findViewById(R.id.accTypeProv);
        hosname=findViewById(R.id.Hospitalnameprov);
        hosarea=findViewById(R.id.HospitalAreaprov);
        hoscity=findViewById(R.id.HospitalCityprov);



        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("UserInfo");
        databaseReference.child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String n=snapshot.child("name").getValue().toString();
                String p=snapshot.child("phone").getValue().toString();
                String e=snapshot.child("email").getValue().toString();
                String a=snapshot.child("accountType").getValue().toString();
                String u=snapshot.child("userid").getValue().toString();
                String hn=snapshot.child("HospitalName").getValue().toString();
                String ha=snapshot.child("HospitalArea").getValue().toString();
                String hc=snapshot.child("HospitalCity").getValue().toString();
                name.setText("Name : "+n);
                email.setText("Email : "+e);
                phone.setText("Phone no : "+p);
                userID.setText("User ID : "+u);
                accounttype.setText("Account Type : "+a);
                hosname.setText("Hospital Name : "+hn);
                hoscity.setText(" "+hc);
                hosarea.setText(" "+ha);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}