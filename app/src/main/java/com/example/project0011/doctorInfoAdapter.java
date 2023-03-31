package com.example.project0011;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class doctorInfoAdapter extends RecyclerView.Adapter<doctorInfoAdapter.HolderDoctor> implements Filterable {

    private Context context;
    public ArrayList<DoctorInfo> doctorlist,filterlist;
    private filterdoc fild;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setDoctorlist(ArrayList<DoctorInfo> doctorlist) {
        this.doctorlist = doctorlist;
    }

    public doctorInfoAdapter(Context context, ArrayList<DoctorInfo> doctorlist) {
        this.context = context;
        this.doctorlist = doctorlist;
        this.filterlist=doctorlist;
    }

    @NonNull
    @Override
    public HolderDoctor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_doctor,parent,false);
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



            }
        });

    }

    @Override
    public int getItemCount() {
        return doctorlist.size();
    }

    @Override
    public Filter getFilter() {
        if(fild==null){
            fild= new filterdoc(this,filterlist);

        }
        return fild;
    }

    class HolderDoctor extends RecyclerView.ViewHolder{

        private ImageView docimagetv,nextTV;
        private TextView docNameTV,docspecTV,docTimeTV,docdegtv;

        public HolderDoctor(@NonNull View itemView) {
            super(itemView);

            docimagetv=itemView.findViewById(R.id.docimagetv);
            nextTV=itemView.findViewById(R.id.nextTV);
            docNameTV=itemView.findViewById(R.id.docNameTV);
            docspecTV=itemView.findViewById(R.id.docspecTV);
            docTimeTV=itemView.findViewById(R.id.docTimeTV);
            docdegtv=itemView.findViewById(R.id.docdegtv);
        }
    }
}
