package com.example.project0011;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    private final ArrayList<DataPlus> allData;

    public RvAdapter(ArrayList<DataPlus> allData) {
        this.allData = allData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_day_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DecimalFormat decimalFormat=new DecimalFormat("#.##");
        DataPlus data = allData.get(position);
        holder.tvStep.setText("Steps : " +data.getSSteps());
        holder.tvCalorie.setText("Calories : "+data.getSCalorie());
        holder.tvDistance.setText(("Distance : "+String.valueOf(decimalFormat.format(data.getDistance()))));
       // holder.tvDistance.setText("Distance : "+data.getSDistance());
        holder.tvDate.setText(data.getDate());
    }

    @Override
    public int getItemCount() {
        return allData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvStep, tvCalorie, tvDistance, tvDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvStep = itemView.findViewById(R.id.tvStep);
            tvCalorie = itemView.findViewById(R.id.tvCalorie);
            tvDistance = itemView.findViewById(R.id.tvDistance);
        }
    }

}
