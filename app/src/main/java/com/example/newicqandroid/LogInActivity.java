package com.example.newicqandroid;

import static java.lang.Thread.sleep;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.newicqandroid.api.ApiManager;
import com.example.newicqandroid.databinding.ActivityLoginBinding;
import com.example.newicqandroid.entities.User;
import com.example.newicqandroid.registerActivity.RegisterActivity;
import com.example.newicqandroid.repositories.UserRepository;

public class LogInActivity extends AppCompatActivity implements IOnResponse {

    private ActivityLoginBinding binding;
    private ApiManager apiManager;
    private UserRepository userRepo;
    private String server = "http://10.0.2.2:5067/api/";
    boolean validationFlags[] = {false, false};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userRepo = new UserRepository(getApplicationContext());
        setListeners();
    }
    private void setListeners(){
        binding.btnLogin.setOnClickListener(view ->{
            apiManager = new ApiManager(server);
            login();
        });
        binding.registerLink.setOnClickListener(
                view ->{
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    intent.putExtra("server", server);
                    startActivity(intent);
                }

        );
    }

    private void login() {
        String username = binding.userNameInput.getText().toString();
        String password = binding.passwordInput.getText().toString();

        apiManager.checkValidation(username, password, this);
    }

    public void setServer(String server) {
        this.server = server;
    }
    @Override
    public void onResponseValidation(boolean username, boolean password) {
        if (!username) { // user is not exists

            validationFlags[0] = false;
            runOnUiThread(new Runnable() {
                public void run() {
                    binding.userNameInput.setError("Username not exists");
                    binding.userNameInput.requestFocus();
                }
            });

        } else
            validationFlags[0] = true;
        if (!password) { // not valid password
            validationFlags[1] = false;
            runOnUiThread(new Runnable() {
                public void run() {
                    binding.passwordInput.setError("Wrong password");
                    binding.passwordInput.requestFocus();
                }
            });
        }
        else
            validationFlags[1] = true;

        if (validationFlags[0] && validationFlags[1]) {
            String user = binding.userNameInput.getText().toString();
            apiManager.getUser(user, this);
            //signIn in server- save the user in session
            //and load the data from the server
            apiManager.signIn(user, getApplicationContext(), this);


        }
    }

    @Override
    public void onResponseGetUser(User user) {
        if (userRepo.getUser(user.getId()) == null) {
            userRepo.addUser(user);
            apiManager.addUser(user, this);
        }
    }

    @Override
    public void onResponseSignIn() {
        String user = binding.userNameInput.getText().toString();
        // enter chats activity:
        Intent intent = new Intent(getApplicationContext(), ChatsActivity.class);
        intent.putExtra("username", user);
        intent.putExtra("server", server);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.settingOption) {
            Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
            someActivityResultLauncher.launch(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    // get result from setting activity
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        String server = data.getExtras().getString("server");
                        setServer(server);
                    }
                }
            });
}