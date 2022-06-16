package com.example.newicqandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.example.newicqandroid.adapters.UsersListAdapter;
import com.example.newicqandroid.databinding.ActivityChatsBinding;
import com.example.newicqandroid.databinding.ActivityLoginBinding;
import com.example.newicqandroid.entities.User;
import com.example.newicqandroid.entities.Chat;
import com.example.newicqandroid.registerActivity.RegisterActivity;

public class ChatsActivity extends AppCompatActivity {

    private ActivityChatsBinding binding;
    private String connectedUser = "rotem";
    private String otherUser = "shir";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String username = intent.getExtras().getString("username");
        binding.username.setText(username);

        RecyclerView usersList = binding.userChatsList;
        UsersListAdapter usersListAdapter = new UsersListAdapter(this, connectedUser);
        usersList.setAdapter(usersListAdapter);
        usersList.setLayoutManager(new LinearLayoutManager(this));

        usersListAdapter.addChat(new User("hilla"));
//        usersListAdapter.addChat(new User("tomer"));
//        usersListAdapter.addChat(new User("rommi"));

        // set listener to the add chat floating button
        binding.addChat.setOnClickListener(this::addChat);

        // todo: this id temporary button- in the future will be changed to the chat button
        binding.tmpButton.setOnClickListener(this::goToChat);
    }

    private void goToChat(View v){
        //todo: will be deleted- temporary for checking the database
        AppLocalDB db = AppLocalDB.createAppDBInstance(getApplicationContext());
        if(db.chatDao().getChatByUsers("rotem", "shir") == null) {
            db.chatDao().Insert(new Chat("rotem", "shir"));
        }
        Chat c = db.chatDao().getChatByUsers("rotem", "shir");
        //todo: will be changed
        Intent intent = new Intent(getApplicationContext(), ChatMessagesActivity.class);
        intent.putExtra("username", "rotem");
        intent.putExtra("idChat", c.getIdChat());
        startActivity(intent);
    }

    private void addChat(View v){
    }
}