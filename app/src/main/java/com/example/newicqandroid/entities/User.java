package com.example.newicqandroid.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
//    private String Id;
//    private String Name;
//    private String Password;
//    private String Image;
//    private String last;
//    private String lastdate;
//    private String Server;
    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String Id;
    private String Name;
    private String Password;
    private String Image;
    private String Server;
    private String last;
    private String lastdate;

    public User(String Id, String name, String server){
        this.Id = Id;
        this.Name = name;
        this.Server = server;
        this.Password = null;
        this.Image = null;
        last = null;
        lastdate = null;
    }

    //todo: maybe will be deleted- just for now
    public User(String username){
        this.Id = username;
    }
    public User(){};

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

    public void setId(String id) {
        this.Id = id;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public void setServer(String server) {
        this.Server = server;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }
}
