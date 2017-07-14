package com.example.mbankole.tripplanner.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mbankole.tripplanner.fragments.ListView;
import com.example.mbankole.tripplanner.fragments.MapView;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.models.User;

import java.util.ArrayList;

/**
 * Created by mbankole on 7/11/17.
 *
 */

public class PlanFragmentPagerAdapter  extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Map", "List"};
    private Context context;
    FragmentManager fragmentManager;
    ListView listView;
    MapView mapView;
    public ArrayList<User> people;
    public ArrayList<Location> places;

    public PlanFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        fragmentManager = fm;
        this.context = context;
    }

    public ListView getListView() {
        if (listView == null) {
            listView = ListView.newInstance();
            listView.people = people;
            listView.places = places;
            //planFragment.viewPager = this;
        }
        return listView;
    }

    public MapView getMapView() {
        if (mapView == null) {
            mapView = MapView.newInstance();
            mapView.people = people;
            mapView.places = places;
            //mapView.viewPager = this;
        }
        return mapView;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return getMapView();
            case 1:
                return getListView();
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

