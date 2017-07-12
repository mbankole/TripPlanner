
package com.example.mbankole.tripplanner.adapters;

        import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.models.Location;

import java.util.List;

/**
 * Created by ericar on 7/11/17.
 */

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> {

    List<Location> mLocations;
    Context context;

    public LocationsAdapter(List<Location> locations) {
        mLocations = locations;
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
    }

    @Override
    public int getItemCount() {
        return mLocations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvLocationname;

        public ViewHolder(View itemView) {
            super(itemView);
            tvLocationname = (TextView) itemView.findViewById(R.id.tvLocationname);
        }
    }
}