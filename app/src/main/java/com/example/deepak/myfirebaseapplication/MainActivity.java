package com.example.deepak.myfirebaseapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;

import com.example.deepak.myfirebaseapplication.Model.User;
import com.example.deepak.myfirebaseapplication.ui.RegisterActivity;
import com.example.deepak.myfirebaseapplication.ui.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etxtUser, etxtPass;
    Button btnlogin,btnRegister,btnotp;
    FirebaseAuth Auth;
    User user;

    void initViews(){
        etxtUser=findViewById(R.id.editTextUser);
        etxtPass=findViewById(R.id.editTextPass);
        btnlogin=findViewById(R.id.buttonLogin);
        btnRegister=findViewById(R.id.buttonRegister);
        btnlogin.setOnClickListener(this);
        Auth = FirebaseAuth.getInstance();
       btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
                }
        });
       btnotp=findViewById(R.id.buttonOtpSign);
       btnotp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent= new Intent(MainActivity.this,OTPActivity.class);
               startActivity(intent);
               finish();
           }
       });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }
    void LoginWithFirebase(){
        Auth.signInWithEmailAndPassword(etxtUser.getText().toString(),etxtPass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Authentication Successfull",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent= new Intent(MainActivity.this,UserActivity.class);
                            startActivity(intent);
                            finish();
                        }else{

                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        }
                });

    }

    @Override
    public void onClick(View v) {
        LoginWithFirebase();
    }
}
