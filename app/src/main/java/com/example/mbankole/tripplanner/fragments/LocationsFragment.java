package com.example.mbankole.tripplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.mbankole.tripplanner.ExploreActivity;
import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.adapters.LocationsAdapter;
import com.example.mbankole.tripplanner.models.Location;

import java.util.ArrayList;

/**
 * Created by ericar on 7/11/17.
 *
 */

public class LocationsFragment extends Fragment implements View.OnClickListener {

    public LocationsFragment() {
    }

    LocationsAdapter locationsAdapter;
    ArrayList<Location> locations;
    RecyclerView rvLocations;
    LocationsAdapter searchesAdapter;
    ArrayList<Location> searchResults;
    RecyclerView rvSearches;
    public ExploreActivity exploreActivity;

    Button btClose;
    RelativeLayout rlSearches;

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
        rvSearches = (RecyclerView) v.findViewById(R.id.rvSearches);

        rlSearches = (RelativeLayout) v.findViewById(R.id.rlSearches);
        btClose = (Button) v.findViewById(R.id.btClose);

        btClose.setOnClickListener(this);
        // init the arraylist (data source)
        locations = new ArrayList<>();
        searchResults = new ArrayList<>();
        // construct the adapter from this data source
        locationsAdapter = new LocationsAdapter(locations);
        searchesAdapter = new LocationsAdapter(searchResults);
        // RecyclerView setup (layout manager, use adapter)
        rvLocations.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvSearches.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        // set the adapter
        rvLocations.setAdapter(locationsAdapter);
        rvSearches.setAdapter(searchesAdapter);
        locationsAdapter.exploreActivity = exploreActivity;
        searchesAdapter.exploreActivity = exploreActivity;

        //Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        //((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        hideSearchResults();
    }

    public void addItems(ArrayList<Location> response) {
        for (int i = 0; i < response.size(); i++) {
            Location location = response.get(i);
            locations.add(location);
            locationsAdapter.notifyItemInserted(locations.size() - 1);
        }
    }

    public void addItem(Location loc) {
        locations.add(loc);
        locationsAdapter.notifyItemInserted(locations.size() - 1);
    }

    public void removeItem(Location loc) {
        locations.remove(loc);
        locationsAdapter.notifyItemRemoved(locations.size() - 1);
    }

    public void addSearchResult(Location loc) {
        searchResults.add(loc);
        showSearchResults();
    }

    public void showSearchResults() {
        rlSearches.setVisibility(View.VISIBLE);
    }

    public void hideSearchResults() {
        rlSearches.setVisibility(View.GONE);
        searchResults.clear();
        locationsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btClose.getId()) {
            hideSearchResults();
        }
    }
}



//
//made the locations fragment


