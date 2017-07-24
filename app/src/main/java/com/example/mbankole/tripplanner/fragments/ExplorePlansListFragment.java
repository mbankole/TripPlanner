package com.example.mbankole.tripplanner.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.activities.PlanEditActivity;
import com.example.mbankole.tripplanner.adapters.PlanAdapter;
import com.example.mbankole.tripplanner.models.Plan;

import java.util.ArrayList;

/**
 * Created by mbankole on 7/20/17.
 */

public class ExplorePlansListFragment extends Fragment {

    public ExplorePlansListFragment() {
    }

    PlanAdapter planAdapter;
    ArrayList<Plan> plans;
    RecyclerView rvPlans;
    FloatingActionButton fabAdd;

    public static final int PLAN_REQUEST_CODE = 20;

    public static ExplorePlansListFragment newInstance() {
        Bundle args = new Bundle();
        ExplorePlansListFragment fragment = new ExplorePlansListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the layout
        View v = inflater.inflate(R.layout.fragment_explore_plans_list, container, false);

        fabAdd = (FloatingActionButton) v.findViewById(R.id.fabNew);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PlanEditActivity.class);
                getActivity().startActivityForResult(i, PLAN_REQUEST_CODE);
            }
        });

        // find RecyclerView
        rvPlans = (RecyclerView) v.findViewById(R.id.rvPlans);
        // init the arraylist (data source)
        plans = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Plan plan = new Plan();
            plans.add(plan.generateSeattlePlan(getContext()));
        }
        // construct the adapter from this data source
        planAdapter = new PlanAdapter(plans);
        // RecyclerView setup (layout manager, use adapter)
        rvPlans.setLayoutManager(new LinearLayoutManager(getContext()));
        // set the adapter
        rvPlans.setAdapter(planAdapter);
//        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        planAdapter.notifyItemInserted(plans.size() - 1);
        return v;
    }


    public void refresh() {
        planAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {}
}