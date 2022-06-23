package com.example.newicqandroid.entities;

public class TransferMessage {

    private String From;
    private String To;
    private String Content;

    public TransferMessage(String from, String to, String content){
        this.From = from;
        this.To = to;
        this.Content = content;
    }
}
