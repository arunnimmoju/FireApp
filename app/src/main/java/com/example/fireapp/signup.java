package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {
    EditText mEmail,mPassword,mName;
    Button mSignupBtn,mLoginbtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mName= findViewById(R.id.name);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mSignupBtn = findViewById(R.id.signupbtn);
        mLoginbtn= findViewById(R.id.loginbtn);


        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);

        if(fAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(),loginTo.class));
            finish();
        }


        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    mPassword.setError("Password is Required");
                    return;
                }
                if(password.length()<6){
                    mPassword.setError("Password must be greater than 6 characters");
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);

                // registering the user

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(signup.this,"User Created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),loginTo.class));
                        }else
                        {
                            Toast.makeText(signup.this,"Error"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


mLoginbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(),loginTo.class));
    }
});



    }
}
