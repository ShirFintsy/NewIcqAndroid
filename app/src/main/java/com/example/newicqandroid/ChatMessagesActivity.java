package com.example.newicqandroid;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.newicqandroid.adapters.MessagesAdapter;
import com.example.newicqandroid.api.ApiManager;
import com.example.newicqandroid.databinding.ActivityChatMessagesBinding;
import com.example.newicqandroid.databinding.ActivityLoginBinding;
import com.example.newicqandroid.entities.Chat;
import com.example.newicqandroid.entities.Message;
import com.example.newicqandroid.entities.TransferMessage;
import com.example.newicqandroid.entities.User;
import com.example.newicqandroid.entities.infoForIntent;
import com.example.newicqandroid.viewModels.MessagesViewModel;

public class ChatMessagesActivity extends AppCompatActivity {

    private MessagesViewModel msgsViewModel;
    private ActivityChatMessagesBinding binding;

    private String connectedUser;
    private String otherUser;
    private int idChat;
    private String server;
    private ApiManager apiManager;
    private String gotNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatMessagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        gotNotification = intent.getExtras().getString("notification");
        connectedUser = intent.getExtras().getString("username");
        otherUser = intent.getExtras().getString("otherUser");


        idChat = parseInt();


        if (gotNotification != null) { // arrived to this activity from a notification
            onReceivedMsg(gotNotification);
        }
        server = intent.getExtras().getString("server");

        if (server == null) {
            server = "http://10.0.2.2:5067/api/";
        }
        apiManager = new ApiManager(server);

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
        //binding.profilePic.setImageBitmap(msgsViewModel.getProfilePic(otherUser));
    }

    public void onSendMsg(View v){
        String msgText = binding.textSend.getText().toString();
        //send message oly if its not empty
        if(!msgText.equals("")) {
            Message msg = new Message(msgText, connectedUser, otherUser, idChat);

            msgsViewModel.add(msg);
            TransferMessage t = new TransferMessage(connectedUser, otherUser, msgText);
            apiManager.sendMessage(t);
            binding.textSend.setText("");
        }
    }

    public void onReceivedMsg(String content){
        //send message oly if its not empty
        if(!content.equals("")) {
            Message msg = new Message(content, otherUser, connectedUser, idChat);

            msgsViewModel.add(msg);
            //TransferMessage t = new TransferMessage(otherUser, connectedUser, content);
            //apiManager.sendMessage(t);
            //binding.textSend.setText("");
        }
    }
}