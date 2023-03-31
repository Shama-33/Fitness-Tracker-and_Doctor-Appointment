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

public class UserProfileActivity extends AppCompatActivity {


    TextView name,email,phone,userID,accounttype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name=findViewById(R.id.nameUser);
        email=findViewById(R.id.emailuser);
        phone=findViewById(R.id.phoneUser);
        userID=findViewById(R.id.userIDUser);
        accounttype=findViewById(R.id.accType);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("UserInfo");
        databaseReference.child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String n=snapshot.child("name").getValue().toString();
                String p=snapshot.child("phone").getValue().toString();
                String e=snapshot.child("email").getValue().toString();
                String a=snapshot.child("accountType").getValue().toString();
                String u=snapshot.child("userid").getValue().toString();
                name.setText("Name : "+n);
                email.setText("Email : "+e);
                phone.setText("Phone no : "+p);
                userID.setText("User ID : "+u);
                accounttype.setText("Account Type : "+a);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}