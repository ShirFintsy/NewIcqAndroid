package com.example.newicqandroid;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
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
import com.example.newicqandroid.repositories.ChatRepository;
import com.example.newicqandroid.viewModels.MessagesViewModel;

public class ChatMessagesActivity extends AppCompatActivity {

    private MessagesViewModel msgsViewModel;
    private ActivityChatMessagesBinding binding;

    private String connectedUser;
    private String otherUser;
    private int idChat;
    private String server;
    private ApiManager apiManager;

    private ChatRepository chatRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatMessagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatRepository = new ChatRepository(getApplicationContext());

        Intent intent = getIntent();
        connectedUser = intent.getExtras().getString("user");
        otherUser = intent.getExtras().getString("otherUser");
        String msg =null;
        msg = intent.getExtras().getString("msg");

        Chat chat = chatRepository.findChatByUsers(connectedUser, otherUser);

        if(chat == null){
            chat = chatRepository.insertChat(new Chat(connectedUser, otherUser));
        }
        idChat = chat.getIdChat();
        server = intent.getExtras().getString("server");
        if (server == null) {
            server = "http://10.0.2.2:5067/api/";
        }


        apiManager = new ApiManager(server);

        msgsViewModel = new ViewModelProvider(this).get(MessagesViewModel.class);
        msgsViewModel.init(getApplicationContext(), idChat);

        RecyclerView msgsRecyclerView = binding.msgsRecyclerView;
        MessagesAdapter msgsAdapter = new MessagesAdapter(this, connectedUser);

        msgsRecyclerView.setAdapter(msgsAdapter);
        msgsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        msgsViewModel.get().observe(this, msgsAdapter::setMsgs);
        if (msg != null) {
            onReceivedMsg(msg);
        }
        binding.sendBtn.setOnClickListener(this::onSendMsg);
        binding.displayName.setText(msgsViewModel.getDisplayName(otherUser));
        setProfile();
    }

    public void setProfile(){
        Bitmap profile = msgsViewModel.getProfilePic(connectedUser);
        if(profile == null){
            binding.profilePic.setImageResource(R.drawable.default_picture);
        }else{
            binding.profilePic.setImageBitmap(profile);
        }
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
        }
    }
}