package com.example.mbankole.tripplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.adapters.PlanAdapter;
import com.example.mbankole.tripplanner.models.Plan;
import com.example.mbankole.tripplanner.models.User;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by mbankole on 7/21/17.
 */

public class ProfilePlansListFragment extends Fragment {

    PlanAdapter planAdapter;
    public ArrayList<Plan> plans;
    RecyclerView rvPlans;
    public User user;
    private FirebaseAuth mAuth;
    private SwipeRefreshLayout swipeContainer;
    private FirebaseAnalytics mFirebaseAnalytics;
    private DatabaseReference mDatabase;

    public ProfilePlansListFragment() {

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

        mDatabase = FirebaseDatabase.getInstance().getReference();
        plans = new ArrayList<>();

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
                planAdapter.clear();
                loadPlans();
            }
        });

        planAdapter.clear();
        loadPlans();

        planAdapter.notifyItemInserted(plans.size() - 1);
        return v;
    }

    public void refreshAdd() {
        planAdapter.notifyItemInserted(0);
        rvPlans.smoothScrollToPosition(0);
    }

    public void loadPlans() {
        DatabaseReference ref = mDatabase.child("plans");
        swipeContainer.setRefreshing(true);

        final ArrayList<Plan> tempPlans = new ArrayList<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                    Plan plan = singleSnapshot.getValue(Plan.class);
                    fixPlan(plan);
                    tempPlans.add(0, plan);
                }

                for (Plan plan : tempPlans) {
                    if (plan.people.contains(user.getUid())) {
                        plans.add(0, plan);
                        refreshAdd();
                    }
                }
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: shits fucked");
                swipeContainer.setRefreshing(false);
            }
        });
    }

    void fixPlan(Plan plan) {
        if (plan.people == null) plan.people = new ArrayList<>();
        if (plan.places == null) plan.places = new ArrayList<>();
    }
}
