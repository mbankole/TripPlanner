package com.example.mbankole.tripplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.adapters.RouteStepAdapter;
import com.example.mbankole.tripplanner.models.Route;

public class RouteFragment extends android.support.v4.app.DialogFragment {

    TextView tvDuration;
    TextView tvDistance;
    TextView tvStartAddress;
    TextView tvEndAddress;
    Route route;
    RecyclerView rvSteps;
    RouteStepAdapter stepAdapter;

    public static RouteFragment newInstance(Route route) {
        RouteFragment frag = new RouteFragment();
        Bundle args = new Bundle();
        args.putParcelable("route", route);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_route, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        route = getArguments().getParcelable("route");

        tvDuration = (TextView) view.findViewById(R.id.tvDuration);
        tvDistance = (TextView) view.findViewById(R.id.tvDistance);
        tvStartAddress = (TextView) view.findViewById(R.id.tvStartAddress);
        tvEndAddress = (TextView) view.findViewById(R.id.tvEndAddress);

        tvDuration.setText(route.duration);
        tvDistance.setText(route.distance);
        tvStartAddress.setText(route.startAddress);
        tvEndAddress.setText(route.endAddress);

        // find RecyclerView
        rvSteps = (RecyclerView) view.findViewById(R.id.rvSteps);
        // construct the adapter from this data source
        stepAdapter = new RouteStepAdapter(route.steps);
        // RecyclerView setup (layout manager, use adapter)
        rvSteps.setLayoutManager(new LinearLayoutManager(getContext()));
        // set the adapter
        rvSteps.setAdapter(stepAdapter);
        stepAdapter.notifyItemInserted(route.steps.size() - 1);
    }
}