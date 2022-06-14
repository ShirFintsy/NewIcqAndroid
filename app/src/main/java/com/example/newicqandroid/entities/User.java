package com.example.newicqandroid.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    private String Id;
    private String Name;
    private String Password;
    private String Image;
    private String Server;
    private String last;
    private String lastdate;

    public User(String id, String name, String server){
        Id = id;
        Name = name;
        Server = server;
        Password = null;
        Image = null;
        last = null;
        lastdate = null;
    }

    public String getPassword() {
        return Password;
    }


}
