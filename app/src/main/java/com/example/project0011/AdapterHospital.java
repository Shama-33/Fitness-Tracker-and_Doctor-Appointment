package com.example.project0011;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterHospital extends RecyclerView.Adapter<AdapterHospital.HolderHospital> implements Filterable {

    private Context context;
    public ArrayList<HospitalModel>hospitalList,filterlist;
    private filterhos fild;

    String hospitalname,area,city,phoneS,userID,accountType;





    public AdapterHospital(Context context, ArrayList<HospitalModel> hospitalList, ArrayList<HospitalModel> filterlist, filterhos fild) {
        this.context = context;
        this.hospitalList = hospitalList;
        this.filterlist = filterlist;
        this.fild = fild;
    }


    public void setHospitalList(ArrayList<HospitalModel> hospitalList) {
       this.hospitalList= hospitalList;
    }

    public AdapterHospital(Context context, ArrayList<HospitalModel> hospitalList) {
        this.context = context;
        this.hospitalList = hospitalList;
        this.filterlist=hospitalList;
    }

    @NonNull
    @Override
    public HolderHospital onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.single_hospital,parent,false);

        return new HolderHospital(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderHospital holder, int position) {


        HospitalModel hospitalModel=hospitalList.get(position);
        //String hospitalname,area,city,phoneS,userID,accountType;
        hospitalname= hospitalModel.getHospitalName();
        area=hospitalModel.getHospitalArea();
        city=hospitalModel.getHospitalCity();
        phoneS=hospitalModel.getPhone();
        userID=hospitalModel.getUserid();
        accountType=hospitalModel.getAccountType();

        holder.name.setText(hospitalname);
        holder.address.setText(area+" "+city);
        holder.phone.setText(phoneS);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=holder.getBindingAdapterPosition();
                HospitalModel hm=hospitalList.get(pos);
                String userID1=hm.getUserid();

                //Toast.makeText(context, hospitalname, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,HosDetailActivity.class);
                intent.putExtra("SHOP_UID",userID1);
//                String s=v.getContext().toString();
//                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
               // Toast.makeText(context, "Item clicked", Toast.LENGTH_SHORT).show();

            }
        });

    }




    @Override
    public int getItemCount() {
        return hospitalList.size();
    }


    @Override
    public Filter getFilter() {
        if(fild==null){
            fild= new filterhos(this,filterlist);

        }
        return fild;
    }

    class HolderHospital extends RecyclerView.ViewHolder{
        private TextView name ,address,phone;

        public HolderHospital(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.hosNameTV);
            address=itemView.findViewById(R.id.hoslocTV);
            phone=itemView.findViewById(R.id.phonenumtv);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent=new Intent(context,HosDetailActivity.class);
//                intent.putExtra("SHOP_UID",userID);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//
//                }
//            });
        }

    }
}
