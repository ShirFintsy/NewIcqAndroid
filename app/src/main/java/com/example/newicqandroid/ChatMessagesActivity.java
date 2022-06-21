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

    private String connectedUser;
    private String otherUser;
    private int idChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatMessagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        connectedUser = intent.getExtras().getString("username");
        idChat = intent.getIntExtra("idChat", 0);

        msgsViewModel = new ViewModelProvider(this).get(MessagesViewModel.class);
        msgsViewModel.init(getApplicationContext(), idChat);

        otherUser = msgsViewModel.getOtherUser(connectedUser, idChat);

        RecyclerView msgsRecyclerView = binding.msgsRecyclerView;
        MessagesAdapter msgsAdapter = new MessagesAdapter(this, connectedUser);

        msgsRecyclerView.setAdapter(msgsAdapter);
        msgsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        msgsViewModel.get().observe(this, msgsAdapter::setMsgs);

        binding.sendBtn.setOnClickListener(this::onSendMsg);
        binding.displayName.setText(msgsViewModel.getDisplayName(otherUser));
    }

    public void onSendMsg(View v){
        String msgText = binding.textSend.getText().toString();
        //send message oly if its not empty
        if(!msgText.equals("")) {
            Message msg = new Message(msgText, connectedUser, otherUser, idChat);

            msgsViewModel.add(msg);
            binding.textSend.setText("");
        }
    }
}