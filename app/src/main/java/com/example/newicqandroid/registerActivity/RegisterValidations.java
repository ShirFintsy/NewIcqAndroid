package com.example.newicqandroid.registerActivity;

import com.example.newicqandroid.IOnResponse;
import com.example.newicqandroid.databinding.ActivityRegisterBinding;

import com.example.newicqandroid.api.ApiManager;

public class RegisterValidations {
    private ActivityRegisterBinding binding;
    private ApiManager apiManager = new ApiManager();

    public RegisterValidations(ActivityRegisterBinding binding){
        this.binding = binding;
    }

    public void checkIsExists(String username, IOnResponse activity){
        apiManager.IsUserExists(username, activity);
    }
}
