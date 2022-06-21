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

    public Bitmap geyCodedImage() {
        try {
            URL url = new URL(getImage());
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return image;
        } catch(IOException e) {
            System.out.println(e);
            return null;
        }
    }

    public Drawable loadImageFromNetwork() {
        Drawable drawable = null;
        try {
            drawable = Drawable.createFromStream(
                    new URL(getImage()).openStream(), "image.jpg");
        } catch (IOException e) {
            Log.d("test", e.getMessage());
        }
        if (drawable == null) {
            Log.d("test", "null drawable");
        } else {
            Log.d("test", "not null drawable");
        }
        return drawable;
    }
}
