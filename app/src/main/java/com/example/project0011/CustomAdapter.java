package com.example.project0011;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Data> {

    private Activity context;
    private List<Data> Datalist;



    public CustomAdapter( Activity context, List<Data> Datalist) {
        super(context, R.layout.samplelayout, Datalist);
        this.context=context;
        this.Datalist=Datalist;
    }


    @NonNull
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater=context.getLayoutInflater();
        View v=layoutInflater.inflate(R.layout.samplelayout,null,true);

        Data data=Datalist.get(position);

        TextView step,cal,dis,date;
        step= v.findViewById(R.id.stepp);
        cal= v.findViewById(R.id.calll);
        date= v.findViewById(R.id.datee);
        dis= v.findViewById(R.id.diss);

//        cal.setText("Calories: "+data.getCalorie());
//        dis.setText("Distance: "+data.getDistance());
//        step.setText("Steps : "+data.getSteps());
        return v;
    }
}
