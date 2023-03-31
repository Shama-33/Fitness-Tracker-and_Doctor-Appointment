package com.example.project0011;

import android.widget.Filter;

import java.util.ArrayList;

public class filterhos extends Filter {



    private AdapterHospital adapter;
    private ArrayList<HospitalModel>filterlist;

    public filterhos(AdapterHospital adapter,ArrayList<HospitalModel>filterlist)
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
            ArrayList<HospitalModel>filtermodel= new ArrayList<>();
            for(int i=0;i<filterlist.size();i++)
            {
                if (filterlist.get(i).getHospitalCity().toUpperCase().contains(constraint) ||
                        filterlist.get(i).getHospitalName().toUpperCase().contains(constraint)||
                        filterlist.get(i).getHospitalArea().toUpperCase().contains(constraint))
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


       adapter.hospitalList=(ArrayList<HospitalModel>)results.values;
        adapter.notifyDataSetChanged();

    }
}
