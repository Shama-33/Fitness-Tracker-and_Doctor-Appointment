package com.example.project0011;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getSupportActionBar().hide();

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        downloadAllData();

    }

    private void downloadAllData(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = user.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Data").child(uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<DataPlus> allData = new ArrayList<>();

                if(snapshot.exists()){
                    for(DataSnapshot ds : snapshot.getChildren()){

                        Data data = ds.getValue(Data.class);
                        String date = ds.getKey();
                        if(data == null) continue;
                        DataPlus plus = new DataPlus(date,data);
                        allData.add(plus);
                    }
                }

                showData(allData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void showData(ArrayList<DataPlus> allData){
        RvAdapter adapter = new RvAdapter(allData);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }

}
