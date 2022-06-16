package com.example.newicqandroid.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;


import com.example.newicqandroid.entities.User;

import java.util.List;

@Dao
public interface UserDao {

    @Transaction
    @Query("SELECT * FROM users")
    List<User> getAll();

    @Transaction
    @Query("SELECT * FROM users WHERE idUser = :username")
    User getByUsername(String username);

    @Transaction
    @Insert
    void Insert(User user);
}
