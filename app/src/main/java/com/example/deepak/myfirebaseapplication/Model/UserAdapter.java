package com.example.deepak.myfirebaseapplication.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import android.view.View;
import android.view.ViewGroup;
import com.example.deepak.myfirebaseapplication.R;


public class UserAdapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<User> objects;
    public UserAdapter(Context context, int resource, ArrayList<User> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        view = LayoutInflater.from(context).inflate(resource,parent,false);
        // view is pointing to the resource -> R.layout.list_item

        TextView txtName = view.findViewById(R.id.textViewName);
        TextView txtEmail = view.findViewById(R.id.textViewEmail);
        User user = objects.get(position);

        txtName.setText(user.name);
        txtEmail.setText(user.email);
        return view;
    }
}
