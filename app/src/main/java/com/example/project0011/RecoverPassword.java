package com.example.project0011;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class RecoverPassword extends AppCompatActivity {

    private EditText recoverymail;
    private Button recoverbutton;

    private FirebaseAuth firebaseAuth;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        //recoverbutton   recoverymail

        recoverbutton=findViewById(R.id.recoverbutton);
        recoverymail=findViewById(R.id.recoverymail);
        firebaseAuth=FirebaseAuth.getInstance();

        recoverbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecoverPass();

            }
        });


    }


    private void RecoverPass()
    {
        email= recoverymail.getText().toString().trim();
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(RecoverPassword.this, "Password Reset Instruction Mail Sent", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(RecoverPassword.this, "Password Reset Instruction Mail NOT Sent", Toast.LENGTH_SHORT).show();

                    }
                });

    }
}