package com.example.newicqandroid.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity(tableName = "messages")
public class Message {

    @PrimaryKey(autoGenerate = true)
    private int Id;

    private int idChat;
    private String fromIdUser;
    private String toIdUser;

    private String Content;
    private String Created;
    private boolean Sent;


    public Message(){ }

    public Message(String content, String created, boolean sent){
        this.Content = content;
        this.Created = created;
        this.Sent = sent;
    }

    public Message(String content, String from, String to, int idChat){
        this.idChat = idChat;
        this.Content = content;
        fromIdUser = from;
        toIdUser = to;

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date d = new Date();
        Created = formatter.format(d);
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

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        this.Content = content;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        this.Created = created;
    }

    public boolean isSent() {
        return Sent;
    }

    public void setSent(boolean sent) {
        this.Sent = sent;
    }



}
