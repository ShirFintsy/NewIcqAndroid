package com.example.newicqandroid.api;

import android.content.Context;
import android.os.Build;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import com.example.newicqandroid.IOnResponse;
import com.example.newicqandroid.entities.Chat;
import com.example.newicqandroid.entities.InvitaionApi;
import com.example.newicqandroid.entities.Message;
import com.example.newicqandroid.entities.User;
import com.example.newicqandroid.repositories.ChatRepository;
import com.example.newicqandroid.repositories.MessagesRepository;
import com.example.newicqandroid.repositories.UserRepository;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
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

     public void addUser(User user, Context context){
          Call<User> call = apiWebService.addUser(user);
          call.enqueue(new Callback<User>() {
               @RequiresApi(api = Build.VERSION_CODES.N)
               @Override
               public void onResponse(Call<User> call, Response<User> response) {
                    getContacts(user.getId(), context);
                    getChats(user.getId(), context);
               }

               @Override
               public void onFailure(Call<User> call, Throwable t) {

               }
          });
     }

     public void addChat(Chat chat){
          InvitaionApi invitation = new InvitaionApi(chat.getIdUser1(), chat.getIdUser2(), "android");
          Call<Void> call = apiWebService.invitations(invitation);
          call.enqueue(new Callback<Void>() {
               @RequiresApi(api = Build.VERSION_CODES.N)
               @Override
               public void onResponse(Call<Void> call, Response<Void> response) {

               }

               @Override
               public void onFailure(Call<Void> call, Throwable t) {

               }
          });
     }



     private void getChats(String username, Context context){
          Call<List<ApiTupleResponse>> call = apiWebService.getUserChats(username);
          call.enqueue(new Callback<List<ApiTupleResponse>>() {
               @RequiresApi(api = Build.VERSION_CODES.N)
               @Override
               public void onResponse(Call<List<ApiTupleResponse>> call,
                                      Response<List<ApiTupleResponse>> response) {

                    //add to chats repository
                    ChatRepository chatRepository = new ChatRepository(context);
                    List<ApiTupleResponse> chats = response.body();
                    if(chats!=null) {
                         for (ApiTupleResponse chat : chats) {
                              Chat c = new Chat(username, chat.getItem2());
                              chatRepository.insertChat(c);
                              int idChat = c.getIdChat();

                              //get messages of this chat
                              Call<List<Message>> call3 = apiWebService.getMsgs(username, chat.getItem1());
                              //call3.enqueue(callbackMsgs);
                              call3.enqueue(new Callback<List<Message>>() {
                                   @Override
                                   public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                                        //add to users repository
                                        //MessagesRepository msgRepo = new MessagesRepository(context, idChat);
                                        List<Message> msgs = response.body();
                                        if(msgs!=null) {
                                             for (Message msg : msgs) {
                                                  msg.setIdChat(idChat);
                                                  if(msg.isSent()) {
                                                       msg.setFromIdUser(username);
                                                       msg.setToIdUser(chat.getItem2());
                                                  }else{
                                                       msg.setFromIdUser(chat.getItem2());
                                                       msg.setToIdUser(username);
                                                  }
                                                  MessagesRepository.insertMsg(msg, context);
                                                  //msgRepo.addMsg(msg);
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
               public void onFailure(Call<List<ApiTupleResponse>> call, Throwable t) {

               }
          });
     }


     private void getContacts(String username, Context context){
          Call<List<User>> call1 = apiWebService.getContacts(username);
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
     }


     public void signIn(String username, Context context){
          Call<Void> call = apiWebService.signIn(username);

          call.enqueue(new Callback<Void>() {
               @RequiresApi(api = Build.VERSION_CODES.N)
               @Override
               public void onResponse(Call<Void> call, Response<Void> response) {
                    getContacts(username, context);
                    getChats(username, context);
               }

               @Override
               public void onFailure(Call<Void> call, Throwable t) {

               }
          });
     }
}
