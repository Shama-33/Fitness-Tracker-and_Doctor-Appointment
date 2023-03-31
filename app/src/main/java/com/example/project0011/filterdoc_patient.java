package com.example.project0011;

import android.widget.Filter;

import java.util.ArrayList;

public class filterdoc_patient extends Filter {

    private doctorInfoAdapter_Patient adapter;
    private ArrayList<DoctorInfo>filterlist;

    public filterdoc_patient(doctorInfoAdapter_Patient adapter, ArrayList<DoctorInfo>filterlist)
    {
        this.adapter=adapter;
        this.filterlist=filterlist;
    }
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results= new FilterResults();
        if(constraint!=null && constraint.length()>0)
        {
            constraint=constraint.toString().toUpperCase();//make case insensitive for query
            ArrayList<DoctorInfo>filtermodel= new ArrayList<>();
            for(int i=0;i<filterlist.size();i++)
            {
                if (filterlist.get(i).getDocSpec().toUpperCase().contains(constraint) ||
                        filterlist.get(i).getDocName().toUpperCase().contains(constraint))
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
        adapter.doctorlist=(ArrayList<DoctorInfo>)results.values;
        adapter.notifyDataSetChanged();

    }
}
