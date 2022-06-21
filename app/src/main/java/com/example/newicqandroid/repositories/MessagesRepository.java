package com.example.newicqandroid.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newicqandroid.AppLocalDB;
import com.example.newicqandroid.dao.MessageDao;
import com.example.newicqandroid.entities.Message;
import com.example.newicqandroid.entities.User;

import java.util.LinkedList;
import java.util.List;

public class MessagesRepository {

    private MessagesListData msgsListData;
    private MessageDao msgDao;

    public MessagesRepository(Context context, int idChat){
        AppLocalDB db = AppLocalDB.createAppDBInstance(context);
        msgDao = db.messageDao();
        msgsListData = new MessagesListData();

        List<Message> currMsgs = msgDao.getMsgsByChat(idChat);
        msgsListData.setValue(currMsgs);
    }

    public LiveData<List<Message>> getMsgs(){
        return msgsListData;
    }

    public void addMsg(Message msg){
        msgsListData.add(msg);
        //update local db
        msgDao.Insert(msg);
        //todo: in the future add update to api
    }


    class MessagesListData extends MutableLiveData<List<Message>>{
        public MessagesListData(){
            super();
            setValue(new LinkedList<>());
            //todo: will be deleted!
            /*
            User shir =  new User("shir");
            User rotem =  new User("rotem");
            List<Message> msgs = new LinkedList<>();
            msgs.add(new Message("hi", "rotem", "shir", 1));
            msgs.add(new Message("by", "shir", "rotem", 1));

            setValue(msgs);*/

        }

        public void add(Message msg){
            List<Message> msgs = getValue();
            if(msgs != null){
                msgs.add(msg);
            }

            setValue(msgs);
        }
    }

}
