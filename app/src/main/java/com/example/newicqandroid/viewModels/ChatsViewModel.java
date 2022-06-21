package com.example.newicqandroid.viewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.newicqandroid.entities.Chat;
import com.example.newicqandroid.entities.Message;
import com.example.newicqandroid.repositories.ChatRepository;

import java.util.List;

public class ChatsViewModel extends ViewModel {

    private ChatRepository chatRepository;
    private LiveData<Chat> chats;

    public ChatsViewModel(){

    }

    public void init(Context context){
        chatRepository = new ChatRepository(context);
    }
    public LiveData<Chat> getChats() {
        return chats;
    }
    // todo: maybe delete if there is no need
    public String getOtherUser(String user, int idChat){
        return chatRepository.getOtherUser(user, idChat);
    }
}
