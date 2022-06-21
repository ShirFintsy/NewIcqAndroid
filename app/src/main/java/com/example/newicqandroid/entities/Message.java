package com.example.newicqandroid.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "messages")
public class Message {

    @PrimaryKey(autoGenerate = true)
    private int idMsg;

    private int idChat;
    private String fromIdUser;
    private String toIdUser;

    private String content;
    private String created;
    private boolean sent;

    public Message(){}

    public Message(String content, String created, boolean sent){
        this.content = content;
        this.created = created;
        this.sent = sent;
    }

    public Message(String content, String from, String to, int idChat){
        this.idChat = idChat;
        this.content = content;
        created = new Date().toString();
        fromIdUser = from;
        toIdUser = to;
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public String getFromIdUser() {
        return fromIdUser;
    }

    public void setFromIdUser(String fromIdUser) {
        this.fromIdUser = fromIdUser;
    }

    public String getToIdUser() {
        return toIdUser;
    }

    public void setToIdUser(String toIdUser) {
        this.toIdUser = toIdUser;
    }

    public int getIdMsg() {
        return idMsg;
    }

    public void setIdMsg(int idMsg) {
        this.idMsg = idMsg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }



}
