package com.example.mbankole.tripplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
    ArrayList<Location> searchResults;
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
        searchResults = new ArrayList<>();
        // construct the adapter from this data source
        locationsAdapter = new LocationsAdapter(locations);
        // RecyclerView setup (layout manager, use adapter)
        rvLocations.setLayoutManager(new LinearLayoutManager(getContext()));
        // set the adapter
        rvLocations.setAdapter(locationsAdapter);
        locationsAdapter.exploreActivity = exploreActivity;

        //Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        //((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //setHasOptionsMenu(true);
        return v;
    }
    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Implementing ActionBar Search inside a fragment
        MenuItem item = menu.add("Search");
        //item.setIcon(R.drawable.ic_action_search); // sets icon
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        SearchView sv = new SearchView(getActivity());

        // modifying the text inside edittext component
        //int id = sv.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        //TextView textView = (TextView) sv.findViewById(id);
        //textView.setHint("Search location...");
        //textView.setHintTextColor(getResources().getColor(R.color.DarkGray));
        //textView.setTextColor(getResources().getColor(R.color.butts));

        // implementing the listener
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.length() < 4) {
                    Toast.makeText(getActivity(),
                            "Your search query must not be less than 3 characters",
                            Toast.LENGTH_LONG).show();
                    return true;
                } else {
                    //doSearch(s);
                    Toast.makeText(getActivity(),
                            "eneterer",
                            Toast.LENGTH_LONG).show();
                    return false;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getActivity(),
                        "Your search query must not be less than 3 characters",
                        Toast.LENGTH_LONG).show();
                return true;
            }
        });
        item.setActionView(sv);
        /*inflater.inflate(R.menu.menu_map, menu);
        super.onCreateOptionsMenu(menu, inflater);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setVisibility(View.GONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                GmapClient.locationSearch(query, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                addItem(Location.locationFromJson(results.getJSONObject(i)));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        searchView.clearFocus();
                    }
                });

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
        MenuItem miPlan = menu.findItem(R.id.miPlan);
        miPlan.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent i = new Intent(getContext(), PlanActivity.class);
                startActivity(i);
                return false;
            }
        });
    }*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {}

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
}