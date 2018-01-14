package com.example.sarvesh.secure_contact;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AccountActivity extends AppCompatActivity {

    private TextInputEditText mEmail,mPassword;
    private Button mLogin;
    private FirebaseAuth mAuth;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mEmail=findViewById(R.id.email);
        mPassword=findViewById(R.id.password);
        mLogin=findViewById(R.id.login);

        mAuth=FirebaseAuth.getInstance();
        mProgress=new ProgressDialog(this);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=mEmail.getText().toString();
                String password=mPassword.getText().toString();

                mProgress.setTitle("Login User");
                mProgress.setMessage("please wait");
                mProgress.setCanceledOnTouchOutside(false);
                mProgress.show();

                if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password))
                {
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                mProgress.dismiss();
                                Toast.makeText(getApplicationContext(),"Successfully Login",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(AccountActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                mProgress.hide();
                                Toast.makeText(getApplicationContext(),"Error in Login",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}

