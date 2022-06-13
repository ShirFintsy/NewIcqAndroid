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
        binding.btnLogin.setOnClickListener(view ->{
            login();

            //startActivity(new Intent(getApplicationContext(), ChatsActivity.class));
        });
        binding.registerLink.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));
    }

    private void login() {
        String username = binding.userNameInput.getText().toString();
        String password = binding.passwordInput.getText().toString();

        //validation:
        if(username.isEmpty()) {
            binding.userNameInput.setError("Please fill out this field");
            binding.userNameInput.requestFocus();
        } else if(!checkIfUserExists(username)){
            binding.userNameInput.setError("User does not exists");
            binding.userNameInput.requestFocus();
        } if (password.isEmpty()) {
            binding.passwordInput.setError("Please fill out this field");
            binding.passwordInput.requestFocus();
        } else if(!validPassword(username, password)) {
            binding.passwordInput.setError("Wrong password");
            binding.passwordInput.requestFocus();
        }

        // enter chats activity:
        Intent intent = new Intent(getApplicationContext(), ChatsActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private boolean checkIfUserExists(String username) {
        return false; // until connection to API
    }

    private boolean validPassword(String username, String password) {
        return false; // until connection to API
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