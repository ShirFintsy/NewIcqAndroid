package com.example.newicqandroid.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newicqandroid.entities.Message;
import com.example.newicqandroid.entities.User;

import java.util.LinkedList;
import java.util.List;

public class MessagesRepository {

    private MessagesListData msgsListData;

    public MessagesRepository(){
        msgsListData = new MessagesListData();
    }
    public LiveData<List<Message>> getMsgs(){
        return msgsListData;
    }

    public void addMsg(Message msg){
        msgsListData.add(msg);

    }

    class MessagesListData extends MutableLiveData<List<Message>>{
        public MessagesListData(){
            super();

            //todo: will be changed!
            User shir =  new User("shir");
            User rotem =  new User("rotem");
            List<Message> msgs = new LinkedList<>();
            msgs.add(new Message("hi", "rotem", "shir", 1));
            msgs.add(new Message("by", "shir", "rotem", 1));

            setValue(msgs);

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
