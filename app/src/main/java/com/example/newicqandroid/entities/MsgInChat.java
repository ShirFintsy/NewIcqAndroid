
/**
package com.example.newicqandroid.entities;

import androidx.room.Entity;

import java.util.LinkedList;
import java.util.List;

@Entity(tableName = "msgInChat")
public class MsgInChat {
    public Chat Chat;
    public List<MsgUsers> Messages;

    public MsgInChat(Chat chat, MsgUsers msgUsers)
    {
        Chat = chat;
        Messages = new LinkedList<MsgUsers>();
        Messages.add(msgUsers);
    }
    public MsgInChat(Chat chat, List<MsgUsers> msgs)
    {
        Chat = chat;
        Messages = msgs;
    }

    public Chat getChat() {
        return Chat;
    }

    public void setChat(Chat chat) {
        Chat = chat;
    }

    public List<MsgUsers> getMessages() {
        return Messages;
    }

    public void setMessages(List<MsgUsers> messages) {
        Messages = messages;
    }


}
**/