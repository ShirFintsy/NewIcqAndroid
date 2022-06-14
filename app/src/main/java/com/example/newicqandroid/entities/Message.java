package com.example.newicqandroid.entities;

import androidx.room.Entity;

import java.util.Date;


@Entity
public class Message {

    private int Id;
    private String Content;
    private String  Created;
    private boolean Sent;

    public Message(String content){
        Content = content;
        Created = new Date().toString();
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }

    public boolean isSent() {
        return Sent;
    }

    public void setSent(boolean sent) {
        Sent = sent;
    }



}
