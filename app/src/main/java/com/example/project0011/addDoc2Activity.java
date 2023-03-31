package com.example.project0011;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Constants;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;

public class addDoc2Activity extends AppCompatActivity {

    private EditText docname,doctime,speciality,prof;
    private ImageView docphoto;
    private Button addinfo;
    String name,time,spec,deg;
    FirebaseAuth firebaseAuth;

    private static final int CAMERA_REQUEST_CODE=200;
    private static final int STORAGE_REQUEST_CODE=300;


    private static final int IMAGE_PICK_GALLERY_CODE=400;
    private static final int IMAGE_PICK_CAMERA_CODE=500;

    private String[] cameraPermissions;
    private String[] storagePermissions;

    private Uri image_uri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doc2);

        this.setTitle("  Add Doctor Information");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.acbar)));
        docname=findViewById(R.id.docname);
        doctime=findViewById(R.id.doctime);
        speciality=findViewById(R.id.speciality);
        prof=findViewById(R.id.prof);
        docphoto=findViewById(R.id.docphoto);
        addinfo=findViewById(R.id.addinfo);

        firebaseAuth=FirebaseAuth.getInstance();



        docphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(Build.VERSION.SDK_INT>=22)
                {
                    checkAndRequestforPermission();
                }
                else
                {
                    openGal();
                }




            }
        });
        docname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        doctime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        speciality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              SelectType();

            }
        });
        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        addinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputData();
                SaveData();

            }
        });


    }

    private void SelectType() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Doctor Type").setItems(DocTypes.types, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String C= DocTypes.types[which];
                speciality.setText(C);
                spec=C;


            }
        }).show();
    }

    private void openGal() {
        Intent intent =new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    private void checkAndRequestforPermission() {
        if(ContextCompat.checkSelfPermission(addDoc2Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(addDoc2Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(this, "Accept to grant Permission", Toast.LENGTH_SHORT).show();
            }
            else
            {
                ActivityCompat.requestPermissions(addDoc2Activity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE,
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        IMAGE_PICK_GALLERY_CODE);
            }
        }
        else
        {
            openGal();
        }

    }

    private void SaveData() {
        FirebaseUser user=firebaseAuth.getCurrentUser();
        String uid=user.getUid();
       final String timestamp=""+System.currentTimeMillis();
        if(image_uri==null)
        {
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("iD",""+timestamp);
            hashMap.put("docName",""+name);
            hashMap.put("docTime",""+time);
            hashMap.put("docSpec",""+spec);
            hashMap.put("docDeg",""+deg);
            hashMap.put("docPthoto","");
            hashMap.put("timeStamp",""+timestamp);
            hashMap.put("UID",""+uid);

            DatabaseReference dref= FirebaseDatabase.getInstance().getReference("DoctorInfo");
            dref.child(uid).child(timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(addDoc2Activity.this, "Information Added", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(addDoc2Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        else
        {
            String FilePathandName="doc/"+""+timestamp;
            StorageReference storageReference=FirebaseStorage.getInstance().getReference(FilePathandName);
            storageReference.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isSuccessful());

                        Uri downloadimageuri=uriTask.getResult();

                    if(uriTask.isSuccessful())
                    {





                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("iD",""+timestamp);
                        hashMap.put("docName",""+name);
                        hashMap.put("docTime",""+time);
                        hashMap.put("docSpec",""+spec);
                        hashMap.put("docDeg",""+deg);
                        hashMap.put("docPhoto",""+downloadimageuri);
                        hashMap.put("timeStamp",""+timestamp);
                        hashMap.put("UID",""+uid);

                        DatabaseReference dref= FirebaseDatabase.getInstance().getReference("DoctorInfo");
                        dref.child(uid).child(timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(addDoc2Activity.this, "Information Added", Toast.LENGTH_SHORT).show();
                                clearData();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(addDoc2Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(addDoc2Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void clearData() {
        docname.setText("");
        doctime.setText("");
        speciality.setText("");
        prof.setText("");
        docphoto.setImageResource(R.drawable.doctor);
        image_uri=null;

    }

    private void InputData() {
        /*
         private EditText docname,doctime,speciality,prof;
    private ImageView docphoto;
    private Button addinfo;
    String name,time,spec,deg;
         */
        name=docname.getText().toString().trim();
        time=doctime.getText().toString().trim();
        //spec=speciality.getText().toString().trim();
        deg=prof.getText().toString().trim();

        if(name.isEmpty())
        {
            docname.setError("Name Must be Entered");
            return;
        }
        if(time.isEmpty())
        {
            doctime.setError("time Must be Entered");
            return;
        }
        if(spec.isEmpty())
        {
            speciality.setError("Speciality Must be Entered");
            return;
        }
        if(deg.isEmpty())
        {
            prof.setError("Degree Must be Entered");
            return;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            if(requestCode==IMAGE_PICK_GALLERY_CODE && data!=null)
            {
                image_uri=data.getData();
                docphoto.setImageURI(data.getData());
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.content_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.SignOutMenuId)
        {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}