package com.example.newicqandroid.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newicqandroid.entities.Message;
import com.example.newicqandroid.entities.MsgUsers;
import com.example.newicqandroid.entities.User;

import java.util.LinkedList;
import java.util.List;

public class MessagesRepository {

    private MessagesListData msgsListData;

    public MessagesRepository(){
        msgsListData = new MessagesListData();
    }
    public LiveData<List<MsgUsers>> getMsgs(){
        return msgsListData;
    }

    public void addMsg(MsgUsers msg){
        msgsListData.add(msg);

    }

    class MessagesListData extends MutableLiveData<List<MsgUsers>>{
        public MessagesListData(){
            super();

            //todo: will be changed!
            User shir =  new User("shir");
            User rotem =  new User("rotem");
            List<MsgUsers> msgs = new LinkedList<>();
            msgs.add(new MsgUsers(new Message("hi"), rotem, shir));
            msgs.add(new MsgUsers(new Message("by"), shir, rotem));

            setValue(msgs);

        }

        public void add(MsgUsers msg){
            List<MsgUsers> msgs = getValue();
            if(msgs != null){
                msgs.add(msg);
            }

            setValue(msgs);
        }
    }

}
