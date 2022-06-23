package com.example.newicqandroid.entities;

import java.io.Serializable;

public class infoForIntent implements Serializable  {
    private String user;
    private String otherUser;
    private String msg;

    public infoForIntent(String user, String otherUser, String text) {
        this.user =user;
        this.otherUser = otherUser;
        this.msg =text;
    }

    public String getOtherUser() {
        return otherUser;
    }

    public String getMsg() {
        return msg;
    }

    public String getUser() {
        return user;
    }
}
