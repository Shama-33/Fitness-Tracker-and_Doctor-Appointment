package com.example.project0011;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

   private FirebaseAuth mAuth;

    private EditText emailin,passwordin;
    private TextView textViewin,forgotpass;
    private Button buttonin;
    private SharedPreferences sharedPreferences;
   // private ProgressBar progressbarin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        this.setTitle("Login");

        //getSupportActionBar().hide();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.acbar)));
        emailin=(EditText) findViewById(R.id.emailin);
        passwordin=(EditText) findViewById(R.id.passwordin);
        textViewin=(TextView) findViewById(R.id.textViewin);
        buttonin= (Button) findViewById(R.id.buttinin);
        forgotpass=findViewById(R.id.forgotpass);
       // progressbarin=(ProgressBar) findViewById(R.id.progressBarin);

        buttonin.setOnClickListener(this);
        textViewin.setOnClickListener(this);
        forgotpass.setOnClickListener(this);
        sharedPreferences=this.getSharedPreferences("com.example.project0011",MODE_PRIVATE);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttinin)
        {
            userlogin();
        }
        else if(view.getId()==R.id.forgotpass)
        {
            Intent intent = new Intent(getApplicationContext(),RecoverPassword.class);
            startActivity(intent);

        }
        else{
            Intent intent=new Intent(MainActivity.this,SignUpActivity.class);
            startActivity(intent);
        }

    }

    private void userlogin() {


        String email=emailin.getText().toString().trim();
        String pass=passwordin.getText().toString().trim();

        //validity of email
        if(email.isEmpty())
        {
            emailin.setError("Enter email address");
            Toast.makeText(MainActivity.this,"Invalid Email",Toast.LENGTH_SHORT).show();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailin.setError("Enter valid email address");
            Toast.makeText(MainActivity.this,"Invalid Email",Toast.LENGTH_SHORT).show();
            emailin.requestFocus();
            return;
        }


        if(pass.isEmpty())
        {
            passwordin.setError("Enter a password");
            Toast.makeText(MainActivity.this,"Enter Password",Toast.LENGTH_SHORT).show();
        }
        if(pass.length()<6)
        {
            passwordin.setError("Password must be at least 6 characters long");
            Toast.makeText(MainActivity.this,"Too short Password",Toast.LENGTH_SHORT).show();
            passwordin.requestFocus();
            return;
        }
        if(pass.length()>32)
        {
            passwordin.setError("Password must shorter than 32 characters");
            Toast.makeText(MainActivity.this,"Too long Password",Toast.LENGTH_SHORT).show();
            passwordin.requestFocus();
            return;
        }
        //progressbarin.setVisibility(View.VISIBLE);

//        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//
//                //progressbarin.setVisibility(View.GONE);
//
//                if(task.isSuccessful())
//                {
//
//                    FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
//                    DatabaseReference databaseReference;
//                    String Cur_date=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
//
//
//
//
//                    if(user != null) {
//                        String uid = user.getUid();
//                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Data").child(uid).child(Cur_date);
//
//
//                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                                if (!snapshot.exists()) {
//                                    sharedPreferences.edit().putInt(Cur_date, 0).apply();
//
//                                } else {
//
//
//                                    Data dt = snapshot.getValue(Data.class);
//                                    sharedPreferences.edit().putInt(Cur_date, dt.getSteps()).apply();
//                                    //Toast.makeText(getApplicationContext(), "step " + dt.getSteps(), Toast.LENGTH_SHORT).show();
//                                }
//
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//                    }
//
//
//
//                    finish();//page wont be seen while returning
//                    Intent intent= new Intent(MainActivity.this,IndexActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//
//
//
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext(),"Login Unsuccessful",Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });







        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //progressbarin.setVisibility(View.GONE);

                if(task.isSuccessful())
                {
                    FirebaseUser u=FirebaseAuth.getInstance().getCurrentUser();
                    String S=u.getUid();

                     DatabaseReference reff=FirebaseDatabase.getInstance().getReference().child("UserInfo").child(S);
                     reff.addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                             UserData userData=new UserData();
                            // userData.AccountType=snapshot.child("accountType").getValue().toString();
                             userData.setAccountType(snapshot.child("accountType").getValue().toString());
                             String c=userData.getAccountType();
                             //Toast.makeText(MainActivity.this, c, Toast.LENGTH_SHORT).show();
                             if(c.equalsIgnoreCase("GeneralUser"))

                             {
                                 FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
                                 DatabaseReference databaseReference;
                                 String Cur_date=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


                                 if(user != null) {
                                     String uid = user.getUid();
                                     databaseReference = FirebaseDatabase.getInstance().getReference().child("Data")
                                             .child(uid).child(Cur_date);

                                     databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                         @Override
                                         public void onDataChange(@NonNull DataSnapshot snapshot) {

                                             if (!snapshot.exists()) {
                                                 sharedPreferences.edit().putInt(Cur_date, 0).apply();
                                             }
                                             else {
                                                 Data dt = snapshot.getValue(Data.class);
                                                 int stp = 0;
                                                 if(dt != null){
                                                     stp = dt.getSteps();
                                                 }
                                                 sharedPreferences.edit().putInt(Cur_date,stp).apply();
                                             }

                                             finish();//page wont be seen while returning
                                             Intent intent= new Intent(MainActivity.this,IndexActivity.class);
                                             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                             startActivity(intent);

                                         }

                                         @Override
                                         public void onCancelled(@NonNull DatabaseError error) {

                                         }
                                     });
                                 }





                             }

                             else
                             {
                                 finish();//page wont be seen while returning
                                 Intent intent= new Intent(MainActivity.this,ProviderActivity.class);
                                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                 startActivity(intent);

                             }



                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError error) {

                         }
                     });




//                    FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
//                    DatabaseReference databaseReference;
//                    String Cur_date=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
//
//
//                    if(user != null) {
//                        String uid = user.getUid();
//                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Data")
//                                .child(uid).child(Cur_date);
//
//                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                                if (!snapshot.exists()) {
//                                    sharedPreferences.edit().putInt(Cur_date, 0).apply();
//                                }
//                                else {
//                                    Data dt = snapshot.getValue(Data.class);
//                                    int stp = 0;
//                                    if(dt != null){
//                                        stp = dt.getSteps();
//                                    }
//                                    sharedPreferences.edit().putInt(Cur_date,stp).apply();
//                                }
//
//                                finish();//page wont be seen while returning
//                                Intent intent= new Intent(MainActivity.this,IndexActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Login Unsuccessful",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }




    //logged in check
    //if logged in direct content act e jabe
    @Override
    public void onStart() {

        super.onStart();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            String S=firebaseUser.getUid();
            DatabaseReference reff=FirebaseDatabase.getInstance().getReference().child("UserInfo").child(S);
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserData userData=new UserData();
                    //userData.AccountType=snapshot.child("accountType").getValue().toString();
                    userData.setAccountType(snapshot.child("accountType").getValue().toString());
                    String c=userData.getAccountType();
                    if(c.equalsIgnoreCase("GeneralUser"))
                    {
                        Intent intent = new Intent(getApplicationContext(),IndexActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else
                    {

                        Intent intent = new Intent(getApplicationContext(),ProviderActivity.class);
                        startActivity(intent);
                        finish();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




//            Intent intent = new Intent(getApplicationContext(),IndexActivity.class);
//            startActivity(intent);
//            finish();
        }
    }

}