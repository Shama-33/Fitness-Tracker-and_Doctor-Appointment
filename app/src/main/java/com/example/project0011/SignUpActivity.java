package com.example.project0011;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth mAuth;
    private EditText emailup,passwordup,passwordupconfirm,Username,phone;
    private TextView textViewup;
    private Button buttonup,buttonuser;
    private ProgressBar progressbarup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.setTitle("Sign Up");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.acbar)));

        mAuth = FirebaseAuth.getInstance();


        Username=(EditText) findViewById(R.id.username);
        phone=findViewById(R.id.phone);
        emailup=(EditText) findViewById(R.id.emailup);
        passwordup=(EditText) findViewById(R.id.passwordup);
        passwordupconfirm=(EditText) findViewById(R.id.passwordupconfirm);
        textViewup=(TextView) findViewById(R.id.textViewup);
        buttonup= (Button) findViewById(R.id.buttonup);
        buttonuser=findViewById(R.id.buttonuser);
        progressbarup=(ProgressBar) findViewById(R.id.progressBarup);

        buttonup.setOnClickListener(this);
        textViewup.setOnClickListener(this);
        buttonuser.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonup)
        {
            providerReg();
            //UserRegister();
        }
        else if(view.getId()==R.id.buttonuser)
        {
            GeneralReg();

        }
        else{
            Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
            startActivity(intent);
        }

    }




    private void UserRegister()
    {
        String user=Username.getText().toString().trim();

        if(user.isEmpty())
        {
            Username.setError("Enter  name");
            Toast.makeText(SignUpActivity.this,"name not entered",Toast.LENGTH_SHORT).show();
        }
        if(user.length()>30)
        {
            Username.setError("Name must be within 8 characters");
            Toast.makeText(SignUpActivity.this,"Name too long",Toast.LENGTH_SHORT).show();
        }
        String Phone_no=phone.getText().toString().trim();
        if(Phone_no.isEmpty())
        {
            Username.setError("Enter phone Number");
            Toast.makeText(SignUpActivity.this,"Phone Number not entered",Toast.LENGTH_SHORT).show();
        }


        String email=emailup.getText().toString().trim();

        //validity of email
        if(email.isEmpty())
        {
            emailup.setError("Enter email address");
            Toast.makeText(SignUpActivity.this,"Invalid Email",Toast.LENGTH_SHORT).show();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailup.setError("Enter valid email address");
            Toast.makeText(SignUpActivity.this,"Invalid Email",Toast.LENGTH_SHORT).show();
            emailup.requestFocus();
            return;
        }
        String pass=passwordup.getText().toString().trim();

        if(pass.isEmpty())
        {
            passwordup.setError("Enter a password");
            Toast.makeText(SignUpActivity.this,"Enter Password",Toast.LENGTH_SHORT).show();
        }
        if(pass.length()<6)
        {
            passwordup.setError("Password must be at least 6 characters long");
            Toast.makeText(SignUpActivity.this,"Too short Password",Toast.LENGTH_SHORT).show();
            passwordup.requestFocus();
            return;
        }
        if(pass.length()>32)
        {
            passwordup.setError("Password must shorter than 32 characters");
            Toast.makeText(SignUpActivity.this,"Too long Password",Toast.LENGTH_SHORT).show();
            passwordup.requestFocus();
            return;
        }

        String passcmp=passwordupconfirm.getText().toString().trim();
        if(!passcmp.equals(pass))
        {
            passwordupconfirm.setError("Password does not match");
            Toast.makeText(SignUpActivity.this,"Password not matched",Toast.LENGTH_SHORT).show();
            passwordupconfirm.requestFocus();
            return;
        }





    }

    private void providerReg() {

        UserRegister();

        String email=emailup.getText().toString().trim();
        String pass=passwordup.getText().toString().trim();
        String Phone_no=phone.getText().toString().trim();
        String user=Username.getText().toString().trim();


        progressbarup.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressbarup.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    //push Data
                    UserData userData=new UserData();
//                    userData.AccountType="Provider";
//                    userData.name=user;
//                    userData.phone=Phone_no;
//                    userData.email=email;
//                    userData.userid=mAuth.getUid();

                    userData.setAccountType("Provider");
                    userData.setUserid(mAuth.getUid());
                    userData.setName(user);
                    userData.setEmail(email);
                    userData.setPhone(Phone_no);

                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("UserInfo");
                    databaseReference.child(mAuth.getUid()).setValue(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(SignUpActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this, "Data NOT Updated", Toast.LENGTH_SHORT).show();
                        }
                    });





                    Toast.makeText(getApplicationContext(), "Provider Register Successful", Toast.LENGTH_SHORT).show();
                    finish();//page wont be seen while returning
                    Intent intent= new Intent(SignUpActivity.this, ProviderActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }

                else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {Toast.makeText(getApplicationContext(), "User is already registered", Toast.LENGTH_SHORT).show();}
                    else{Toast.makeText(getApplicationContext(), "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();}
                }


            }
        });
    }



    private void GeneralReg() {


        UserRegister();
        String email=emailup.getText().toString().trim();
        String pass=passwordup.getText().toString().trim();
        String Phone_no=phone.getText().toString().trim();
        String user=Username.getText().toString().trim();


        progressbarup.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressbarup.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    //push Data
                    UserData userData=new UserData();
//                    userData.AccountType="GeneralUser";
//                    userData.name=user;
//                    userData.phone=Phone_no;
//                    userData.email=email;
//                    userData.userid=mAuth.getUid();
                    userData.setAccountType("GeneralUser");
                    userData.setUserid(mAuth.getUid());
                    userData.setName(user);
                    userData.setEmail(email);
                    userData.setPhone(Phone_no);

                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("UserInfo");
                    databaseReference.child(mAuth.getUid()).setValue(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(SignUpActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this, "Data NOT Updated", Toast.LENGTH_SHORT).show();
                        }
                    });







                    Toast.makeText(getApplicationContext(), "User Register Successful", Toast.LENGTH_SHORT).show();
                    finish();//page wont be seen while returning
                    Intent intent= new Intent(SignUpActivity.this, IndexActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }

                else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {Toast.makeText(getApplicationContext(), "User is already registered", Toast.LENGTH_SHORT).show();}
                    else{Toast.makeText(getApplicationContext(), "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();}
                }


            }
        });
    }
}