package com.example.newicqandroid.api;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.newicqandroid.IOnResponse;
import com.example.newicqandroid.entities.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

     Retrofit retrofit;
     IApiWebService apiWebService;

     public ApiManager(){
          retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:5067/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
          apiWebService = retrofit.create(IApiWebService.class);
     }


     public void IsUserExists(String username, IOnResponse func) {

          Call<User> call = apiWebService.getUserByUsername(username);
          call.enqueue(new Callback<User>() {
               @RequiresApi(api = Build.VERSION_CODES.N)
               @Override
               public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    if (user != null){
                         //result.booleanValue() = false;
                         //func.apply(true);
                         func.onResponseIsUserExists();
                    }
               }

               @Override
               public void onFailure(Call<User> call, Throwable t) {

               }
          });

/*
          try{
               Response<User> res = call.execute();
               User user = res.body();
               if (user == null){
                    result[0] = false;
               }

               return result[0];
          }catch(Exception e){
               return false;
          }*/
     }

}
