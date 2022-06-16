package com.example.newicqandroid.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.newicqandroid.entities.Message;

import java.util.List;


@Dao
public interface MessageDao {

    @Transaction
    @Query("SELECT * FROM messages")
    List<Message> getAll();

    @Transaction
    @Query("SELECT * FROM messages WHERE fromIdUser = :username OR toIdUser = :username")
    List<Message> getMsgsByUser(String username);

    @Transaction
    @Insert
    void Insert(Message msg);


}
