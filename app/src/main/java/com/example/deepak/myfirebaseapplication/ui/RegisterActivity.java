package com.example.deepak.myfirebaseapplication.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.deepak.myfirebaseapplication.Model.User;
import com.example.deepak.myfirebaseapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etxtName, etxtEmail,etxtPassword;
    Button btnregister;
    User user;
    FirebaseAuth Auth;

    void initViews(){
        etxtName= findViewById(R.id.editTextName);
        etxtEmail=findViewById(R.id.editTextEmail);
        etxtPassword=findViewById(R.id.editTextPassword);
        btnregister=findViewById(R.id.button);
        user=new User();
        btnregister.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        Auth= FirebaseAuth.getInstance();
    }

    void InsertDataFirebase(){
        Auth.createUserWithEmailAndPassword(user.email,user.password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Authentication ok.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = Auth.getCurrentUser();


                        }else{
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        user.name= etxtName.getText().toString();
        user.email= etxtEmail.getText().toString();
        user.password=etxtPassword.getText().toString();
        InsertDataFirebase();
    }
}
