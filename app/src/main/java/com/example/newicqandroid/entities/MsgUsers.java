/**package com.example.newicqandroid.entities;

import androidx.room.Embedded;
import androidx.room.Entity;

@Entity(tableName = "msgUsers")
public class MsgUsers {

    private Message Message;
    private User From;
    private User To;

    public MsgUsers(Message msg, User from, User to)
    {
        Message = msg;
        From = from;
        To = to;
    }

    public Message getMessage() {
        return Message;
    }

    public void setMessage(Message message) {
        Message = message;
    }

    public User getFrom() {
        return From;
    }

    public void setFrom(User from) {
        From = from;
    }

    public User getTo() {
        return To;
    }

    public void setTo(User to) {
        To = to;
    }



}
**/