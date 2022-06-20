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
import android.widget.Button;
import android.widget.TextView;

import com.example.newicqandroid.adapters.UsersListAdapter;
import com.example.newicqandroid.api.ApiManager;
import com.example.newicqandroid.databinding.ActivityChatsBinding;
import com.example.newicqandroid.entities.User;
import com.example.newicqandroid.entities.Chat;

import java.util.ArrayList;
import java.util.List;

public class ChatsActivity extends AppCompatActivity implements UsersListAdapter.onUserListener {

    private ActivityChatsBinding binding;
    private String connectedUser;
    private ApiManager apiManager = new ApiManager();
    private UsersListAdapter usersListAdapter;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userList = new ArrayList<>();

        Intent intent = getIntent();
        String username = intent.getExtras().getString("username");
        binding.username.setText(username);
        connectedUser = username;
        //todo: add profile picture from user by api

        RecyclerView usersList = binding.userChatsList;
        usersListAdapter = new UsersListAdapter(this, connectedUser, this);
        usersList.setAdapter(usersListAdapter);
        usersList.setLayoutManager(new LinearLayoutManager(this));
        //debug:
        User tomer = new User("tomer");
        User hilla = new User("hilla");
        usersListAdapter.addChat(tomer);
        userList.add(tomer);
        usersListAdapter.addChat(hilla);
        userList.add(hilla);

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

//    @Override
//    public void onResponseValidation(boolean username, boolean password) { }
//
//    @Override
//    public void onResponseGetUser(User user) {
//       if (user != null) {
//           userList.add(user);
//           usersListAdapter.addChat(user);
//       }
//    }

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