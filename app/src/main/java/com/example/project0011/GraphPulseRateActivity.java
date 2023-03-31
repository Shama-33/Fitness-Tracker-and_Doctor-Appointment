package com.example.project0011;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphPulseRateActivity extends AppCompatActivity {


    GraphView graphView;
    LineGraphSeries lineGraphSeries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_pulse_rate);

        getSupportActionBar().hide();

        graphView= (GraphView) findViewById(R.id.graphpulserate);
        lineGraphSeries=new LineGraphSeries();
        graphView.addSeries(lineGraphSeries);
    }


    @Override
    protected void onStart() {
        super.onStart();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserDataAll").child("PulseRate");
        databaseReference.child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataPoint[] dp= new DataPoint[(int)snapshot.getChildrenCount()];
                int index=0,arr=1;
                for(DataSnapshot ds : snapshot.getChildren())
                {



                    graphView.getViewport().setXAxisBoundsManual(true);
                    graphView.getViewport().setMinX(1);
                    graphView.getViewport().setMaxX((int)snapshot.getChildrenCount());
//                    graphView.getViewport().setYAxisBoundsManual(true);
//                    graphView.getViewport().setMinY(0);
//                    graphView.getViewport().setMaxY(6);


                    graphView.getGridLabelRenderer().setNumHorizontalLabels((int)snapshot.getChildrenCount());
                    //graphView.getGridLabelRenderer().setNumVerticalLabels(7);


                    String s=ds.child("min").getValue().toString();
                    int p=Integer.parseInt(s);
                    //graph_steps g=ds.getValue(graph_steps.class);
                    // long i= Integer.parseInt(g.getDate());
                    dp[index]=new DataPoint(arr++,p);
                    index+=1;
                    //arr=arr+1;
                }
                lineGraphSeries.resetData(dp);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}