package com.example.newicqandroid.api;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import com.example.newicqandroid.IOnResponse;
import com.example.newicqandroid.entities.Chat;
import com.example.newicqandroid.entities.Message;
import com.example.newicqandroid.entities.User;
import com.example.newicqandroid.repositories.ChatRepository;
import com.example.newicqandroid.repositories.MessagesRepository;
import com.example.newicqandroid.repositories.UserRepository;

import java.util.List;
import java.util.Objects;
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

     public void getUser(String username,IOnResponse func) {

          Call<User> call = apiWebService.getUserByUsername(username);
          call.enqueue(new Callback<User>() {
               @RequiresApi(api = Build.VERSION_CODES.N)
               @Override
               public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    func.onResponseGetUser(user);
               }

               @Override
               public void onFailure(Call<User> call, Throwable t) {

               }
          });
     }

     public void addUser(User user){
          Call<User> call = apiWebService.addUser(user);
          call.enqueue(new Callback<User>() {
               @RequiresApi(api = Build.VERSION_CODES.N)
               @Override
               public void onResponse(Call<User> call, Response<User> response) {

               }

               @Override
               public void onFailure(Call<User> call, Throwable t) {

               }
          });
     }


     public void getData(String username, Context context){
          Call<List<User>> call1 = apiWebService.getContacts();
         // Call<List<Pair<Integer,String>>> call2 = apiWebService.getUserChats(username);

/*
          Callback<List<Message>> callbackMsgs =  new Callback<List<Message>>() {
               @RequiresApi(api = Build.VERSION_CODES.N)
               @Override
               public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                    //add to users repository
                    MessagesRepository msgRepo = new MessagesRepository(context, -1);
                    List<Message> msgs = response.body();
                    if(msgs!=null) {
                         for (Message msg : msgs) {
                              msgRepo.insertMsg(msg);
                         }
                    }
               }

               @Override
               public void onFailure(Call<List<Message>> call, Throwable t) {

               }
          };*/

          call1.enqueue(new Callback<List<User>>() {
               @RequiresApi(api = Build.VERSION_CODES.N)
               @Override
               public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    //add to users repository
                    UserRepository userRepository = new UserRepository(context);
                    List<User> users = response.body();
                    if(users!=null) {
                         for (User user : users) {
                              userRepository.addUser(user);
                         }
                    }
               }

               @Override
               public void onFailure(Call<List<User>> call, Throwable t) {

               }
          });
          /*
          call2.enqueue(new Callback<List<Pair<Integer,String>>>() {
               @RequiresApi(api = Build.VERSION_CODES.N)
               @Override
               public void onResponse(Call<List<Pair<Integer,String>>> call,
                  Response<List<Pair<Integer,String>>> response) {
                    //add to chats repository
                    ChatRepository chatRepository = new ChatRepository(context);
                    List<Pair<Integer,String>> chats = response.body();
                    if(chats!=null) {
                         for (Pair<Integer, String> chat : chats) {
                              chatRepository.insertChat(new Chat(chat.first,
                                      username, chat.second));

                              //get messages of this chat
                              Call<List<Message>> call3 = apiWebService.getMsgs(chat.first);
                              //call3.enqueue(callbackMsgs);
                              call3.enqueue(new Callback<List<Message>>() {
                                   @Override
                                   public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                                        //add to users repository
                                        MessagesRepository msgRepo = new MessagesRepository(context, chat.first);
                                        List<Message> msgs = response.body();
                                        if(msgs!=null) {
                                             for (Message msg : msgs) {
                                                  msg.setIdChat(chat.first);
                                                  if(msg.isSent()) {
                                                       msg.setFromIdUser(username);
                                                  }else{
                                                       msg.setToIdUser(chat.second);
                                                  }
                                                  msgRepo.addMsg(msg);
                                             }

                                        }
                                   }
                                   @Override
                                   public void onFailure(Call<List<Message>> call, Throwable t) {

                                   }
                              });

                         }
                    }
               }

               @Override
               public void onFailure(Call<List<Pair<Integer,String>>> call, Throwable t) {

               }
          });*/
     }


     public void signIn(String username, Context context){
          Call<User> call = apiWebService.signIn(username);

          call.enqueue(new Callback<User>() {
               @RequiresApi(api = Build.VERSION_CODES.N)
               @Override
               public void onResponse(Call<User> call, Response<User> response) {
                   // Log.i("hiiii","success");

                    getData(username, context);
               }

               @Override
               public void onFailure(Call<User> call, Throwable t) {

               }
          });
     }
}
