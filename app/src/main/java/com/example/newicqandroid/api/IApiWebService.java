package com.example.newicqandroid.api;


import com.example.newicqandroid.entities.User;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IApiWebService {
    @GET("contacts/{username}")
    Call<User> getUserByUsername(@Path("username") String username);


}
