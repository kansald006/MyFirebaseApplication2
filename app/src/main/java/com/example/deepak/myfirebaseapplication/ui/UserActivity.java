package com.example.deepak.myfirebaseapplication.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.deepak.myfirebaseapplication.MainActivity;
import com.example.deepak.myfirebaseapplication.R;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        btn= findViewById(R.id.buttonlogout);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(UserActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
