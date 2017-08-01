package com.example.mbankole.tripplanner.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by danahe97 on 8/1/17.
 */

public class ChatUserAdapter extends RecyclerView.Adapter<ChatUserAdapter.ViewHolder> {

    ArrayList<User> mPeople;
    Context context;

    public ChatUserAdapter(ArrayList<User> people) {
        mPeople = people;
    }

    @Override
    public ChatUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View userView = inflater.inflate(R.layout.item_chat_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChatUserAdapter.ViewHolder holder, int position) {
        User user = mPeople.get(position);
        Picasso.with(context)
                .load(user.imageUrl)
             //   .fit()
                .into(holder.ivProfileImage);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivProfileImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
        }
    }

    @Override
    public int getItemCount() {
        return mPeople.size();
    }
}
