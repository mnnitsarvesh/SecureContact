package com.example.sarvesh.secure_contact;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class ContactActivity extends AppCompatActivity {


    private StorageReference mStorageRef;
    private TextInputEditText mName,mNumber;
    private Button mButton;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        mName=findViewById(R.id.name);
        mNumber=findViewById(R.id.number);
        mButton=findViewById(R.id.submit);
        mProgress=new ProgressDialog(this);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProgress.setTitle("Create Account");
                mProgress.setMessage("please wait");
                mProgress.setCanceledOnTouchOutside(false);
                mProgress.show();


                String name=mName.getText().toString();
                String number=mNumber.getText().toString();

                HashMap<String,String> user=new HashMap<>();
                user.put("Name",name);
                user.put("Number",number);

                FirebaseUser current_user= FirebaseAuth.getInstance().getCurrentUser();
                String uid=current_user.getUid();
                mDatabase= FirebaseDatabase.getInstance().getReference().child("User").child(uid);
                mDatabase.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            mProgress.dismiss();
                            Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(ContactActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            mProgress.hide();
                            Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}


