
package com.example.mbankole.tripplanner.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbankole.tripplanner.ExploreActivity;
import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.utility.gradient;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ericar on 7/11/17.
 */

public class PlanLocationsAdapter extends RecyclerView.Adapter<PlanLocationsAdapter.ViewHolder> {

    List<Location> mLocations;
    Context context;
    public ExploreActivity exploreActivity;
    android.app.FragmentManager fm;

    public PlanLocationsAdapter(List<Location> locations) {
        mLocations = locations;
    }

    public void setFm(android.app.FragmentManager fm) {
        this.fm = fm;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View locationView = inflater.inflate(R.layout.item_plan_location, parent, false);
        ViewHolder viewHolder = new ViewHolder(locationView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get the data according to position
        Location location = mLocations.get(position);
        // populate the views according to this data
        holder.tvLocationname.setText(location.name);
        Picasso.with(context)
                .load(location.photoUrl)
                //.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
                .transform(new gradient())
                .into(holder.ivLocationImage);
    }

    @Override
    public int getItemCount() {return mLocations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvLocationname;
        public ImageView ivLocationImage;

        public ViewHolder(View itemView) {
            super(itemView);
            tvLocationname = (TextView) itemView.findViewById(R.id.tvLocationname);
            ivLocationImage = (ImageView) itemView.findViewById(R.id.ivLocationImage);
        }
    }
}