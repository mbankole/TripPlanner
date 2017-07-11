package com.example.mbankole.tripplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mbankole.tripplanner.R;

/**
 * Created by mbankole on 7/11/17.
 */

public class LocationsFragment extends Fragment {

    public LocationsFragment() {}

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
        return inflater.inflate(R.layout.fragment_placeholder, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    }
}
