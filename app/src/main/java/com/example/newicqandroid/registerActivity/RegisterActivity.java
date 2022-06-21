package com.example.newicqandroid.registerActivity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.example.newicqandroid.ChatsActivity;
import com.example.newicqandroid.IOnResponse;
import com.example.newicqandroid.LogInActivity;
import com.example.newicqandroid.Utils;
import com.example.newicqandroid.databinding.ActivityRegisterBinding;
import com.example.newicqandroid.entities.User;
import com.example.newicqandroid.repositories.UserRepository;

import java.io.IOException;


public class RegisterActivity extends AppCompatActivity implements IOnResponse {

    public ActivityRegisterBinding binding;
    private RegisterValidations validator;
    private static boolean error = false; //indicates if some field was invalid
    private UserRepository userRepository;
    private User registeredUser = null;
    private static boolean uploadedImg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();

        validator = new RegisterValidations(binding);

        userRepository = new UserRepository(getApplicationContext());
    }

    private void setListeners(){
        binding.btnRegister.setOnClickListener(this::signUp);
        binding.imageButton.setOnClickListener(this::uploadImg);
    }

    public void signUp(View view) {
        error = false;
        String passPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        String username = binding.userNameInput.getText().toString().trim();
        String displayName = binding.displayNameInput.getText().toString().trim();
        String pass = binding.passwordInput.getText().toString().trim();
        String confirmPass = binding.confirmPasswordInput.getText().toString().trim();

        String strImg;
        if(!uploadedImg){
            strImg = "defult_picture.jpg";
        }else {
            Drawable image = binding.imageButton.getDrawable();
            Bitmap img = Utils.drawableToBitmap(image);
            strImg = Utils.encodeImg(img);
        }
        registeredUser = new User(username, displayName, pass, strImg);

        if(username.isEmpty()) {
            binding.userNameInput.setError("Please fill out this field");
            binding.userNameInput.requestFocus();
            error = true;
        }else {
            validator.checkIsExists(username, this);
            //error = true;
        }
        if(displayName.isEmpty()){
            binding.displayNameInput.setError("Please fill out this field");
            binding.displayNameInput.requestFocus();
            error = true;
        } if(pass.isEmpty()){
            binding.passwordInput.setError("Please fill out this field");
            binding.passwordInput.requestFocus();
            error = true;
        }else if(!pass.matches(passPattern)){
            binding.passwordInput.setError("Password not matches pattern: Minimum eight characters, " +
                    "at least one uppercase letter, one lowercase letter and one number");
            binding.passwordInput.requestFocus();
            error = true;
        }else if(!confirmPass.equals(pass)) {
            binding.confirmPasswordInput.setError("Not matches password field");
            binding.confirmPasswordInput.requestFocus();
            error = true;
        }

        //if no error in validation happened
        //if(!error){
            //endRegistration();
       // }
    }

    private void endRegistration(){
        //send the data to the webApi and save in room
        userRepository.addUser(registeredUser);

        //move to the chats activity
        Intent intent = new Intent(getApplicationContext(), ChatsActivity.class);
        intent.putExtra("username", binding.userNameInput.getText().toString());
        startActivity(intent);
    }

    public void uploadImg(View view) {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchLoadImg.launch(i);
    }

    ActivityResultLauncher<Intent> launchLoadImg =
        registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
        (result) -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null && data.getData() != null) {
                    Uri imgUri = data.getData();
                    Bitmap imgBitmap = null;
                    try {
                        imgBitmap = MediaStore.Images.Media.getBitmap(
                                this.getContentResolver(), imgUri);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                    Bitmap circleBitmap =  Utils.getCircleBitmap(imgBitmap);
                    binding.imageButton.setImageBitmap(circleBitmap);
                    uploadedImg = true;
                }
            }
        });


    @Override
    public void onResponseValidation(boolean username, boolean password) {
        if (username) { // user is exists
            error = true;

            runOnUiThread(new Runnable() {
                public void run() {
                    binding.userNameInput.setError("Username already exists");
                    binding.userNameInput.requestFocus();
                }
            });

        }else if(!error){
            // todo: for now- until chat activity will word
            //Intent intent = new Intent(getApplicationContext(), ChatsActivity.class);
            //intent.putExtra("username", binding.userNameInput.getText().toString());
            //startActivity(intent);
            endRegistration();
        }
    }

    @Override
    public void onResponseGetUser(User user) { }
}