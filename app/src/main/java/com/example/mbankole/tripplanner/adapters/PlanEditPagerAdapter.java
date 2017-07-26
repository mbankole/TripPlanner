package com.example.mbankole.tripplanner.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mbankole.tripplanner.activities.PlanEditActivity;
import com.example.mbankole.tripplanner.fragments.PlanListFragment;
import com.example.mbankole.tripplanner.fragments.PlanMapFragment;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.models.User;

import java.util.ArrayList;

/**
 * Created by mbankole on 7/20/17.
 */

public class PlanEditPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"", ""};
    FragmentManager fragmentManager;
    PlanListFragment planListFragment;
    PlanMapFragment planMapFragment;
    public ArrayList<User> people;
    public ArrayList<Location> places;
    public PlanEditActivity planEditActivity;

    public PlanEditPagerAdapter(FragmentManager fm, ArrayList<User> people, ArrayList<Location> places) {
        super(fm);
        fragmentManager = fm;
        this.people = people;
        this.places = places;
    }

    public PlanListFragment getPlanListFragment() {
        if (planListFragment == null) {
            planListFragment = PlanListFragment.newInstance();
            planListFragment.locations = places;
            planListFragment.planEditActivity = planEditActivity;
            //planFragment.viewPager = this;
        }
        return planListFragment;
    }

    public PlanMapFragment getPlanMapFragment() {
        if (planMapFragment == null) {
            planMapFragment = PlanMapFragment.newInstance();
            planMapFragment.people = people;
            planMapFragment.places = places;
            planMapFragment.planEditActivity = planEditActivity;
            planMapFragment.setFm(fragmentManager);
            //planMapFragment.viewPager = this;
        }
        return planMapFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return getPlanMapFragment();
            case 1:
                return getPlanListFragment();
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