package com.example.newicqandroid.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String idUser;
    private String name;
    private String password;
    private String image;
    private String server;
    private String last;
    private String lastdate;

    public User(String idUser, String name, String server){
        this.idUser = idUser;
        this.name = name;
        this.server = server;
        password = null;
        image = null;
        last = null;
        lastdate = null;
    }

    //todo: maybe will be deleted- just for now
    public User(String username){
        this.idUser = username;
    }

    public String getPassword() {
        return password;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getServer() {
        return server;
    }

    public String getLast() {
        return last;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }
}
