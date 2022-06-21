package com.example.newicqandroid.repositories;

import android.content.Context;

import com.example.newicqandroid.AppLocalDB;
import com.example.newicqandroid.dao.UserDao;
import com.example.newicqandroid.entities.User;

public class UserRepository {
    private UserDao userDao;

    public UserRepository(Context context){
        AppLocalDB db = AppLocalDB.createAppDBInstance(context);
        userDao = db.userDao();
    }

    public String getDisplayName(String username){
        User user = userDao.getByUsername(username);
        if(user != null){
            return user.getName();
        }
        return null;
    }

    public User getUser(String username){
        return userDao.getByUsername(username);
    }
}


