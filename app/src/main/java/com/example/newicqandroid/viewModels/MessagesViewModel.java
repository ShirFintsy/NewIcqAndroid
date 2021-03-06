package com.example.newicqandroid.viewModels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.newicqandroid.entities.Message;
import com.example.newicqandroid.repositories.ChatRepository;
import com.example.newicqandroid.repositories.MessagesRepository;
import com.example.newicqandroid.repositories.UserRepository;

import java.util.List;

public class MessagesViewModel extends ViewModel {

    private ChatRepository chatRepository;
    private MessagesRepository msgsRepo;
    private UserRepository userRepository;
    private LiveData<List<Message>> messages;

    public MessagesViewModel(){

    }

    public void init(Context context, int idChat){
        msgsRepo = new MessagesRepository(context,idChat);
        chatRepository = new ChatRepository(context);
        userRepository = new UserRepository(context);
        messages = msgsRepo.getMsgs();
    }
    public LiveData<List<Message>> get(){return messages;}

    public void add(Message msg){
        //need to update the repository- the local db
        msgsRepo.addMsg(msg);

    }

    public String getOtherUser(String user, int idChat){
        return chatRepository.getOtherUser(user, idChat);
    }

    public String getDisplayName(String username){
        return userRepository.getDisplayName(username);
    }

    public Bitmap getProfilePic(String username){
        return userRepository.getProfilePic(username);
    }
}
