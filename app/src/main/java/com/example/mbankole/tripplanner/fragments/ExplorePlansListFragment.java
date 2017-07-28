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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by mbankole on 7/20/17.
 */

public class ExplorePlansListFragment extends Fragment {

    public ExplorePlansListFragment() {
    }

    PlanAdapter planAdapter;
    public ArrayList<Plan> plans;
    RecyclerView rvPlans;
    FloatingActionButton fabAdd;

    FirebaseUser currentUser;
    private FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        fabAdd = (FloatingActionButton) v.findViewById(R.id.fabNew);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PlanEditActivity.class);
                i.putExtra("creatorUID", currentUser.getUid());
                i.putExtra("creatorUserName", currentUser.getDisplayName());
                getActivity().startActivityForResult(i, PLAN_REQUEST_CODE);
            }
        });

        // find RecyclerView
        rvPlans = (RecyclerView) v.findViewById(R.id.rvPlans);
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
        planAdapter.notifyItemInserted(plans.size() - 1);
        return v;
    }


    public void refreshAdd() {
        planAdapter.notifyItemInserted(0);
        rvPlans.smoothScrollToPosition(0);
    }

    public void refresh() {
        planAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {}
}