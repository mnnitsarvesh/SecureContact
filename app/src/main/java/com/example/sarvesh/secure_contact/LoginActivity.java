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

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText mEmail,mPassword;
    private Button mButton,mAccount;

    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail=findViewById(R.id.email);
        mPassword=findViewById(R.id.password);
        mButton=findViewById(R.id.loginbtn);

        mAccount=findViewById(R.id.account);

        mAuth=FirebaseAuth.getInstance();
        mProgress=new ProgressDialog(this);



        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=mEmail.getText().toString();
                String password=mPassword.getText().toString();

                mProgress.setTitle("Create Account");
                mProgress.setMessage("please wait");
                mProgress.setCanceledOnTouchOutside(false);
                mProgress.show();

                if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password))
                {
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                mProgress.dismiss();
                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
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

        mAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(LoginActivity.this,AccountActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }


}

