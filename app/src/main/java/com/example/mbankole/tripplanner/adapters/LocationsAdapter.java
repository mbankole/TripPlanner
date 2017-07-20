
//
//
//
//
//
//


package com.example.mbankole.tripplanner.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbankole.tripplanner.ExploreActivity;
import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.fragments.LocationDetailFragment;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.utility.gradient;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ericar on 7/11/17.
 */

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> {

    List<Location> mLocations;
    Context context;
    public ExploreActivity exploreActivity;
    FragmentManager fm;

    public LocationsAdapter(List<Location> locations) {
        mLocations = locations;
    }

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View locationView = inflater.inflate(R.layout.item_location, parent, false);
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
//                .resize(300, 300)
//                .centerCrop()
//                .transform(new jp.wasabeef.picasso.transformations.RoundedCornersTransformation(80, 0))
                .into(holder.ivLocationImage);
    }

    @Override
    public int getItemCount() {return mLocations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvLocationname;
        public ImageView ivLocationImage;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvLocationname = (TextView) itemView.findViewById(R.id.tvLocationname);
            ivLocationImage = (ImageView) itemView.findViewById(R.id.ivLocationImage);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Location location= mLocations.get(position);
                LocationDetailFragment frag = LocationDetailFragment.newInstance(location, false);
                frag.exploreActivity = exploreActivity;
                frag.show(fm, "name");
            }
        }
    }
}

//added on click listener





///
//
//
//
//
//
//
//
//
//
//
//
///
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

