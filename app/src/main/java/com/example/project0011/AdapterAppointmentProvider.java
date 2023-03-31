package com.example.project0011;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterAppointmentProvider extends RecyclerView.Adapter<AdapterAppointmentProvider.HolderAppointmentProvider> implements Filterable {

    private Context context;
    public ArrayList<ModelAppointmentUser>modellist,filterlist;
    private filterAppointmentProvider fild;

    public AdapterAppointmentProvider(Context context, ArrayList<ModelAppointmentUser> modellist) {
        this.context = context;
        this.modellist = modellist;
        this.filterlist=modellist;
    }

    @NonNull
    @Override
    public HolderAppointmentProvider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.single_app_doc,parent,false);
        return new HolderAppointmentProvider(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAppointmentProvider holder, int position) {

        ModelAppointmentUser modelAppointmentUser=modellist.get(position);
        String docname,docdeg,docspec,doctime,UId,date,appstatus,RequestedBy;
        String HospitalName;
        String HospitalCity;
        String HospitalArea;
        docname=modelAppointmentUser.getDocname();
        HospitalName=modelAppointmentUser.getHospitalName();
        RequestedBy= modelAppointmentUser.getRequestedBy();
        appstatus=modelAppointmentUser.getAppstatus();

        //loadUserInfo(modelAppointmentUser,holder);

        holder.hospitalname.setText("Hospital : "+HospitalName);
        holder.doctorname.setText("Doctor "+docname);
        holder.RequetedBy.setText("Requested By : "+RequestedBy);
        holder.Status.setText(appstatus);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int pos=holder.getBindingAdapterPosition();
                //HospitalModel hm=hospitalList.get(pos);
                ModelAppointmentUser mau= modellist.get(pos);
                String userID1=mau.getUId();
                String RequestedBy= mau.getRequestedBy();
                String HN= mau.getHospitalName();
                String DN= mau.getDocname();
                String Id= mau.getAppointmentID();
                String status= mau.getAppstatus();
                Log.d("ID",Id);


              //  Toast.makeText(context, hospitalname, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,EditStatusActivity.class);
                intent.putExtra("UID",userID1);
                intent.putExtra("RequestedBy",RequestedBy);
                intent.putExtra("HospitalName",HN);
                intent.putExtra("DoctorName",DN);
                intent.putExtra("APP_ID",Id);
                intent.putExtra("Status",status);
//                String s=v.getContext().toString();
//                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                // Toast.makeText(context, "Item clicked", Toast.LENGTH_SHORT).show();




            }
        });

    }

//    private void loadUserInfo(ModelAppointmentUser modelAppointmentUser, HolderAppointmentProvider holder) {
//        DatabaseReference reff= FirebaseDatabase.getInstance().getReference("Appointments");
//    }

    @Override
    public int getItemCount() {
        return modellist.size();
    }

    @Override
    public Filter getFilter() {
        if(fild==null){
            fild= new filterAppointmentProvider(this,filterlist);

        }
        return fild;
    }

    class HolderAppointmentProvider extends RecyclerView.ViewHolder{


        private TextView hospitalname,doctorname,RequetedBy,Status;
        public HolderAppointmentProvider(@NonNull View itemView) {
            super(itemView);

            hospitalname=itemView.findViewById(R.id.hospitalnameVieww);
            doctorname=itemView.findViewById(R.id.doctornameVieww);
            RequetedBy=itemView.findViewById(R.id.RequetedBy);
            Status=itemView.findViewById(R.id.Statuss);
        }
    }
}
