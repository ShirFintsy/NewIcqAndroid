package com.example.newicqandroid;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newicqandroid.adapters.UsersListAdapter;
import com.example.newicqandroid.databinding.ActivityChatsBinding;
import com.example.newicqandroid.databinding.ActivityLoginBinding;
import com.example.newicqandroid.entities.User;

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
    }
}
