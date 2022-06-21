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

import android.util.Log;
import android.view.View;

import com.example.newicqandroid.adapters.UsersListAdapter;
import com.example.newicqandroid.api.ApiManager;
import com.example.newicqandroid.databinding.ActivityChatsBinding;
import com.example.newicqandroid.entities.Chat;
import com.example.newicqandroid.entities.InvitaionApi;
import com.example.newicqandroid.repositories.ChatRepository;
import com.example.newicqandroid.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class ChatsActivity extends AppCompatActivity implements UsersListAdapter.onUserListener {

    private ActivityChatsBinding binding;
    private String connectedUser;
    private ApiManager apiManager = new ApiManager();
    private UsersListAdapter usersListAdapter;
    private List<Chat> userList;
    private ChatRepository chatRepository;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        chatRepository = new ChatRepository(getApplicationContext());
        userRepository = new UserRepository(getApplicationContext());

        // current intent:
        Intent intent = getIntent();
        String username = intent.getExtras().getString("username");
        binding.username.setText(userRepository.getDisplayName(username));
        connectedUser = username;
        //todo: add profile picture from user by api
        //binding.profilePicture.setImageBitmap(userRepository.getProfilePic(connectedUser));

        // adapter for recycle list:
        userList = new ArrayList<>();
        RecyclerView usersList = binding.userChatsList;
        usersListAdapter = new UsersListAdapter(this, connectedUser, this);
        usersList.setAdapter(usersListAdapter);
        usersList.setLayoutManager(new LinearLayoutManager(this));
        setUpChats();

        // set listener to the add chat floating button
        binding.addChat.setOnClickListener(this::addChat);
    }

    public void addThisUser(String username){
        Chat chat = new Chat(connectedUser, username);
        Chat current = chatRepository.insertChat(chat);
        userList.add(current);
        usersListAdapter.addChat(current);
        apiManager.addChat(chat);
    }

    private void addChat(View v){
        Intent intent = new Intent(getApplicationContext(), AddChatsActivity.class);
        intent.putExtra("connected", connectedUser);
        someActivityResultLauncher.launch(intent);
    }

    private void setUpChats() {
        List<Chat> userChats = chatRepository.getChatsByUser(connectedUser);
        userList = userChats;
        usersListAdapter.setChats(userChats);
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
                    }
                }
            });

    // when click on a specific chat - enter to chat messages
    @Override
    public void onUserClick(int position) {
        Chat chat = userList.get(position);
        Intent intent = new Intent(getApplicationContext(), ChatMessagesActivity.class);
        intent.putExtra("username", connectedUser);
        intent.putExtra("idChat", chat.getIdChat());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpChats();
    }

    //todo: this will sign out user and upload to server
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}