package com.example.project0011;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class HosDetailActivity extends AppCompatActivity {

    private TextView hosname,hosphone,hosAdd;
    private ImageButton callbutton;
    RelativeLayout Rel;
    private EditText search;
    private Button Filterbutton;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private doctorInfoAdapter_Patient adapter;
    private TextView filteredTV;
    private Button ConfirmApp;
    String docname,docdeg,docspec,doctime,UId,date,appstatus,RequestedBy;







    String userid;
    String name;
    String phone;
    String email;
    String AccountType;
    String HospitalName;
    String HospitalCity;
    String HospitalArea;




    private String SHOP_UID;
    private ArrayList<DoctorInfo>doclist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hos_detail);
        this.setTitle("Doctors List");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.acbar)));

    hosname=findViewById(R.id.hosnamepatTV);
    hosphone=findViewById(R.id.hosphonepatTV);
    hosAdd=findViewById(R.id.hosAddpatTV);
    callbutton=findViewById(R.id.callbutton);
    //mapbutton=findViewById(R.id.mapbutton);
    Rel=findViewById(R.id.Doctorspat);
    search=findViewById(R.id.searchdoc);
    Filterbutton=findViewById(R.id.filterButtonp);
    recyclerView=findViewById(R.id.DocRvpat);
    filteredTV= findViewById(R.id.weid);
    ConfirmApp=findViewById(R.id.ConfirmApp);



        SHOP_UID=getIntent().getStringExtra("SHOP_UID");
        firebaseAuth=FirebaseAuth.getInstance();



        
        loadhosdetail();
        loadhosdoc();
        
        callbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialPhone();
            }
        });

        ConfirmApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*

                hashMap.put("AppointmentID",timestamp);
                        hashMap.put("docname",docname1000);
                        hashMap.put("docdeg",docdeg1000);
                        hashMap.put("docspec",docspec1000);
                        hashMap.put("doctime",doctime1000);
                        //hashMap.put("UId",uid1000);
                        hashMap.put("date",Cur_date);
                        hashMap.put("appstatus","Requested");
                        hashMap.put("RequestedBy",RUID);

                 */
//



