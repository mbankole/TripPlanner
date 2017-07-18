package com.example.mbankole.tripplanner.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mbankole.tripplanner.ExploreActivity;
import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.models.TransportOption;
import com.example.mbankole.tripplanner.models.User;
import com.example.mbankole.tripplanner.utility.gradient;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mbankole on 7/18/17.
 */

public class PlanListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Object> mObjects;
    Context context;
    public ExploreActivity exploreActivity;
    android.app.FragmentManager fm;

    private final int LOCATION = 0, TRANSPORT = 1;

    public PlanListAdapter(List<Object> objects) {
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
            case LOCATION:
                View v1 = inflater.inflate(R.layout.item_plan_location, parent, false);
                viewHolder = new ViewHolderLocation(v1);
                break;
            case TRANSPORT:
                View v2 = inflater.inflate(R.layout.item_transport_select, parent, false);
                viewHolder = new ViewHolderTransport(v2);
                break;
            default:
                View v = inflater.inflate(R.layout.item_plan_location, parent, false);
                viewHolder = new ViewHolderLocation(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case LOCATION:
                ViewHolderLocation vh1 = (ViewHolderLocation) holder;
                configureViewHolderLocation(vh1, position);
                break;
            case TRANSPORT:
                ViewHolderTransport vh2 = (ViewHolderTransport) holder;
                configureViewHolderTransport(vh2, position);
                break;
            default:
                ViewHolderLocation vh = (ViewHolderLocation) holder;
                configureViewHolderLocation(vh, position);
                break;
        }
    }

    public void configureViewHolderLocation(ViewHolderLocation holder, int position) {
        // get the data according to position
        Location location = (Location) mObjects.get(position);
        // populate the views according to this data
        holder.tvLocationname.setText(location.name);
        Picasso.with(context)
                .load(location.photoUrl)
                //.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
                .transform(new gradient())
                .into(holder.ivLocationImage);
    }

    public void configureViewHolderTransport(ViewHolderTransport holder, int position) {
        TransportOption transportOption = (TransportOption) mObjects.get(position);
        holder.tvName.setText(transportOption.name);
    }

    @Override
    public int getItemViewType(int position) {
        if (mObjects.get(position) instanceof Location) {
            return LOCATION;
        } else if (mObjects.get(position) instanceof TransportOption) {
            return TRANSPORT;
        }
        return -1;
    }

    @Override
    public int getItemCount() {return mObjects.size();
    }

    public class ViewHolderLocation extends RecyclerView.ViewHolder {

        public TextView tvLocationname;
        public ImageView ivLocationImage;
        public LinearLayout llPeople;
        public RelativeLayout rlInfo;
        public Button expand;
        public Button add;
        public Button close;

        public boolean expanded = false;

        public ViewHolderLocation(View itemView) {
            super(itemView);
            tvLocationname = (TextView) itemView.findViewById(R.id.tvLocationname);
            ivLocationImage = (ImageView) itemView.findViewById(R.id.ivLocationImage);
            llPeople = (LinearLayout) itemView.findViewById(R.id.llPeople);
            rlInfo = (RelativeLayout) itemView.findViewById(R.id.rlInfo);
            expand = (Button) itemView.findViewById(R.id.btExpand);
            add = (Button) itemView.findViewById(R.id.btAdd);
            close = (Button) itemView.findViewById(R.id.btClose);

            rlInfo.setVisibility(View.GONE);

            expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expanded) {
                        rlInfo.setVisibility(View.GONE);
                        expanded = false;
                    }
                    else {
                        rlInfo.setVisibility(View.VISIBLE);
                        expanded = true;
                    }
                }
            });

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rlInfo.setVisibility(View.GONE);
                    expanded = false;
                }
            });

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addUser(User.generateAdam());
                }
            });
        }

        void addUser(User user) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_user_slim, llPeople, false);
            ((TextView)v.findViewById(R.id.tvUsername)).setText(user.name);
            llPeople.addView(v);
        }
    }

    public class ViewHolderTransport extends RecyclerView.ViewHolder{

        public TextView tvName;
        public ImageView ivImage;

        public ViewHolderTransport(View itemView) {
            super(itemView);
            //itemView.setOnClickListener(this);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
        }
        /*
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                User user = (mObjects.get(position));
                PeopleDetailsFragment frag = PeopleDetailsFragment.newInstance(user);
                frag.exploreActivity = exploreActivity;
                frag.show(fm, "name");
            }
        }*/
    }
}