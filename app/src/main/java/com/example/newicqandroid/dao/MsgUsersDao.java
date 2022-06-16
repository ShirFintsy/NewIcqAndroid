/**package com.example.newicqandroid.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.newicqandroid.entities.MsgUsers;

import java.util.List;


@Dao
public interface MsgUsersDao {

    @Transaction
    @Query("SELECT * FROM msgUsers")
    List<MsgUsers> getAll();

    // todo: complete
    @Transaction
    @Query("SELECT * FROM msgUsers WHERE msgUsers.`From` = :fromUser AND msgUsers.IdUser = :toUser")
    List<MsgUsers> getByUsers(String fromUser, String toUser);

    @Transaction
    @Insert
    void Insert(MsgUsers msgUsers);
}
**/