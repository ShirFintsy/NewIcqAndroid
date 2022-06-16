package com.example.newicqandroid.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.newicqandroid.entities.Chat;
import com.example.newicqandroid.entities.Message;

import java.util.List;


@Dao
public interface ChatDao {

    @Transaction
    @Query("SELECT * FROM chats WHERE IdChat = :chatId")
    List<Chat> getChat(int chatId);

    @Transaction
    @Insert
    void Insert(Chat chat);
}
