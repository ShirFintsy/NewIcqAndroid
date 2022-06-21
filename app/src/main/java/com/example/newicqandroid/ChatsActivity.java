package com.example.newicqandroid;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.newicqandroid.adapters.UsersListAdapter;
import com.example.newicqandroid.api.ApiManager;
import com.example.newicqandroid.databinding.ActivityChatsBinding;
import com.example.newicqandroid.entities.User;
import com.example.newicqandroid.entities.Chat;
import com.example.newicqandroid.viewModels.ChatsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChatsActivity extends AppCompatActivity implements UsersListAdapter.onUserListener {

    private ActivityChatsBinding binding;
    private String connectedUser;
    private ApiManager apiManager = new ApiManager();
    private UsersListAdapter usersListAdapter;
    private List<User> userList;
    private ChatsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        //view model:
//        viewModel = new ViewModelProvider(this).get(ChatsViewModel.class);
//        viewModel.getChats().observe(this, new Observer<Chat>() {
//            @Override
//            public void onChanged(Chat chats) {
//
//            }
//        });


        // current intent:
        Intent intent = getIntent();
        String username = intent.getExtras().getString("username");
        binding.username.setText(username);
        connectedUser = username;
        //todo: add profile picture from user by api

        // adpter for recycle list:
        userList = new ArrayList<>();
        RecyclerView usersList = binding.userChatsList;
        usersListAdapter = new UsersListAdapter(this, connectedUser, this);
        usersList.setAdapter(usersListAdapter);
        usersList.setLayoutManager(new LinearLayoutManager(this));

        // set listener to the add chat floating button
        binding.addChat.setOnClickListener(this::addChat);
    }

    public void addThisUser(String username){
        AppLocalDB db = AppLocalDB.createAppDBInstance(getApplicationContext());
        User user = db.userDao().getByUsername(username);
        if (user != null) {
           userList.add(user);
           usersListAdapter.addChat(user);
        }

        db.chatDao().Insert(new Chat(connectedUser, username));

    }

    private void addChat(View v){
        Intent intent = new Intent(getApplicationContext(), AddChatsActivity.class);
        intent.putExtra("connected", connectedUser);
        someActivityResultLauncher.launch(intent);
    }

    // get result from add chat activity
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
                        //apiManager.getUser(user,this);
                    }
                }
            });

    // when click on a specific chat - enter to chat messages
    @Override
    public void onUserClick(int position) {
        AppLocalDB db = AppLocalDB.createAppDBInstance(getApplicationContext());
        User user = userList.get(position);
        String otherUser = user.getId();


        if(db.chatDao().getChatByUsers(connectedUser, otherUser) == null) {
            db.chatDao().Insert(new Chat(connectedUser, otherUser));
        }

        Chat c = db.chatDao().getChatByUsers(connectedUser, otherUser);

        Intent intent = new Intent(getApplicationContext(), ChatMessagesActivity.class);
        intent.putExtra("username", connectedUser);
        intent.putExtra("idChat", c.getIdChat());
        startActivity(intent);
    }
}