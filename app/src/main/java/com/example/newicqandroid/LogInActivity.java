package com.example.newicqandroid;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.newicqandroid.api.ApiManager;
import com.example.newicqandroid.databinding.ActivityLoginBinding;
import com.example.newicqandroid.entities.User;
import com.example.newicqandroid.registerActivity.RegisterActivity;

public class LogInActivity extends AppCompatActivity implements IOnResponse {

    private ActivityLoginBinding binding;
    private final ApiManager apiManager = new ApiManager();
    boolean validationFlags[] = {false, false};
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
        });
        binding.registerLink.setOnClickListener(
                view ->
                    startActivity(new Intent(getApplicationContext(), RegisterActivity.class))
        );
    }

    private void login() {
        String username = binding.userNameInput.getText().toString();
        String password = binding.passwordInput.getText().toString();

        apiManager.checkValidation(username, password, this);
    }

    @Override
    public void onResponseValidation(boolean username, boolean password) {
        if (!username) { // user is not exists
            binding.userNameInput.setError("User does not exists");
            binding.userNameInput.requestFocus();
            validationFlags[0] = false;
        } else
            validationFlags[0] = true;
        if (!password) { // not valid password
            //binding.passwordInput.setError("Wrong password");
            System.out.println("error");
            binding.passwordInput.requestFocus();
            validationFlags[1] = false;
        }
        else
            validationFlags[1] = true;

        if (validationFlags[0] && validationFlags[1]) {
            String user = binding.userNameInput.getText().toString();
            // enter chats activity:
            Intent intent = new Intent(getApplicationContext(), ChatsActivity.class);
            intent.putExtra("username", user);
            startActivity(intent);
        }
    }

    @Override
    public void onResponseGetUser(User user) { }
}