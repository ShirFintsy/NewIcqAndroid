package com.example.newicqandroid.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "chats")
public class Chat {
    @PrimaryKey(autoGenerate = false)
    private int idChat;
    private String idUser1;
    private String idUser2;

    private static int counterId = 0;

    private void generateId(){
        counterId++;
        idChat = counterId;
    }
    public Chat(){
       generateId();
    }

    public Chat(String idUser1, String idUser2) {
        generateId();
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public String getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(String idUser1) {
        this.idUser1 = idUser1;
    }

    public String getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(String idUser2) {
        this.idUser2 = idUser2;
    }



}
