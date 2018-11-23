package com.example.deepak.myfirebaseapplication.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import com.example.deepak.myfirebaseapplication.MainActivity;
import com.example.deepak.myfirebaseapplication.Model.UserAdapter;
import com.example.deepak.myfirebaseapplication.R;
import java.util.ArrayList;
import com.example.deepak.myfirebaseapplication.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class UserActivity extends AppCompatActivity implements AdapterView.OnClickListener {
    Button btn;
    ListView listView;
    ArrayList<User> users;
    UserAdapter adapter;
    User user;
    FirebaseFirestore db;
    FirebaseAuth Auth;
    FirebaseUser CurrentUser;
    String uid;

    void initviews(){

        users = new ArrayList<>();
        db=FirebaseFirestore.getInstance();
        listView = findViewById(R.id.listView);
        Auth= FirebaseAuth.getInstance();

    }
    void deleteFromDB(){

        String userId = user.id;
        Toast.makeText(UserActivity.this,"user"+userId,Toast.LENGTH_LONG).show();
        //String docId = document.getId();
       // FirebaseUser CurrentUser = Auth.getCurrentUser();


        db.collection("users").document(userId).delete().addOnCompleteListener(this, new OnCompleteListener<Void>() {
               // CurrentUser.delete().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UserActivity.this, "User Deleted",
                            Toast.LENGTH_SHORT).show();
                    users.remove(user);
                    adapter.notifyDataSetChanged();


                }else{
                    Toast.makeText(UserActivity.this, "User Not Deleted",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    void showOptions(){
        String[] options = {"View","Delete","Update","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(options, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                       // showInfo();
                        Intent intent= new Intent(UserActivity.this,UploadActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 1:
                        deleteFromDB();
                        break;
                    case 2:
                        Intent intent1= new Intent(UserActivity.this,RegisterActivity.class);
                        intent1.putExtra("keyuser",  user);
                        startActivityForResult(intent1,101);

                        break;
                    case 3:
                        //finish();
                        break;
                }
            }
        });
        builder.create().show();
    }
    void fetch() {
        users = new ArrayList<>();
        db.collection("users").get().addOnCompleteListener(this, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        User user = document.toObject(User.class);
                        Log.i("users",""+user.toString());
                        users.add(user);
                        adapter = new UserAdapter(UserActivity.this,R.layout.list_item,users);
                        listView.setAdapter(adapter);

                    }
                } else {

                }

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initviews();
        fetch();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 user = users.get(position);
                 Toast.makeText(UserActivity.this,"user"+position,Toast.LENGTH_LONG).show();
                 uid = user.id;
                 Log.i("user",""+user.toString());
                showOptions();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,101,0,"Logout");
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == 101){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(UserActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
