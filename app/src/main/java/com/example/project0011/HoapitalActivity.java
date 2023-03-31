package com.example.project0011;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class HoapitalActivity extends AppCompatActivity {

    BottomNavigationView bot_nav_view;
    private float x1,y1,x2,y2;
    private Button docappbut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoapital);

        this.setTitle("");


        bot_nav_view=findViewById(R.id.bot_nav_view_hos);
        docappbut=findViewById(R.id.docappbut);

        docappbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ViewHospitalActivity.class);
                startActivity(intent);
            }
        });

        bot_nav_view.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.homemenu:
                        Intent intent = new Intent(getApplicationContext(),IndexActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.hospitalmenu:
                        //getSupportFragmentManager().beginTransaction().replace(androidx.fragment.R.id.fragment_container_view_tag,HospitalFragment).commit();

                        return true;

                }
                return false;
            }
        });

    }






    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1>x2){
                    Intent i = new Intent(HoapitalActivity.this, IndexActivity.class);
                    startActivity(i);
                }

//                else if(x1 >x2){
//                Intent i = new Intent(MainActivity.this, SwipeRight.class);
//                startActivity(i);
//            }
                break;
        }
        return false;
    }



    //for sign out
//    @Override
//    public boolean onCreateOptionsMenu (Menu menu)
//    {
//        getMenuInflater().inflate(R.menu.content_menu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId()==R.id.SignOutMenuId)
//        {
//            FirebaseAuth.getInstance().signOut();
//            finish();
//            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
//            startActivity(intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }



    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.logoutplusprofilemenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logoutmenuid)
        {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.profilemenuid)
        {
            Intent intent = new Intent(getApplicationContext(),UserProfileActivity.class);
            startActivity(intent);


        }
        return super.onOptionsItemSelected(item);
    }
}