package com.example.newicqandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.newicqandroid.adapters.MessagesAdapter;
import com.example.newicqandroid.databinding.ActivityChatMessagesBinding;
import com.example.newicqandroid.databinding.ActivityLoginBinding;
import com.example.newicqandroid.entities.Message;
import com.example.newicqandroid.entities.User;
import com.example.newicqandroid.viewModels.MessagesViewModel;

public class ChatMessagesActivity extends AppCompatActivity {

    private MessagesViewModel msgsViewModel;
    private ActivityChatMessagesBinding binding;

    //todo: only for now- will be change!
    private String connectedUser = "rotem";
    private String otherUser = "shir";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatMessagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        connectedUser = intent.getExtras().getString("username");


        msgsViewModel = new ViewModelProvider(this).get(MessagesViewModel.class);

        RecyclerView msgsRecyclerView = binding.msgsRecyclerView;

        MessagesAdapter msgsAdapter = new MessagesAdapter(this, connectedUser);

        msgsRecyclerView.setAdapter(msgsAdapter);
        msgsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        msgsViewModel.get().observe(this, msgsAdapter::setMsgs);

        binding.sendBtn.setOnClickListener(this::onSendMsg);


    }

    public void onSendMsg(View v){
        String msgText = binding.textSend.getText().toString();
        //send message oly if its not empty
        if(!msgText.equals("")) {
            User other = new User(otherUser);
            User connected = new User(connectedUser);
            Message msg = new Message(msgText, connectedUser, otherUser, 1);
            //MsgUsers msgUsers = new MsgUsers(msg, connected, other);

            msgsViewModel.add(msg);
            binding.textSend.setText("");
        }
    }
}