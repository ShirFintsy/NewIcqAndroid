package com.example.newicqandroid.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newicqandroid.AppLocalDB;
import com.example.newicqandroid.R;
import com.example.newicqandroid.dao.ChatDao;
import com.example.newicqandroid.entities.Chat;
import com.example.newicqandroid.entities.User;
import com.example.newicqandroid.repositories.ChatRepository;
import com.example.newicqandroid.repositories.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UsersViewHolder>{

    private final LayoutInflater inflater;
    private List<Chat> chats;
    private final String connectedUser;
    private onUserListener listener;
    private ChatRepository chatRepository;
    private UserRepository userRepository;

    static class UsersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView user;
        private TextView lastMsg;
        private TextView date;
        private ImageView profilePicture;
        private onUserListener listener;

        private UsersViewHolder(View view, onUserListener onUserListener){
            super(view);
            this.listener = onUserListener;
            this.user = view.findViewById(R.id.user);
            this.lastMsg = view.findViewById(R.id.msg);
            this.date = view.findViewById(R.id.msgDate);
            this.profilePicture = view.findViewById(R.id.profilePicture);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onUserClick(getAdapterPosition());
        }
    }

    public UsersListAdapter(Context context, String connectedUser, onUserListener userListener){
        this.inflater = LayoutInflater.from(context);
        this.connectedUser = connectedUser;
        this.listener = userListener;
        this.chats = new ArrayList<>();
        this.chatRepository = new ChatRepository(context);
        this.userRepository = new UserRepository(context);
    }

    public void addChat(Chat chat){
        if(!chats.contains(chat)){
            chats.add(chat);
        }
        notifyDataSetChanged(); // not working when adding from outside
    }
    public void setChats(List<Chat> chats){
        this.chats = chats;
        notifyDataSetChanged();
    }

    public List<Chat> getChats(){
        return chats;
    }

    @NonNull
    @Override
    public UsersListAdapter.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_layout, parent, false);
        return new UsersViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        if(chats != null) {
            Chat chat = chats.get(position);
            String current = chatRepository.getOtherUser(connectedUser, chat.getIdChat());
            holder.user.setText(userRepository.getDisplayName(current));
            holder.lastMsg.setText(chatRepository.getLastMsgByChadId(chat.getIdChat()).getContent());
            holder.date.setText(chatRepository.getLastMsgByChadId(chat.getIdChat()).getCreated());
            //holder.profilePicture.setImageBitmap(currentUser.decodeImg());

        }
    }

    public interface onUserListener {
        void onUserClick(int position);
    }
    @Override
    public int getItemCount() {
        if(chats == null)
            return 0;
        return chats.size();
    }

}
