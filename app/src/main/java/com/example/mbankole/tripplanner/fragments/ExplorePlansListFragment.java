package com.example.mbankole.tripplanner.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.activities.PlanEditActivity;
import com.example.mbankole.tripplanner.adapters.PlanAdapter;
import com.example.mbankole.tripplanner.models.Plan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.facebook.login.widget.ProfilePictureView.TAG;

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
    private SwipeRefreshLayout swipeContainer;
    FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;

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

        mDatabase = FirebaseDatabase.getInstance().getReference();

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

        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                planAdapter.clear();
                loadPlans();
            }
        });

        planAdapter.notifyItemInserted(plans.size() - 1);
        loadPlans();
        return v;
    }


    public void refreshAdd() {
        planAdapter.notifyItemInserted(0);
        rvPlans.smoothScrollToPosition(0);
    }



    public void loadPlans() {
        DatabaseReference ref = mDatabase.child("plans");
        swipeContainer.setRefreshing(true);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                    Plan plan = singleSnapshot.getValue(Plan.class);
                    fixPlan(plan);
                    plans.add(0, plan);
                    refreshAdd();
                }
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: shits fucked");
            }
        });
    }

    void fixPlan(Plan plan) {
        if (plan.people == null) plan.people = new ArrayList<>();
        if (plan.places == null) plan.places = new ArrayList<>();
    }

    public void refresh() {
        planAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {}
}