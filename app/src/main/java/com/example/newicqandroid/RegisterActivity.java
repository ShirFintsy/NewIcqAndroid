package com.example.newicqandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.newicqandroid.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners(){

        binding.btnRegister.setOnClickListener(this::signUp);
        binding.imageButton.setOnClickListener(this::uploadImg);
    }

    public void signUp(View view) {

    }

    public void uploadImg(View view){

    }
}