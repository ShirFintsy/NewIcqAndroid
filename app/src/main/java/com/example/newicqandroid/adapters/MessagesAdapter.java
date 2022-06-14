package com.example.newicqandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newicqandroid.R;
import com.example.newicqandroid.entities.MsgUsers;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {

    public static final int RECEIVED_MSG = 0;
    public static final int SENT_MSG = 1;

    private LayoutInflater inflater;
    private List<MsgUsers> msgs;
    private String connectedUser;

    public MessagesAdapter(Context context, String connectedUser){
        inflater = LayoutInflater.from(context);
        this.connectedUser = connectedUser;
    }

    public void setMsgs(List<MsgUsers> msgs){
        this.msgs = msgs;
        notifyDataSetChanged();
    }

    public List<MsgUsers> getMsgs(){
        return msgs;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if(viewType == SENT_MSG){
            view = inflater.inflate(R.layout.layout_sent_message, parent, false);
        }else{
            view = inflater.inflate(R.layout.layout_receive_message, parent, false);
        }

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        if(msgs != null) {
            MsgUsers currMsg = msgs.get(position);
            holder.content.setText(currMsg.getMessage().getContent());
            holder.time.setText(currMsg.getMessage().getCreated());
        }
    }

    @Override
    public int getItemCount() {
        if(msgs == null)
            return 0;
        return msgs.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(msgs.get(position).getFrom().getName().equals(connectedUser))
            return SENT_MSG;
        else
            return RECEIVED_MSG;
    }


    class MessageViewHolder extends RecyclerView.ViewHolder{
        private final TextView content;
        private final TextView time;

        private MessageViewHolder(View view){
            super(view);
            this.content = view.findViewById(R.id.content);
            this.time = view.findViewById(R.id.time);
        }
    }

}
