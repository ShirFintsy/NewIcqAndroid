package com.example.newicqandroid.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.newicqandroid.AppLocalDB;
import com.example.newicqandroid.dao.ChatDao;
import com.example.newicqandroid.entities.Chat;
import com.example.newicqandroid.entities.Message;

import java.util.List;

public class ChatRepository {

    private ChatDao chatDao;

    public ChatRepository(Context context){
        AppLocalDB db = AppLocalDB.createAppDBInstance(context);
        chatDao = db.chatDao();
    }

    public String getOtherUser(String user, int idChat){
        Chat chat = chatDao.getChatById(idChat);
        if(chat.getIdUser1().equals(user)){
            return chat.getIdUser2();
        }

        return chat.getIdUser1();
    }
}
