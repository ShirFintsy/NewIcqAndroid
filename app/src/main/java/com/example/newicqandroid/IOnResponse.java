package com.example.newicqandroid;

import com.example.newicqandroid.entities.User;

public interface IOnResponse {
    void onResponseValidation(boolean username, boolean password);
    void onResponseGetUser(User user);
}
