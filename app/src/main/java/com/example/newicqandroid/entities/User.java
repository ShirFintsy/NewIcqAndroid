package com.example.newicqandroid.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
    //@SerializedName("id")
    private String Id;
    //@SerializedName("name")
    private String Name;
    //@SerializedName("password")
    private String Password;
    //@SerializedName("image")
    private String Image;
    //@SerializedName("server")
    private String Server;
    //@SerializedName("last")
    private String last;
    //@SerializedName("lastdate")
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
    public User(String id, String name, String password, String image){
        this.Id = id;
        this.Name = name;
        this.Password = password;
        this.Image = image;
        //todo: from settings get server url
        Server = "localhost:5067";
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

    public Bitmap getDrawableImage() throws java.net.MalformedURLException, java.io.IOException {
        String url = getImage();
        if (url == null)
            return null;
        HttpURLConnection connection = (HttpURLConnection)new URL(url) .openConnection();
        connection.setRequestProperty("User-agent","Mozilla/4.0");

        connection.connect();
        InputStream input = connection.getInputStream();

        return BitmapFactory.decodeStream(input);
    }

    public Bitmap decodeImg(){
        String imgStr = getImage();
        if (imgStr == null)
            return null;
        byte[] decodedString = Base64.decode(imgStr, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return bitmap;
    }
}
