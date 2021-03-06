package com.example.mbankole.tripplanner.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by danahe97 on 7/11/17.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    List<User> mUsers;
    Context context;
    android.app.FragmentManager fm;

    public UserAdapter(List<User> users) {
        mUsers = users;
    }

    public void setFm(android.app.FragmentManager fm) {
        this.fm = fm;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View userView = inflater.inflate(R.layout.item_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get the data according to position
        User user = mUsers.get(position);
        // populate the views according to this data
        holder.tvUsername.setText(user.name);
        holder.ivUserImage.setImageResource(R.color.black);
        if (user.imageUrl != null) {
            Picasso.with(context)
                    .load(user.imageUrl)
                    .fit()
                    .into(holder.ivUserImage);
        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvUsername;
        public ImageView ivUserImage;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            ivUserImage = (ImageView) itemView.findViewById(R.id.ivUserImage);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                User user =  mUsers.get(position);
                Intent intent = new Intent(context, com.example.mbankole.tripplanner.activities.ProfileActivity.class);
                intent.putExtra("user", user);
                context.startActivity(intent);
            }
        }
    }

    public void clear() {
        mUsers.clear();
        notifyDataSetChanged();
    }
}
