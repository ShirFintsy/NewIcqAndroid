package com.example.newicqandroid.api;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.newicqandroid.IOnResponse;
import com.example.newicqandroid.entities.User;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
                    .callbackExecutor(Executors.newSingleThreadExecutor())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
          apiWebService = retrofit.create(IApiWebService.class);
     }


//     public void IsUserExists(String username, IOnResponse func) {
//
//          Call<User> call = apiWebService.getUserByUsername(username);
//          call.enqueue(new Callback<User>() {
//               @RequiresApi(api = Build.VERSION_CODES.N)
//               @Override
//               public void onResponse(Call<User> call, Response<User> response) {
//                    User user = response.body();
//                    func.onResponseIsUserExists(user != null); // send true if user is exists, false otherwise
//               }
//
//               @Override
//               public void onFailure(Call<User> call, Throwable t) {
//
//               }
//          });
//     }

     public void checkValidation(String username, String password, IOnResponse func) {

          Call<User> call = apiWebService.getUserByUsername(username);
          call.enqueue(new Callback<User>() {
               @RequiresApi(api = Build.VERSION_CODES.N)
               @Override
               public void onResponse(Call<User> call, Response<User> response) {
                    boolean usernameResult = false, passwordResult = false;
                    User user = response.body();
                    if (user != null) {
                         usernameResult = true;

                         if (user.getPassword().equals(password)) {
                              passwordResult = true;
                         }
                    }
                    func.onResponseValidation(usernameResult, passwordResult); // send true if user is exists, false otherwise
               }

               @Override
               public void onFailure(Call<User> call, Throwable t) {

               }
          });
     }

//     public void validPassword(String username, String password, IOnResponse func) {
//          Call<User> call = apiWebService.getUserByUsername(username);
//          call.enqueue(new Callback<User>() {
//               @RequiresApi(api = Build.VERSION_CODES.N)
//               @Override
//               public void onResponse(Call<User> call, Response<User> response) {
//                    User user = response.body();
//                    if (user != null){
//                         func.onResponseValidPassword(user.getPassword().equals(password)); // true if equals, false otherwise
//                    } //else
////                         func.onResponseValidPassword(false);
//               }
//
//               @Override
//               public void onFailure(Call<User> call, Throwable t) {
//
//               }
//          });
//     }

}
