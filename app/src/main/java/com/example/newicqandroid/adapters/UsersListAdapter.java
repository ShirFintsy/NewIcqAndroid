package com.example.newicqandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newicqandroid.R;
import com.example.newicqandroid.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UsersViewHolder>{

    private final LayoutInflater inflater;
    private List<User> chats = new ArrayList<>();

    static class UsersViewHolder extends RecyclerView.ViewHolder{
        private final TextView user;
        private final TextView date;

        private UsersViewHolder(View view){
            super(view);
            this.user = view.findViewById(R.id.userName);
            this.date = view.findViewById(R.id.date);
        }
    }

    public UsersListAdapter(Context context, String connectedUser){
        inflater = LayoutInflater.from(context);
    }

    public void addChat(User user){
        this.chats.add(user);
        notifyDataSetChanged();
    }

    public List<User> getChats(){
        return chats;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_layout, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        if(chats != null) {
            User currentUser = chats.get(position); // get user info from list by position
            holder.user.setText(currentUser.getLast());
            holder.date.setText(currentUser.getLastdate());
        }
    }


    @Override
    public int getItemCount() {
        if(chats == null)
            return 0;
        return chats.size();
    }

}
