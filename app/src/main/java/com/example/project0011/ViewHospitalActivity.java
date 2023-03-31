package com.example.project0011;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewHospitalActivity extends AppCompatActivity {




    private RelativeLayout toolbar1,doctorrel1,apprel1;
    private TextView hospitalle1,Appointments1;
    private EditText search_bar1;
    private Button filterbutton;
    private TextView filteredTV1;
    private RecyclerView RV,appRV;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

    private ArrayList<HospitalModel>Hospitallist;
    private AdapterHospital adapterHospital;



    private ArrayList<ModelAppointmentUser>AppointmentList;
    private AdapterAppointmentUser adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hospital);

        this.setTitle("Find Hospital");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.acbar)));

        toolbar1=findViewById(R.id.toolbar1);
        doctorrel1=findViewById(R.id.doctorrel1);
        apprel1=findViewById(R.id.apprel1);
        hospitalle1=findViewById(R.id.hospitalle1);
        Appointments1=findViewById(R.id.Appointments1);
        search_bar1=findViewById(R.id.search_bar1);
        filteredTV1=findViewById(R.id.filteredTV1);
        RV=findViewById(R.id.SingleHospitalID);
        appRV=findViewById(R.id.SingleAppointemtID);
        
        loadmyInfo();
       // loadAppInfo();




        search_bar1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    adapterHospital.getFilter().filter(s);


                }catch(Exception e)
                {
                    e.printStackTrace();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





        hospitalle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAllHos();

            }
        });
        Appointments1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAppInfo();
                ShowAllApp();


            }
        });

    }

    private void loadAppInfo() {




        AppointmentList=new ArrayList<>();


        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Appointments");

                reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                AppointmentList.clear();
                for(DataSnapshot ds: datasnapshot.getChildren())
                {
                    String UID=""+ds.getRef().getKey();


                    DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Appointments").child(UID);
                    reff.orderByChild("RequestedBy").equalTo(firebaseAuth.getUid())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                    if(datasnapshot.exists())
                                    {
                                        for(DataSnapshot ds: datasnapshot.getChildren())
                                        {
                                            ModelAppointmentUser modelAppointmentUser=ds.getValue(ModelAppointmentUser.class);

                                            AppointmentList.add(modelAppointmentUser);
                                        }
                                        adapter= new AdapterAppointmentUser(getApplicationContext(),AppointmentList);
                                        appRV.setAdapter(adapter);

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });



                }


//






            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void loadmyInfo() {

        Hospitallist=new ArrayList<>();

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("UserInfo");
        reff.orderByChild("accountType").equalTo("Provider").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Hospitallist.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    String T=dataSnapshot.child("accountType").getValue().toString();
                    HospitalModel hospitalModel = dataSnapshot.getValue(HospitalModel.class);
                    //if(!T.equalsIgnoreCase("GeneralUser"));
                   // {

                        Hospitallist.add(hospitalModel);
                    //}

                }

                adapterHospital= new AdapterHospital(getApplicationContext(),Hospitallist);
                RV.setAdapter(adapterHospital);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void ShowAllApp() {
        apprel1.setVisibility(View.VISIBLE);
        doctorrel1.setVisibility(View.GONE);
        Appointments1.setTextColor(getResources().getColor(R.color.black));
        Appointments1.setBackgroundColor(getResources().getColor(android.R.color.white));
        hospitalle1.setTextColor(getResources().getColor(R.color.white));
        hospitalle1.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    private void ShowAllHos() {
        doctorrel1.setVisibility(View.VISIBLE);
        apprel1.setVisibility(View.GONE);

       hospitalle1.setTextColor(getResources().getColor(R.color.black));
       hospitalle1.setBackgroundColor(getResources().getColor(android.R.color.white));
        Appointments1.setTextColor(getResources().getColor(R.color.white));
        Appointments1.setBackgroundColor(getResources().getColor(android.R.color.transparent));
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