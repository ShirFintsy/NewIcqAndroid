package com.example.newicqandroid.api;


import android.util.Pair;

import com.example.newicqandroid.entities.Chat;
import com.example.newicqandroid.entities.InvitaionApi;
import com.example.newicqandroid.entities.Message;
import com.example.newicqandroid.entities.User;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IApiWebService {
    @GET("contacts/{username}")
    Call<User> getUserByUsername(@Path("username") String username);

    @POST("setup/register")
    Call<User> addUser(@Body User user);

    @GET("setup/{username}")
    Call<Void> signIn(@Path("username") String username);

    @GET("contacts")
    Call<List<User>> getContacts();

    @GET("chats/user/{username}")
    Call<List<Pair<Integer,String>>> getUserChats(@Path("username") String username);

    @GET("chats/{chatId}")
    Call<List<Message>> getMsgs(@Path("chatId") int chatId);

    @POST("invitations")
    Call<Void> invitations(@Body InvitaionApi invitations);

}
