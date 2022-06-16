package com.example.newicqandroid;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.newicqandroid.dao.UserDao;
import com.example.newicqandroid.dao.MessageDao;
import com.example.newicqandroid.dao.ChatDao;
import com.example.newicqandroid.entities.Chat;
import com.example.newicqandroid.entities.Message;
import com.example.newicqandroid.entities.User;

@Database(entities = {User.class, Message.class, Chat.class}, version = 2)
public abstract class AppLocalDB extends RoomDatabase{
    public abstract UserDao userDao();
    public abstract MessageDao messageDao();
    public abstract ChatDao chatDao();
}