//                DatabaseReference reff=FirebaseDatabase.getInstance().getReference("PreAppointments");
//                reff.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for(DataSnapshot ds: snapshot.getChildren())
//                        {
//
//                            docname=ds.child("docname").getValue().toString();
//                            docdeg =ds.child("docdeg").getValue().toString();
//                            docspec =ds.child("docspec").getValue().toString();
//                            doctime =ds.child("doctime").getValue().toString();
//                            date =ds.child("date").getValue().toString();
//                            appstatus= ds.child("appstatus").getValue().toString();
//                            RequestedBy = ds.child("RequestedBy").getValue().toString();
//
//
//
//
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(HosDetailActivity.this, "Data not Updated", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
               // Toast.makeText(HosDetailActivity.this, docname, Toast.LENGTH_SHORT).show();




                UploadToDatabase();
            }
        });







        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    adapter.getFilter().filter(s);


                }catch(Exception e)
                {
                    e.printStackTrace();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        Filterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(HosDetailActivity.this);
                builder.setTitle("Choose Categoty")
                        .setItems(DocTypes.types1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String selected= DocTypes.types1[which];
                                filteredTV.setText(selected);
                                //spec=C;
                                if(selected.equals("All"))
                                {
                                    loadhosdoc();

                                }
                                else
                                {
                                    loadFildDoc(selected);
                                }


                            }
                        }).show();
            }
        });









    }

    private void UploadToDatabase() {





        DatabaseReference refff=FirebaseDatabase.getInstance().getReference("PreAppointments");
        refff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                for(DataSnapshot ds: snapshot.getChildren()) {
                    {

                        docname = ds.child("docname").getValue().toString();
                        docdeg = ds.child("docdeg").getValue().toString();
                        docspec = ds.child("docspec").getValue().toString();
                        doctime = ds.child("doctime").getValue().toString();
                        date = ds.child("date").getValue().toString();
                        appstatus = ds.child("appstatus").getValue().toString();
                        RequestedBy = ds.child("RequestedBy").getValue().toString();



                    }
                }

                }
                else
                {
                    ConfirmApp.setText("Confirm Appointment");
                    return;

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HosDetailActivity.this, "Data not Updated", Toast.LENGTH_SHORT).show();
                return;

            }
        });





        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String RUID=user.getUid();
        String Cur_date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String timestamp= ""+System.currentTimeMillis();

        HashMap<String,String> hashMap= new HashMap<>();
        hashMap.put("AppointmentID",timestamp);
        hashMap.put("docname",docname);
        hashMap.put("docdeg",docdeg);
        hashMap.put("docspec",docspec);
        hashMap.put("doctime",doctime);
        hashMap.put("UId",SHOP_UID);
        hashMap.put("date",Cur_date);
        hashMap.put("appstatus",appstatus);
        hashMap.put("RequestedBy",RequestedBy);
        hashMap.put("HospitalName",HospitalName);
        hashMap.put("HospitalCity",HospitalCity);
        hashMap.put("HospitalArea",HospitalArea);

        if(SHOP_UID!=null)
        {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Appointments");
            databaseReference.child(SHOP_UID).child(timestamp).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(HosDetailActivity.this, "Value Updated", Toast.LENGTH_LONG).show();
                            ConfirmApp.setText("Request Sent!!");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(HosDetailActivity.this, "Value Not Updated", Toast.LENGTH_SHORT).show();

                        }
                    });

        }




        DatabaseReference reff=FirebaseDatabase.getInstance().getReference("PreAppointments");
        reff.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });



    }

    private void DialPhone() {

        startActivity((new Intent(Intent.ACTION_DIAL, Uri.parse("tel: "+Uri.encode(phone)))));
        Toast.makeText(this, ""+phone, Toast.LENGTH_SHORT).show();
    }

    private void loadhosdoc() {
        doclist = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DoctorInfo").child(SHOP_UID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doclist.clear();
                for(DataSnapshot ds : snapshot.getChildren())
                {
                    DoctorInfo doctorInfo= ds.getValue(DoctorInfo.class);
                    doclist.add(doctorInfo);
                }
                adapter=new doctorInfoAdapter_Patient(HosDetailActivity.this,doclist);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadhosdetail() {
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("UserInfo");
        UserData userData=new UserData();
        reff.child(SHOP_UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                /*
                String userid;
    String name;
    String phone;
    String email;
    String AccountType;
    String HospitalName;
    String HospitalCity;
    String HospitalArea;


                 */
                userid=snapshot.child("userid").getValue().toString();
                name=snapshot.child("name").getValue().toString();
                phone=snapshot.child("phone").getValue().toString();
                email=snapshot.child("email").getValue().toString();
                AccountType=snapshot.child("accountType").getValue().toString();
                HospitalName=snapshot.child("HospitalName").getValue().toString();
                HospitalCity=snapshot.child("HospitalCity").getValue().toString();
                HospitalArea=snapshot.child("HospitalArea").getValue().toString();

                hosAdd.setText(HospitalArea+" "+HospitalCity);
                hosname.setText(HospitalName);
                hosphone.setText(phone);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void loadFildDoc(String selected) {



        doclist=new ArrayList<>();

        DatabaseReference reff=FirebaseDatabase.getInstance().getReference("DoctorInfo");
        reff.child(SHOP_UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doclist.clear();
                for(DataSnapshot ds: snapshot.getChildren())
                {
                    String pc=""+ds.child("docSpec").getValue().toString();
                    if (selected.equals(pc)) {
                        DoctorInfo dox = ds.getValue(DoctorInfo.class);
                        doclist.add(dox);
                    }
                }
                adapter= new doctorInfoAdapter_Patient(getApplicationContext(),doclist);
                recyclerView.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }







    //for sign out
    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.content_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.SignOutMenuId)
        {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}