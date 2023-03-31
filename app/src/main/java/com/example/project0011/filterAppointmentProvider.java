package com.example.project0011;

import android.content.Context;
import android.widget.Filter;

import java.util.ArrayList;

public class filterAppointmentProvider extends Filter {

    private AdapterAppointmentProvider adapter;
    private ArrayList<ModelAppointmentUser>filterlist;

    public filterAppointmentProvider(AdapterAppointmentProvider adapter, ArrayList<ModelAppointmentUser> filterlist) {
        this.adapter = adapter;
        this.filterlist = filterlist;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {



        FilterResults results= new FilterResults();
        if(constraint!=null && constraint.length()>0)
        {
            constraint=constraint.toString().toUpperCase();//make case insensitive for query
            ArrayList<ModelAppointmentUser>filtermodel= new ArrayList<>();
            for(int i=0;i<filterlist.size();i++)
            {
                if (filterlist.get(i).getAppstatus().toUpperCase().contains(constraint) ||
                        filterlist.get(i).getDocname().toUpperCase().contains(constraint))
                {
                    filtermodel.add(filterlist.get(i));
                }

            }
            results.count= filtermodel.size();
            results.values=filtermodel;
        }
        else
        {
            results.count= filterlist.size();
            results.values=filterlist;

        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.modellist=(ArrayList<ModelAppointmentUser>)results.values;
        adapter.notifyDataSetChanged();

    }
}
