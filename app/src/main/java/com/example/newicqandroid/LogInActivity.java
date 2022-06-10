package com.example.newicqandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.newicqandroid.databinding.ActivityLoginBinding;

public class LogInActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }
    private void setListeners(){
        binding.btnLogin.setOnClickListener(v->
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));
    }
//    private void setListeners(){
//        binding.btnLogin.setOnClickListener(v-> {
//            EditText username = findViewById(R.id.userNameInput);
//            Intent intent = new Intent(this, ChatsActivity.class);
//            intent.putExtra("username", username.getText().toString());
//            startActivity(intent);
//        });
//    }
}