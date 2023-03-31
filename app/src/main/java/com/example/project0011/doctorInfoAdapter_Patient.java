package com.example.project0011;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class doctorInfoAdapter_Patient extends RecyclerView.Adapter<doctorInfoAdapter_Patient.HolderDoctor> implements Filterable {

    private Context context;
    public ArrayList<DoctorInfo> doctorlist,filterlist;
    private filterdoc_patient fild;
    String Hospitalname,Hospitalcity,HospitalArea,HospitalNumber,Uidpass;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setDoctorlist(ArrayList<DoctorInfo> doctorlist) {
        this.doctorlist = doctorlist;
    }

    public doctorInfoAdapter_Patient(Context context, ArrayList<DoctorInfo> doctorlist) {
        this.context = context;
        this.doctorlist = doctorlist;
        this.filterlist=doctorlist;
    }

    @NonNull
    @Override
    public HolderDoctor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_doctor_patient_view,parent,false);
        return new HolderDoctor(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderDoctor holder, int position) {

        DoctorInfo doctorInfo=doctorlist.get(position);
        String id=doctorInfo.getiD();
        String name=doctorInfo.getDocName();
        String spec=doctorInfo.getDocSpec();
        String time=doctorInfo.getDocTime();
        String deg=doctorInfo.getDocDeg();
        String UId=doctorInfo.getUId();
        String docphoto=doctorInfo.getDocPhoto();
        String Timestampp=doctorInfo.getTimeStamp();

       // Log.d("doctorname",UId);




        holder.docNameTV.setText(name);
        holder.docTimeTV.setText(time);
        holder.docspecTV.setText(spec);
        holder.docdegtv.setText(deg);
        try
        {
            Picasso.with(context).load(docphoto).placeholder(R.drawable.doctor).into(holder.docimagetv);


        }catch (Exception e){

            holder.docimagetv.setImageResource(R.drawable.doctor);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                int pos=holder.getBindingAdapterPosition();
               // HospitalModel hm=hospitalList.get(pos);
                DoctorInfo di=doctorlist.get(pos);
                //String userID1=hm.getUserid();


                //Intent intent=new Intent(context,HosDetailActivity.class);
                //intent.putExtra("SHOP_UID",userID1);
//                String s=v.getContext().toString();
//                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
              //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               // context.startActivity(intent);
                // Toast.makeText(context, "Item clicked", Toast.LENGTH_SHORT).show();

                View vieww=LayoutInflater.from(context).inflate(R.layout.appoinmentdl,null);
                //docimagetv2 docNameTV2 docspecTV2 docTimeTV2 docdegtv2 confirm_button2
                ImageView docimage2=vieww.findViewById(R.id.docimagetv2);
                TextView docname2=vieww.findViewById(R.id.docNameTV2);
                TextView docspec2=vieww.findViewById(R.id.docspecTV2);
                TextView doctime2=vieww.findViewById(R.id.docTimeTV2);
                TextView docdeg2=vieww.findViewById(R.id.docdegtv2);
                Button confirmbutton2=vieww.findViewById(R.id.confirm_button2);




                String id2=di.getiD();
                String name2=di.getDocName();
                String spec2=di.getDocSpec();
                String time2=di.getDocTime();
                String deg2=di.getDocDeg();
                String UId2=di.getUId();
                String docphoto2=di.getDocPhoto();
                String Timestampp2=di.getTimeStamp();
                Uidpass=di.getUId();

                //Log.d("Name",Uidpass);


                //Toast.makeText(context, name2, Toast.LENGTH_SHORT).show();


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(vieww);
                try{
                    Picasso.with(context).load(docphoto2).placeholder(R.drawable.doctor).into(docimage2);

                }catch (Exception e){
                    docimage2.setImageResource(R.drawable.doctor);

                }
                docname2.setText(name2);
                docdeg2.setText(deg2);
                doctime2.setText(time2);
                docspec2.setText(spec2);


                AlertDialog dialog=builder.create();
                dialog.show();






                confirmbutton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //String Uidpass= UId2;
                        String docname1000=docname2.getText().toString();
                        String docdeg1000=docdeg2.getText().toString();
                        String docspec1000=docspec2.getText().toString();
                        String doctime1000=doctime2.getText().toString();
                        String uid1000=Uidpass;
                        String timestamp=""+System.currentTimeMillis();
                        //String RUID=FirebaseAuth.getInstance().getCurrentUser().getUid();

                        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                        String RUID=user.getUid();
                        String Cur_date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                        HashMap <String,String> hashMap= new HashMap<>();
                        hashMap.put("AppointmentID",timestamp);
                        hashMap.put("docname",docname1000);
                        hashMap.put("docdeg",docdeg1000);
                        hashMap.put("docspec",docspec1000);
                        hashMap.put("doctime",doctime1000);
                        //hashMap.put("UId",uid1000);
                        hashMap.put("date",Cur_date);
                        hashMap.put("appstatus","Requested");
                        hashMap.put("RequestedBy",RUID);

                        if(uid1000!=null)
                        {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Appointments");
                            databaseReference.child(uid1000).child(timestamp).setValue(hashMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });

                        }

                        //addtocart(docname1000,docspec1000,docdeg1000,doctime1000,timestamp);

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PreAppointments");
                        databaseReference.child(timestamp).setValue(hashMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });


                        dialog.dismiss();








                    }
                });

            }
        });

    }
//    private int itemid=1;
//
//    private void addtocart(String docname1000,String docspec1000,String docdeg1000,String doctime1000,String timestamp) {
//    itemid++;
//
//
//
//
//
//
//
//    }

    @Override
    public int getItemCount() {
        return doctorlist.size();
    }

    @Override
    public Filter getFilter() {
        if(fild==null){
            fild= new filterdoc_patient(this,filterlist);

        }
        return fild;
    }

    class HolderDoctor extends RecyclerView.ViewHolder{

        private ImageView docimagetv,nextTV;
        private TextView docNameTV,docspecTV,docTimeTV,docdegtv;
        private TextView AppointmentButton;

        public HolderDoctor(@NonNull View itemView) {
            super(itemView);

            docimagetv=itemView.findViewById(R.id.docimagetv1);
            nextTV=itemView.findViewById(R.id.nextTV1);
            docNameTV=itemView.findViewById(R.id.docNameTV1);
            docspecTV=itemView.findViewById(R.id.docspecTV1);
            docTimeTV=itemView.findViewById(R.id.docTimeTV1);
            docdegtv=itemView.findViewById(R.id.docdegtv1);
            AppointmentButton=itemView.findViewById(R.id.Appointment_take1);
        }
    }
}
