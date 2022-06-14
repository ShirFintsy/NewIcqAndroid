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

    //todo: maybe will be deleted- just for now
    public User(String name){
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }

    public String getServer() {
        return Server;
    }

    public String getLast() {
        return last;
    }

    public String getLastdate() {
        return lastdate;
    }

}
