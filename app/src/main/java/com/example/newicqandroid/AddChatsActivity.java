package com.example.newicqandroid;

import androidx.appcompat.app.AppCompatActivity;
import com.example.newicqandroid.databinding.ActivityAddChatsBinding;
import com.example.newicqandroid.databinding.ActivityChatsBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddChatsActivity extends AppCompatActivity {
    private ActivityAddChatsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddChatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.addButton.setOnClickListener(view-> {
            String user = binding.addChatInput.getText().toString();
            Intent toIntent = new Intent(getApplicationContext(), ChatsActivity.class);
            toIntent.putExtra("username", user);
            setResult(RESULT_OK, toIntent);
            finish();
        });

    }



}