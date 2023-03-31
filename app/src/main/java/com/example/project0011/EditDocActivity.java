package com.example.project0011;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditDocActivity extends AppCompatActivity {
    EditText initialname,deldocname,deldoctime;
    Button entername,deletedoctor,editdoctor;
    String USER_ID="0",TIMESTAMP="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doc);

        initialname=findViewById(R.id.initialname);
        deldocname=findViewById(R.id.deldocname);
        deldoctime=findViewById(R.id.deldoctime);

        entername=findViewById(R.id.entername);
        deletedoctor=findViewById(R.id.deletedoctor);
        editdoctor=findViewById(R.id.editdoctor);

        entername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchData();
            }
        });

        deletedoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                del();
            }
        });

        editdoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDoctor();
            }
        });


    }




    private void EditDoctor() {
        String n,t;
        n=deldocname.getText().toString();
        t=deldoctime.getText().toString();
        if((USER_ID=="0")||n.isEmpty()||t.isEmpty())
        {
            return;
        }

        else
        {
            HashMap hashmap=new HashMap<String,String>();
            hashmap.put("docName",n);
            hashmap.put("docTime",t);
            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("DoctorInfo")
                    .child(FirebaseAuth.getInstance().getUid()).child(TIMESTAMP);
            databaseReference.updateChildren(hashmap);

        }
    }




    private void del() {
        String n,t;
        n=deldocname.getText().toString();
        t=deldoctime.getText().toString();
        if((USER_ID=="0")||n.isEmpty()||t.isEmpty())
        {
            return;
        }

        else
        {
            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("DoctorInfo")
                    .child(FirebaseAuth.getInstance().getUid()).child(TIMESTAMP);
            databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(EditDocActivity.this, "Doctor Removed", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditDocActivity.this, "Doctor Not Removed", Toast.LENGTH_SHORT).show();

                }
            });

        }


    }

    private void FetchData() {
        String str= initialname.getText().toString().trim();
        if(str.isEmpty())
        {
            return;
        }
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("DoctorInfo")
                .child(FirebaseAuth.getInstance().getUid());
        databaseReference.orderByChild("docName").equalTo(str).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot ds: snapshot.getChildren())
                    {
                        String n,t;
                        n=ds.child("docName").getValue().toString();
                        t=ds.child("docTime").getValue().toString();
                        USER_ID=ds.child("UID").getValue().toString();
                        TIMESTAMP=ds.child("timeStamp").getValue().toString();
                        deldocname.setText(n);
                        deldoctime.setText(t);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}