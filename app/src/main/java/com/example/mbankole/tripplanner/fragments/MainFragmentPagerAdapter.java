package com.example.mbankole.tripplanner.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by mbankole on 7/11/17.
 */

public class MainFragmentPagerAdapter  extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"Map", "locations", "people"};
    private Context context;
    FragmentManager fragmentManager;
    LocationsFragment locationsFragment;
    MapFragment mapFragment;
    PeopleFragment peopleFragment;

    public MainFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        fragmentManager = fm;
        this.context = context;
    }

    public LocationsFragment getLocationsFragment() {
        if (locationsFragment == null) {
            locationsFragment = LocationsFragment.newInstance();
        }
        return locationsFragment;
    }

    public MapFragment getMapFragment() {
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
        }
        return mapFragment;
    }

    public PeopleFragment getPeopleFragment() {
        if (peopleFragment == null) {
            peopleFragment = PeopleFragment.newInstance();
        }
        return peopleFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return getMapFragment();
            case 1:
                return getLocationsFragment();
            case 2:
                return getPeopleFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}