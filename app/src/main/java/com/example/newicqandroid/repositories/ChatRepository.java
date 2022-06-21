package com.example.newicqandroid.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.newicqandroid.AppLocalDB;
import com.example.newicqandroid.dao.ChatDao;
import com.example.newicqandroid.dao.MessageDao;
import com.example.newicqandroid.entities.Chat;
import com.example.newicqandroid.entities.Message;
import com.example.newicqandroid.entities.User;

import java.util.List;

public class ChatRepository {

    private ChatDao chatDao;
    private MessageDao msgDao;

    public ChatRepository(Context context){
        AppLocalDB db = AppLocalDB.createAppDBInstance(context);
        chatDao = db.chatDao();
        msgDao = db.messageDao();
    }

    public Chat findChatById(int chatID) {
        return chatDao.getChatById(chatID);
    }

    public Chat insertChat(Chat chat) {
        if(chatDao.getChatByUsers(chat.getIdUser1(), chat.getIdUser2()) == null)
            chatDao.Insert(chat);
        return chatDao.getChatByUsers(chat.getIdUser1(), chat.getIdUser2());
    }
    public String getOtherUser(String user, int idChat){
        Chat chat = chatDao.getChatById(idChat);
        if(chat.getIdUser1().equals(user)){
            return chat.getIdUser2();
        }

        return chat.getIdUser1();
    }
    public Message getLastMsgByChadId(int chatId) {
        List<Message> msgs = msgDao.getMsgsByChat(chatId);
        String time = "";
        Message last = new Message();
        for(Message msg: msgs) {
            if (msg.getCreated().compareTo(time)> 0) { // created is biger then time
                last = msg;
                time = msg.getCreated();
            }
        }
        return last;
        //return new Message("hi", "shir" , "rotem", chatId);
    }

    public List<Chat> getChatsByUser(String username) {
        return chatDao.getChatsByUser(username);
    }
}
