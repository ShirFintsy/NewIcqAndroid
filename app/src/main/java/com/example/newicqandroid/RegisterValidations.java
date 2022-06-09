package com.example.newicqandroid;

import com.example.newicqandroid.databinding.ActivityRegisterBinding;

public class RegisterValidations {
    private ActivityRegisterBinding binding;

    public RegisterValidations(ActivityRegisterBinding binding){
        this.binding = binding;
    }

    public boolean checkIsExists(String username){
        return false;
    }
}
