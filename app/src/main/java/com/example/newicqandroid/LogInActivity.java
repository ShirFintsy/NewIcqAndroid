package com.example.newicqandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.newicqandroid.api.ApiManager;
import com.example.newicqandroid.databinding.ActivityLoginBinding;
import com.example.newicqandroid.registerActivity.RegisterActivity;

public class LogInActivity extends AppCompatActivity implements IOnResponse {

    private ActivityLoginBinding binding;
    private final ApiManager apiManager = new ApiManager();
    boolean checkedValidation[] = {false, false};
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
            checkedValidation[0] = false;
        } else {
            checkIfUserExists(username, this); // update userIsExists to false
        }

        if (password.isEmpty()) {
            binding.passwordInput.setError("Please fill out this field");
            binding.passwordInput.requestFocus();
            checkedValidation[1] = false;
        } else validPassword(username, password, this);

        if (checkedValidation[0] && checkedValidation[1]) {
            // enter chats activity:
            Intent intent = new Intent(getApplicationContext(), ChatsActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }
    }

    private void checkIfUserExists(String username, IOnResponse activity) {
        apiManager.IsUserExists(username, activity);
    }

    private void validPassword(String username, String password, IOnResponse activity) {
        apiManager.validPassword(username, password, activity);
    }

    @Override
    public void onResponseIsUserExists(boolean x) {
        if (!x) { // user is not exists
            binding.userNameInput.setError("User does not exists");
            binding.userNameInput.requestFocus();
            checkedValidation[0] = false;
        } else
            checkedValidation[0] = true;
    }
    @Override
    public void onResponseValidPassword(boolean x) {
        if (!x) { // not valid password
            binding.passwordInput.setError("Wrong password");
            binding.passwordInput.requestFocus();
            checkedValidation[1] = false;
        }
        else
            checkedValidation[1] = true;
    }
}