package com.example.mbankole.tripplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbankole.tripplanner.ExploreActivity;
import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.utility.gradient;
import com.squareup.picasso.Picasso;

/**
 * Created by mbankole on 7/12/17.
 *
 */

public class LocationDetailFragment extends DialogFragment implements  View.OnClickListener{
    TextView tvName;
    TextView tvAddress;
    ImageView ivPhoto;
    Button btAdd;
    ExploreActivity exploreActivity;

    private final String TAG = "POIDETAILFRAGMENT";

    public LocationDetailFragment() {}

    public static LocationDetailFragment newInstance(Location loc, Boolean plan) {
        LocationDetailFragment frag = new LocationDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("location", loc);
        args.putBoolean("plan", plan);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location_detail, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        final Location loc = getArguments().getParcelable("location");
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvAddress = (TextView) view.findViewById(R.id.tvAddress);
        btAdd = (Button) view.findViewById(R.id.btAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exploreActivity.addLocation(loc);
            }
        });
        ivPhoto = (ImageView)view.findViewById(R.id.ivPhoto);
        tvName.setText(loc.name);
        tvAddress.setText(loc.address);
        if (loc.photoUrl != null) {
            Picasso.with(getContext())
                    .load(loc.photoUrl)
                    //.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
                    .transform(new gradient())
                    .into(ivPhoto);
        }
        Boolean plan = getArguments().getBoolean("plan");
        if (plan) {
            btAdd.setVisibility(View.GONE);
        }
    }

    public void debug(String message) {
        Log.d(TAG, message);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btAdd.getId()) {
            addToList();
        }
    }

    void addToList() {
        dismiss();
    }
}
