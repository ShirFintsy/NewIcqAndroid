package com.example.newicqandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newicqandroid.R;
import com.example.newicqandroid.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UsersViewHolder>{

    private final LayoutInflater inflater;
    private List<User> chats;
    private final String connectedUser;
    private onUserListener listener;

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
    }

    public void addChat(User user){
        if(!chats.contains(user)){
            chats.add(user);
        }
        notifyDataSetChanged(); // not working when adding from outside
    }

    public List<User> getChats(){
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
            User currentUser = chats.get(position); // get user info from list by position
            holder.user.setText(currentUser.getId());
            holder.lastMsg.setText(currentUser.getLast());
            holder.date.setText(currentUser.getLastdate());
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
