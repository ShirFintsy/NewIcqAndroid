package com.example.newicqandroid;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.example.newicqandroid.adapters.UsersListAdapter;
import com.example.newicqandroid.api.ApiManager;
import com.example.newicqandroid.databinding.ActivityChatsBinding;
import com.example.newicqandroid.entities.User;
import com.example.newicqandroid.entities.Chat;

public class ChatsActivity extends AppCompatActivity implements IOnResponse {

    private ActivityChatsBinding binding;
    private String connectedUser = "rotem";
    private String otherUser = "shir";
    private ApiManager apiManager = new ApiManager();
    UsersListAdapter usersListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String username = intent.getExtras().getString("username");
        binding.username.setText(username);
        //todo: add profile picture from user by api

        RecyclerView usersList = binding.userChatsList;
        usersListAdapter = new UsersListAdapter(this, connectedUser);
        usersList.setAdapter(usersListAdapter);
        usersList.setLayoutManager(new LinearLayoutManager(this));
        usersListAdapter.addChat(new User("tomer"));

        // set listener to the add chat floating button
        binding.addChat.setOnClickListener(this::addChat);

        // todo: this id temporary button- in the future will be changed to the chat button
        binding.tmpButton.setOnClickListener(this::goToChat);
    }
    public void addThisUser(String username){
        apiManager.getUser(username, this);
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
        Intent intent = new Intent(getApplicationContext(), AddChatsActivity.class);
        someActivityResultLauncher.launch(intent);
    }

    @Override
    public void onResponseValidation(boolean username, boolean password) { }

    @Override
    public void onResponseGetUser(User user) {
        usersListAdapter.addChat(user);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        String user = data.getExtras().getString("username");
                        addThisUser(user);
                        //apiManager.getUser(user, );
                    }
                }
            });
}