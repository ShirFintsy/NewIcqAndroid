package com.example.newicqandroid;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newicqandroid.api.ApiManager;
import com.example.newicqandroid.databinding.ActivityAddChatsBinding;
import com.example.newicqandroid.databinding.ActivityChatsBinding;
import com.example.newicqandroid.entities.Chat;
import com.example.newicqandroid.entities.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddChatsActivity extends AppCompatActivity implements IOnResponse{
    private ActivityAddChatsBinding binding;
    private ApiManager apiManager;
    private String connectedUser;
    private String server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddChatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        connectedUser = intent.getExtras().getString("connected");
        server = intent.getExtras().getString("server");
        apiManager = new ApiManager(server);

        binding.addButton.setOnClickListener(view-> {
            String user = binding.addChatInput.getText().toString();
            apiManager.getUser(user, this);
        });

    }

    @Override
    public void onResponseValidation(boolean username, boolean password) { }

    @Override
    public void onResponseGetUser(User user) {
        Intent toIntent = new Intent(getApplicationContext(), ChatsActivity.class);

        if (user == null || user.getId().equals(connectedUser)) {
            runOnUiThread(new Runnable() {
                public void run() {
                    binding.addChatInput.setError("User does not exists");
                    binding.addChatInput.requestFocus();
                }
            });
        }else {
            AppLocalDB db = AppLocalDB.createAppDBInstance(getApplicationContext());
            if(db.userDao().getByUsername(user.getId()) == null) { // check if user is not already in db
                db.userDao().Insert(user); // insert user into db
            }
            Chat current = db.chatDao().getChatByUsers(user.getId(), connectedUser);
            if (current != null) { // chat is already on screen
                runOnUiThread(new Runnable() {
                    public void run() {
                        binding.addChatInput.setError("Chat is already on screen");
                        binding.addChatInput.requestFocus();
                    }
                });

            } else {
                // notify the chats activity to add this chat to local db
                toIntent.putExtra("username", user.getId());
                setResult(RESULT_OK, toIntent);
                finish();
            }

        }
    }

    @Override
    public void onResponseSignIn() { }
}