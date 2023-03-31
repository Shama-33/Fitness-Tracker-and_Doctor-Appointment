package com.example.project0011;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterAppointmentUser extends RecyclerView.Adapter<AdapterAppointmentUser.HolderAppointmentUser> {

    public AdapterAppointmentUser(Context context, ArrayList<ModelAppointmentUser> appList) {
        this.context = context;
        this.appList = appList;
    }

    private Context context;
    private ArrayList<ModelAppointmentUser>appList;

    @NonNull
    @Override
    public HolderAppointmentUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.appointment_patient,parent,false);

        return new HolderAppointmentUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAppointmentUser holder, int position) {

        ModelAppointmentUser modelAppointmentUser=appList.get(position);
        String dn=modelAppointmentUser.getDocname();
        String hn=modelAppointmentUser.getHospitalName();
        String s= modelAppointmentUser.getAppstatus();

        //loadshopinfo(modelAppointmentUser,holder);

        holder.doctornameView.setText("Doctor : "+dn);
        holder.hospitalnameView.setText("Hospital : "+hn);
        holder.Status.setText(s);



    }

//    private void loadshopinfo(ModelAppointmentUser modelAppointmentUser, HolderAppointmentUser holder) {
//    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    class HolderAppointmentUser extends RecyclerView.ViewHolder{

        private TextView hospitalnameView,doctornameView,Status;

        public HolderAppointmentUser(@NonNull View itemView) {
            super(itemView);
            hospitalnameView=itemView.findViewById(R.id.hospitalnameView);
            doctornameView=itemView.findViewById(R.id.doctornameView);
            Status=itemView.findViewById(R.id.Status);
        }
    }



}
