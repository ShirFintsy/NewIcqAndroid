package com.example.newicqandroid.repositories;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.example.newicqandroid.AppLocalDB;
import com.example.newicqandroid.Utils;
import com.example.newicqandroid.api.ApiManager;
import com.example.newicqandroid.dao.UserDao;
import com.example.newicqandroid.entities.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class UserRepository {
    private UserDao userDao;
    private ApiManager api;
    private Context context;

    public UserRepository(Context context){
        AppLocalDB db = AppLocalDB.createAppDBInstance(context);
        userDao = db.userDao();
        this.context = context;
    }

    public String getDisplayName(String username){
        User user = userDao.getByUsername(username);
        if(user != null){
            return user.getName();
        }
        return null;
    }

    public void addUser(User user){
        if(userDao.getByUsername(user.getId()) == null){
            userDao.Insert(user);
        }
    }

    public User getUser(String username){
        return userDao.getByUsername(username);
    }

    public Bitmap getProfilePic(String username) {
        String img = userDao.getByUsername(username).getImage();
        if(img.equals("default_picture.jpg")){
            return null;
        } else if(img.startsWith("https://")){
            try {
                URL url = new URL(img);
                InputStream content = (InputStream) url.getContent();
                Drawable d = Drawable.createFromStream(content, "src");

                return Utils.drawableToBitmap(d);
            }catch (Exception e){
                return null;
            }
        }
        return Utils.decodeImg(img);
    }
}


