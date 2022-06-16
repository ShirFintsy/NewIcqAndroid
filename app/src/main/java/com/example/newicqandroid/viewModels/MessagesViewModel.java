package com.example.newicqandroid.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.newicqandroid.entities.Message;
import com.example.newicqandroid.repositories.MessagesRepository;

import java.util.List;

public class MessagesViewModel extends ViewModel {

    private MessagesRepository msgsRepo;
    private LiveData<List<Message>> messages;

    public MessagesViewModel(){
        msgsRepo = new MessagesRepository();
        messages = msgsRepo.getMsgs();
    }

    public LiveData<List<Message>> get(){return messages;}

    public void add(Message msg){
        //need to update the repository- the local db
        msgsRepo.addMsg(msg);

        //and need to update the display,
        // this list is observed and when its change adapter activates
        //List<MsgUsers> msgs = messages.getValue();
        //if(msgs != null){
          //  msgs.add(msg);
        //}

    }
}
