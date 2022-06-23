package com.example.newicqandroid;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.newicqandroid.dao.UserDao;
import com.example.newicqandroid.dao.MessageDao;
import com.example.newicqandroid.dao.ChatDao;
import com.example.newicqandroid.entities.Chat;
import com.example.newicqandroid.entities.Message;
import com.example.newicqandroid.entities.User;


@Database(entities = {User.class, Message.class, Chat.class}, version = 12)
public abstract class AppLocalDB extends RoomDatabase {
    private static AppLocalDB appDB = null;

    public abstract UserDao userDao();
    public abstract MessageDao messageDao();
    public abstract ChatDao chatDao();

    public static AppLocalDB createAppDBInstance(Context context) {
        if(appDB == null){
            appDB = Room.databaseBuilder(context.getApplicationContext(),
                    AppLocalDB.class, "newIcqDB")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build();
        }

        return appDB;
    }
}
