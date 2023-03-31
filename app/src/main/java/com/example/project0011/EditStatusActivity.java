package com.example.project0011;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;

public class EditStatusActivity extends AppCompatActivity {

    private TextView OrderID,DoctorNameIDd,Statusd,Reqby;
    private EditText Update;
    String UID,RequestedBy,HospitalName,DoctorName,ID,Status;
    String Text;
    private Button UpdateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_status);

        this.setTitle("Schedule/Cancel");

        OrderID=findViewById(R.id.OrderID);
        DoctorNameIDd=findViewById(R.id.DoctorNameIDd);
        Statusd=findViewById(R.id.Statusd);
        Update=findViewById(R.id.Update);
        UpdateButton=findViewById(R.id.UpdateButton);
        Reqby=findViewById(R.id.Reqby);

        /*
        intent.putExtra("UID",userID1);
                intent.putExtra("RequestedBy",RequestedBy);
                intent.putExtra("HospitalName",HN);
                intent.putExtra("DoctorName",DN);
                intent.putExtra("APP_ID",Id);
                intent.putExtra("Status",status);
         */

        UID=getIntent().getStringExtra("UID");
        RequestedBy=getIntent().getStringExtra("RequestedBy");
        HospitalName=getIntent().getStringExtra("HospitalName");
        DoctorName=getIntent().getStringExtra("DoctorName");
        ID=getIntent().getStringExtra("APP_ID");
        Status=getIntent().getStringExtra("Status");

        //Toast.makeText(this, ID, Toast.LENGTH_SHORT).show();

        OrderID.setText(ID);
        DoctorNameIDd.setText(DoctorName);
        Reqby.setText(RequestedBy);
        Statusd.setText(Status);



        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelAppointmentUser modelAppointmentUser=new ModelAppointmentUser();
                modelAppointmentUser.setAppstatus(Text);

                Text= Update.getText().toString();
//
                HashMap hashMap= new HashMap<>();
                hashMap.put("appstatus",""+Text);

                DatabaseReference  databaseReference= FirebaseDatabase.getInstance().getReference("Appointments");
                databaseReference.child(FirebaseAuth.getInstance().getUid()).child(ID)
                        .updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(EditStatusActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditStatusActivity.this, "Sorry", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });



    }
}