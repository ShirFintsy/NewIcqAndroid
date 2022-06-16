package com.example.newicqandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.newicqandroid.databinding.ActivityChatsBinding;
import com.example.newicqandroid.registerActivity.RegisterActivity;

public class ChatsActivity extends AppCompatActivity {

    private ActivityChatsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String username = intent.getExtras().getString("username");
        //binding.currentUser.setText(username);

        // set listener to the add chat floating button
        binding.addChat.setOnClickListener(this::addChat);

        // todo: this id temporary button- in the future will be changed to the chat button
        binding.tmpButton.setOnClickListener(this::goToChat);
    }

    private void goToChat(View v){

        //todo: will be changed
        Intent intent = new Intent(getApplicationContext(), ChatMessagesActivity.class);
        intent.putExtra("username", "rotem");
        intent.putExtra("chatId", "1");
        startActivity(intent);
    }

    private void addChat(View v){

    }
}