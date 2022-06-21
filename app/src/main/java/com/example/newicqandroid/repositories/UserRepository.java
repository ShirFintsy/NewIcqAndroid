package com.example.newicqandroid.repositories;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.newicqandroid.AppLocalDB;
import com.example.newicqandroid.Utils;
import com.example.newicqandroid.api.ApiManager;
import com.example.newicqandroid.dao.UserDao;
import com.example.newicqandroid.entities.User;

import java.util.Objects;

public class UserRepository {
    private UserDao userDao;
    private ApiManager api;

    public UserRepository(Context context){
        AppLocalDB db = AppLocalDB.createAppDBInstance(context);
        userDao = db.userDao();
        api = new ApiManager();
    }

    public String getDisplayName(String username){
        User user = userDao.getByUsername(username);
        if(user != null){
            return user.getName();
        }
        return null;
    }

    public void addUser(User user){
        //todo: delete later
        if(userDao.getByUsername(user.getId()) == null){
            userDao.Insert(user);
        }

        api.addUser(user);
    }

    public User getUser(String username){
        return userDao.getByUsername(username);
    }

    public Bitmap getProfilePic(String username){
        String img = userDao.getByUsername(username).getImage();
        if(img.equals("defult_picture.jpg")){
            return BitmapFactory.decodeFile("app/src/main/res/drawable/default_picture.jpg");
        } else if(img.contains("https://")){
            //todo: not working for hhtp
            return BitmapFactory.decodeFile(img);
        }
        return Utils.decodeImg(img);
    }
}


