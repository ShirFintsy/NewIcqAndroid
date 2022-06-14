package com.example.newicqandroid;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.example.newicqandroid.databinding.ActivityRegisterBinding;

import java.io.IOException;


public class RegisterActivity extends AppCompatActivity implements IOnResponse {

    public ActivityRegisterBinding binding;
    private RegisterValidations validator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();

        validator = new RegisterValidations(binding);

    }

    private void setListeners(){
        binding.btnRegister.setOnClickListener(this::signUp);
        binding.imageButton.setOnClickListener(this::uploadImg);
    }

    private void updateUserError(){
        binding.userNameInput.setError("Username already exists");
        binding.userNameInput.requestFocus();
    }

    public void signUp(View view) {
        String passPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        String username = binding.userNameInput.getText().toString().trim();
        String displayName = binding.displayNameInput.getText().toString().trim();
        String pass = binding.passwordInput.getText().toString().trim();
        String confirmPass = binding.confirmPasswordInput.getText().toString().trim();



        if(username.isEmpty()) {
            binding.userNameInput.setError("Please fill out this field");
            binding.userNameInput.requestFocus();
        }else{
            validator.checkIsExists(username, this);

        } if(displayName.isEmpty()){
            binding.displayNameInput.setError("Please fill out this field");
            binding.displayNameInput.requestFocus();
        } if(pass.isEmpty()){
            binding.passwordInput.setError("Please fill out this field");
            binding.passwordInput.requestFocus();
        }else if(!pass.matches(passPattern)){
            binding.passwordInput.setError("Password not matches pattern: Minimum eight characters, " +
                    "at least one uppercase letter, one lowercase letter and one number");
            binding.passwordInput.requestFocus();
        }else if(!confirmPass.equals(pass)){
            binding.confirmPasswordInput.setError("Not matches password field");
            binding.confirmPasswordInput.requestFocus();
        }
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

                    Bitmap circleBitmap =  RegisterHelper.getCircleBitmap(imgBitmap);
                    binding.imageButton.setImageBitmap(circleBitmap);
                }
            }
        });


    @Override
    public void onResponseIsUserExists(boolean x) {
        if (x) { // user is exists
            binding.userNameInput.setError("Username already exists");
            binding.userNameInput.requestFocus();
        }
    }

    @Override
    public void onResponseValidPassword(boolean x) {
        return;
    }
}