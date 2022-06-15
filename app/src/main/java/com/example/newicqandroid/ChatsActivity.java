package com.example.newicqandroid;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newicqandroid.databinding.ActivityChatsBinding;
import com.example.newicqandroid.databinding.ActivityLoginBinding;

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
    }
}
