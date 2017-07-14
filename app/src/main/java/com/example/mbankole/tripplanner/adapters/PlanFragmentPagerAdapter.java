package com.example.mbankole.tripplanner.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mbankole.tripplanner.fragments.ListView;
import com.example.mbankole.tripplanner.fragments.LocationsFragment;
import com.example.mbankole.tripplanner.fragments.MapFragment;
import com.example.mbankole.tripplanner.fragments.MapView;
import com.example.mbankole.tripplanner.fragments.PeopleFragment;
import com.example.mbankole.tripplanner.fragments.PlanFragment;

/**
 * Created by mbankole on 7/11/17.
 *
 */

public class PlanFragmentPagerAdapter  extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Map", "List"};
    private Context context;
    FragmentManager fragmentManager;
    LocationsFragment locationsFragment;
    MapFragment mapFragment;
    PeopleFragment peopleFragment;
    PlanFragment planFragment;
    ListView listView;
    MapView mapView;

    public PlanFragmentPagerAdapter(FragmentManager fm, Context context) {
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
            mapFragment = new MapFragment();
            mapFragment.setFm(fragmentManager);
        }
        return mapFragment;
    }

    public PeopleFragment getPeopleFragment() {
        if (peopleFragment == null) {
            peopleFragment = PeopleFragment.newInstance();
        }
        return peopleFragment;
    }

    public PlanFragment getPlanFragment() {
        if (planFragment == null) {
            planFragment = PlanFragment.newInstance();
            //planFragment.viewPager = this;
        }
        return planFragment;
    }


    public ListView getListView() {
        if (listView == null) {
            listView = ListView.newInstance();
            //planFragment.viewPager = this;
        }
        return listView;
    }


    public MapView getMapView() {
        if (mapView == null) {
            mapView = MapView.newInstance();
            //mapView.viewPager = this;
        }
        return mapView;
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
            case 3:
                return getPlanFragment();
            case 4:
                return getListView();
            case 5:
                return getMapView();
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


/**
 * Created by ericar on 7/11/17.
 *
 */

