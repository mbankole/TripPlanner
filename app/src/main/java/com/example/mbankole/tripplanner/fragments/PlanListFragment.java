package com.example.mbankole.tripplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mbankole.tripplanner.ExploreActivity;
import com.example.mbankole.tripplanner.PlanActivity;
import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.adapters.PlanListAdapter;
import com.example.mbankole.tripplanner.models.Location;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ericar on 7/13/17.
 */

public class PlanListFragment extends Fragment{

    public ArrayList<Location> locations;
    public ArrayList<Object> list_objects = new ArrayList<>();
    RecyclerView rvPlanList;
    public PlanListAdapter listAdapter;
    android.app.FragmentManager fm;
    public PlanActivity planActivity;
    public ExploreActivity exploreActivity;


    public static PlanListFragment newInstance() {
        Bundle args = new Bundle();
        //args.putParcelable("user", user);
        PlanListFragment fragment = new PlanListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the layout
        View v = inflater.inflate(R.layout.fragment_listsview, container, false);

        fm = getActivity().getFragmentManager();
        // find RecyclerView
        rvPlanList = (RecyclerView) v.findViewById(R.id.rvPlanList);
        // construct the adapter from this data source
        listAdapter = new PlanListAdapter(list_objects);
        listAdapter.setFm(fm);
//        locationAdapter.exploreActivity = exploreActivity;
        // RecyclerView setup (layout manager, use adapter)
        rvPlanList.setLayoutManager(new LinearLayoutManager(getContext()));
        // set the adapter
        rvPlanList.setAdapter(listAdapter);

        // Extend the Callback class
        ItemTouchHelper.Callback _ithCallback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);
            }

            //and in your implementaion of
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // get the viewHolder's and target's positions in your adapter data, swap them
                Collections.swap(list_objects, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                // and notify the adapter that its dataset has changed
                listAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                planActivity.refresh();

                String ToastString = "";
                for (int i=0; i<list_objects.size(); i++) {
                    //ToastString += ((User)list_objects.get(i)).name;
                }
                Toast toast = Toast.makeText(getContext(), ToastString, Toast.LENGTH_LONG);
                toast.show();
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };
        ItemTouchHelper ith = new ItemTouchHelper(_ithCallback);
        ith.attachToRecyclerView(rvPlanList);

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        listAdapter.notifyItemInserted(list_objects.size() - 1);
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
        MenuItem miProfile = menu.findItem(R.id.miProfile);
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
                return false;
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {}

    public void refresh() {

 //       addItems(places);
    }
}

//created fragment of the planning list
//
//
