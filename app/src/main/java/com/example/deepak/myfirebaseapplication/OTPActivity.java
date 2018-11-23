package com.example.deepak.myfirebaseapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.FirebaseTooManyRequestsException;

import java.util.concurrent.TimeUnit;
import android.content.Intent;

public class OTPActivity extends AppCompatActivity {
    EditText editText;
    Button btn;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        editText = findViewById(R.id.editText);
        btn = findViewById(R.id.buttonotp);
       // auth.useAppLanguage();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = editText.getText().toString().trim();

                if (mobile.isEmpty() || mobile.length() < 10) {
                    editText.setError("Enter a valid mobile");
                    editText.requestFocus();
                    return;
                }

                Intent intent = new Intent(OTPActivity.this, VerifyActivity.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);
            }
        });
    }}


