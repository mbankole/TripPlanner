package com.example.mbankole.tripplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mbankole.tripplanner.ExploreActivity;
import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.adapters.LocationsAdapter;
import com.example.mbankole.tripplanner.models.Location;

import java.util.ArrayList;

/**
 * Created by ericar on 7/11/17.
 *
 */

public class LocationsFragment extends Fragment {

    public LocationsFragment() {
    }

    LocationsAdapter locationsAdapter;
    ArrayList<Location> locations;
    RecyclerView rvLocations;
    ArrayList<Location> landmarks;
    public ExploreActivity exploreActivity;

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
        rvLocations.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        // set the adapter
        rvLocations.setAdapter(locationsAdapter);
        locationsAdapter.exploreActivity = exploreActivity;
        for (int i=0; i < 20; i++) {
            landmarks.add(Location.generateEiffelTower());
            landmarks.add(Location.generateStatueOfLiberty());
            landmarks.add(Location.generateTajMahal());
        }

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        addItems(landmarks);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_map, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        final MenuItem miProfile = menu.findItem(R.id.miProfile);
        miProfile.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {}

    public void addItems(ArrayList<Location> response) {
        for (int i = 0; i < response.size(); i++) {
            Location location = response.get(i);
            locations.add(location);
            locationsAdapter.notifyItemInserted(locations.size() - 1);
        }
    }
}