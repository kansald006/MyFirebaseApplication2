package com.example.deepak.myfirebaseapplication.ui;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

import com.example.deepak.myfirebaseapplication.MainActivity;
import com.example.deepak.myfirebaseapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
       FirebaseAuth Auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Auth= FirebaseAuth.getInstance();
        FirebaseUser user= Auth.getCurrentUser();
        if(user!=null){
            handler.sendEmptyMessageDelayed(201,5000);

        }else{
            handler.sendEmptyMessageDelayed(101,5000);
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==101){
                Intent intent= new Intent(SplashActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();

            }else if(msg.what==201){
                Intent intent= new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        }
    };
}
