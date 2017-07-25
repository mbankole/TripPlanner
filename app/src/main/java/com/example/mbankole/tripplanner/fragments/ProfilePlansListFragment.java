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
import com.example.mbankole.tripplanner.adapters.PlanAdapter;
import com.example.mbankole.tripplanner.models.Plan;

import java.util.ArrayList;

/**
 * Created by mbankole on 7/21/17.
 */

public class ProfilePlansListFragment extends Fragment {

    PlanAdapter planAdapter;
    public ArrayList<Plan> plans;
    RecyclerView rvPlans;

    public ProfilePlansListFragment() {
        plans = new ArrayList<>();
    }

    public static ProfilePlansListFragment newInstance() {
        Bundle args = new Bundle();
        ProfilePlansListFragment fragment = new ProfilePlansListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the layout
        View v = inflater.inflate(R.layout.fragment_explore_plans_list, container, false);

        // find RecyclerView
        rvPlans = (RecyclerView) v.findViewById(R.id.rvPlans);
        // init the arraylist (data source)

        // construct the adapter from this data source
        planAdapter = new PlanAdapter(plans);
        // RecyclerView setup (layout manager, use adapter)
        rvPlans.setLayoutManager(new LinearLayoutManager(getContext()));
        // set the adapter
        rvPlans.setAdapter(planAdapter);
//        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        planAdapter.notifyItemInserted(plans.size() - 1);
        return v;
    }

}
