package com.example.deepak.myfirebaseapplication.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

import com.example.deepak.myfirebaseapplication.MainActivity;
import com.example.deepak.myfirebaseapplication.Model.User;
import com.example.deepak.myfirebaseapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.widget.Toast;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etxtName, etxtEmail,etxtPassword;
    Button btnregister,btnLogin;
    User user;
    FirebaseAuth Auth;
    FirebaseFirestore db;
    ArrayList<User> users;
    Boolean update;

    void initViews(){
        etxtName= findViewById(R.id.editTextName);
        etxtEmail=findViewById(R.id.editTextEmail);
        etxtPassword=findViewById(R.id.editTextPassword);
        btnregister=findViewById(R.id.button);
        btnLogin=findViewById(R.id.buttonLogin);
        user=new User();
        btnregister.setOnClickListener(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        Auth= FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        Intent rcv= getIntent();
        update=rcv.hasExtra("keyuser");
        if(update){
            user= (User) rcv.getSerializableExtra("keyuser");
            etxtName.setText(user.name);
            etxtEmail.setText(user.email);
            etxtPassword.setText(user.password);
            etxtEmail.setFocusable(false);
            etxtPassword.setFocusable(false);
            btnregister.setText("Update User");
            btnregister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user.name= etxtName.getText().toString();
                    user.email= etxtEmail.getText().toString();
                    user.password=etxtPassword.getText().toString();

                    saveUpdateUser();
                }
            });

        }else{

        }

    }
    void saveUpdateUser() {

        String uid = user.id;
        db.collection("users").document(uid).set(user)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "User Added in Firestore", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    void InsertDataFirebase(){
        //String userId = Auth.getCurrentUser().getUid();
       /*  db.collection("users").document(userId).set(user).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Authentication ok.",
                            Toast.LENGTH_SHORT).show();
                    FirebaseUser user = Auth.getCurrentUser();
                    saveUser();

                }else{
                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });*/


        Auth.createUserWithEmailAndPassword(user.email,user.password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Authentication sucessfull.",
                            Toast.LENGTH_SHORT).show();

                    saveUser();

                }else{
                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void saveUser(){

        user.id=Auth.getCurrentUser().getUid();
        db.collection("users").document(user.id).set(user).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "User Added in Firestore", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
            }
        }
        });
       /* db.collection("users").add(user).addOnSuccessListener(this,new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String documentId = documentReference.getId();
            }
        }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


            }
        });*/
    }

  void fetch(){
      users = new ArrayList<>();
        db.collection("users").get().addOnCompleteListener(this, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        User user = document.toObject(User.class);
                        users.add(user);
                    }
                } else {

                }

            }
        });
  }
    @Override
    public void onClick(View v) {user.name= etxtName.getText().toString();
        user.email= etxtEmail.getText().toString();
        user.password=etxtPassword.getText().toString();

        InsertDataFirebase();
    }
}
