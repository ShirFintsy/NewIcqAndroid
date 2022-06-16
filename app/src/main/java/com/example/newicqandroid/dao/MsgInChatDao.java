/**package com.example.newicqandroid.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Transaction;


import com.example.newicqandroid.entities.MsgInChat;

import java.util.List;


@Dao
public interface MsgInChatDao {

    @Transaction
    @Insert
    void Insert(MsgInChat msgInChat);
}
**/