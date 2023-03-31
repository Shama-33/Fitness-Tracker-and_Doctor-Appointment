package com.example.project0011;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

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

import java.util.ArrayList;

public class AddDoctorActivity extends AppCompatActivity {
    TextView Hname,filteredTV;
    TextView doctors,Appointments;
    private EditText search_bar,searchapp;
    private Button Filterbutton;
    ImageButton imageButton2,addDoc,Editdoc;
    private RelativeLayout doctorrel,apprel;
    private RecyclerView docrecV;
    private RecyclerView AppointmentsRV;
    //private Button EditStatus;

    private ArrayList<DoctorInfo>doclist;
    private doctorInfoAdapter docadapter;


    private ArrayList<ModelAppointmentUser>modlist;
    private AdapterAppointmentProvider adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);
        //this.setTitle("");
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.acbar)));
        getSupportActionBar().hide();
        Hname=findViewById(R.id.textView5);
        imageButton2=findViewById(R.id.imageButton2);
        addDoc=findViewById(R.id.addDoc);
        Appointments=findViewById(R.id.Appointments);
        doctors=findViewById(R.id.doctors);
        doctorrel=findViewById(R.id.doctorrel);
        apprel=findViewById(R.id.AppoinmentRelativeLayout);
        search_bar=findViewById(R.id.search_bar);
        Filterbutton=findViewById(R.id.Filterbutton);
        filteredTV=findViewById(R.id.filteredTV);
        docrecV=findViewById(R.id.docrecV);
        AppointmentsRV=findViewById(R.id.AppointmentsRV);
        searchapp=findViewById(R.id.searchapp);
        Editdoc=findViewById(R.id.EditDoc);
        //EditStatus=findViewById(R.id.EditStatus);

        LoadShopname();
        loadDoc();
        ShowAllDoc();



        searchapp.addTextChangedListener(new TextWatcher() {
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

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    docadapter.getFilter().filter(s);


                }catch(Exception e)
                {
                    e.printStackTrace();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        EditStatus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        Filterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(AddDoctorActivity.this);
                builder.setTitle("Choose Categoty")
                        .setItems(DocTypes.types1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selevted= DocTypes.types1[which];
                        filteredTV.setText(selevted);
                        //spec=C;
                        if(selevted.equals("All"))
                        {
                            loadDoc();

                        }
                        else
                        {
                            loadFildDoc(selevted);
                        }


                    }
                }).show();
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });
        addDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),addDoc2Activity.class);
                startActivity(intent);

            }
        });
        doctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAllDoc();

            }
        });
        Appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAllAppointment();
                ShowAllApp();

            }
        });

        Editdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),EditDocActivity.class);
                startActivity(intent);

            }
        });








    }

    private void loadAllAppointment() {
        modlist = new ArrayList<>();

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Appointments").child(FirebaseAuth.getInstance().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modlist.clear();
                for (DataSnapshot ds: snapshot.getChildren())
                {
                    String time= ""+ds.getRef().getKey();
                    ModelAppointmentUser modelAppointmentUser= ds.getValue(ModelAppointmentUser.class);
                    if (modelAppointmentUser.getRequestedBy()!=null)
                    { modlist.add(modelAppointmentUser);}
                    else{
                        DatabaseReference r= FirebaseDatabase.getInstance().getReference("Appointments")
                                .child((FirebaseAuth.getInstance().getUid())).child(time);
                        r.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                }
                adapter= new AdapterAppointmentProvider(getApplicationContext(),modlist);
                AppointmentsRV.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadFildDoc(String selected) {



        doclist=new ArrayList<>();

        DatabaseReference reff=FirebaseDatabase.getInstance().getReference("DoctorInfo");
        reff.child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
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
                docadapter= new doctorInfoAdapter(getApplicationContext(),doclist);
                docrecV.setAdapter(docadapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void loadDoc() {
        doclist=new ArrayList<>();

        DatabaseReference reff=FirebaseDatabase.getInstance().getReference("DoctorInfo");
        reff.child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doclist.clear();
                for(DataSnapshot ds: snapshot.getChildren())
                {
                    DoctorInfo dox= ds.getValue(DoctorInfo.class);
                    doclist.add(dox);
                }
                docadapter= new doctorInfoAdapter(getApplicationContext(),doclist);
                docrecV.setAdapter(docadapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ShowAllApp() {
        apprel.setVisibility(View.VISIBLE);
        doctorrel.setVisibility(View.GONE);
        Appointments.setTextColor(getResources().getColor(R.color.black));
        Appointments.setBackgroundColor(getResources().getColor(android.R.color.white));
        doctors.setTextColor(getResources().getColor(R.color.white));
        doctors.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    private void ShowAllDoc() {
        doctorrel.setVisibility(View.VISIBLE);
        apprel.setVisibility(View.GONE);

        doctors.setTextColor(getResources().getColor(R.color.black));
        doctors.setBackgroundColor(getResources().getColor(android.R.color.white));
        Appointments.setTextColor(getResources().getColor(R.color.white));
        Appointments.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    private void LoadShopname() {
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
                    Hname.setText(h);
                }
                else
                {
                    Toast.makeText(AddDoctorActivity.this, "MUST ADD HOSPITAL NAME", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(getApplicationContext(),ProviderActivity.class);
                    startActivity(intent);
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //for sign out
    //@Override
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




}