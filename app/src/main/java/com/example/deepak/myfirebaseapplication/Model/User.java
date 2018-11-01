package com.example.deepak.myfirebaseapplication.Model;

import java.io.Serializable;

public class User implements Serializable {
    public int id;
    public String name;
    public String email;
    public String password;


    public User(){

    }

    public User(int id,String name, String email, String password) {
        this.id= id;
        this.name = name;
        this.email = email;
        this.password=password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

