package com.example.mbankole.tripplanner.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.models.Message;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mbankole on 7/31/17.
 */

public class PlanMessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Object> mObjects;
    Context context;
    android.app.FragmentManager fm;

    private final int RECEIVED = 0, SENT = 1, REQUEST = 2;

    public PlanMessagesAdapter(List<Object> objects) {
        mObjects = objects;
    }

    public void setFm(android.app.FragmentManager fm) {
        this.fm = fm;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();

        switch (viewType) {
            case RECEIVED:
                View v1 = inflater.inflate(R.layout.item_message_received, parent, false);
                viewHolder = new ViewHolderReceived(v1);
                break;
            case SENT:
                View v2 = inflater.inflate(R.layout.item_message_sent, parent, false);
                viewHolder = new ViewHolderSent(v2);
                break;
            default:
                View v = inflater.inflate(R.layout.item_message_received, parent, false);
                viewHolder = new ViewHolderReceived(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case RECEIVED:
                ViewHolderReceived vh1 = (ViewHolderReceived) holder;
                configureViewHolderReceived(vh1, position);
                break;
            case SENT:
                ViewHolderSent vh2 = (ViewHolderSent) holder;
                configureViewHolderSent(vh2, position);
                break;
            default:
                ViewHolderReceived vh = (ViewHolderReceived) holder;
                configureViewHolderReceived(vh, position);
                break;
        }
    }

    public void configureViewHolderReceived(ViewHolderReceived holder, int position) {
        Message message = (Message) mObjects.get(position);
        holder.tvBody.setText(message.getBody());
        Picasso.with(context).load(message.getSenderProfileUrl()).into(holder.ivProfileImage);
    }

    public void configureViewHolderSent(ViewHolderSent holder, int position) {
        Message message = (Message) mObjects.get(position);
        holder.tvBody.setText(message.getBody());
        Picasso.with(context).load(message.getSenderProfileUrl()).into(holder.ivProfileImage);
    }

    @Override
    public int getItemViewType(int position) {
        Object object = mObjects.get(position);
        if (object instanceof Message) {
            if (((Message)object).isReceived()) return RECEIVED;
            else return SENT;
        }
        return -1;
    }

    @Override
    public int getItemCount() {return mObjects.size();
    }

    public class ViewHolderReceived extends RecyclerView.ViewHolder {

        public TextView tvBody;
        public ImageView ivProfileImage;

        public ViewHolderReceived(View itemView) {
            super(itemView);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
        }
    }

    public class ViewHolderSent extends RecyclerView.ViewHolder {

        public TextView tvBody;
        public ImageView ivProfileImage;

        public ViewHolderSent(View itemView) {
            super(itemView);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
        }
    }
}