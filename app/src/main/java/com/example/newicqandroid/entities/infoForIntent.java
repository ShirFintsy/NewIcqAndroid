package com.example.newicqandroid.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class infoForIntent implements Serializable  {
    private String user;
    private int idChat;
    private String msg;

    public infoForIntent(String user, int id, String text) {
        this.user =user;
        this.idChat = id;
        this.msg =text;
    }

    public int getIdChat() {
        return idChat;
    }

    public String getMsg() {
        return msg;
    }

    public String getUser() {
        return user;
    }
}
