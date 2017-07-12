package com.example.mbankole.tripplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.adapters.LocationsAdapter;
import com.example.mbankole.tripplanner.models.Location;

import java.util.ArrayList;

/**
 * Created by ericar on 7/11/17.
 */

public class LocationsFragment extends Fragment {

    public LocationsFragment() {
    }

    LocationsAdapter locationsAdapter;
    ArrayList<Location> locations;
    RecyclerView rvLocations;
    ArrayList<Location> landmarks;

    public static LocationsFragment newInstance() {
        Bundle args = new Bundle();
        //args.putParcelable("user", user);
        LocationsFragment fragment = new LocationsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the layout
        View v = inflater.inflate(R.layout.fragment_locations, container, false);
        // find RecyclerView
        rvLocations = (RecyclerView) v.findViewById(R.id.rvLocations);
        // init the arraylist (data source)
        locations = new ArrayList<>();
        landmarks = new ArrayList<>();
        // construct the adapter from this data source
        locationsAdapter = new LocationsAdapter(locations);
        // RecyclerView setup (layout manager, use adapter)
        rvLocations.setLayoutManager(new LinearLayoutManager(getContext()));
        // set the adapter
        rvLocations.setAdapter(locationsAdapter);
        for (int i=0; i < 20; i++) {
            landmarks.add(Location.generateEiffelTower());
            landmarks.add(Location.generateStauteOfLiberty());
            landmarks.add(Location.generateTajMahal());
        }
        addItems(landmarks);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {}

    public void addItems(ArrayList<Location> response) {
        for (int i = 0; i < response.size(); i++) {
            Location location = response.get(i);
            landmarks.add(location);
            locationsAdapter.notifyItemInserted(locations.size() - 1);
        }
    }
}