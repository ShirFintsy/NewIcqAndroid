package com.example.newicqandroid.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.newicqandroid.entities.Chat;

import java.util.List;


@Dao
public interface ChatDao {

    @Transaction
    @Query("SELECT * FROM chats WHERE IdChat = :chatId")
    Chat getChatById(int chatId);

    @Transaction
    @Query("SELECT * FROM chats WHERE (idUser1 = :user1 AND idUser2 = :user2) " +
            "OR (idUser1 = :user2 AND idUser2 = :user1)")
    Chat getChatByUsers(String user1, String user2);

    @Transaction
    @Query("SELECT * FROM chats WHERE (idUser1 = :username OR idUser2 = :username) ")
    List<Chat> getChatsByUser(String username);

    @Transaction
    @Insert
    void Insert(Chat chat);
}
