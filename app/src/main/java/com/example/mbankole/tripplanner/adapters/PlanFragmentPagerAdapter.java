package com.example.mbankole.tripplanner.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mbankole.tripplanner.PlanActivity;
import com.example.mbankole.tripplanner.fragments.PlanListFragment;
import com.example.mbankole.tripplanner.fragments.PlanMapFragment;
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
    public PlanActivity planActivity;
    PlanListFragment planListFragment;
    PlanMapFragment planMapFragment;
    public ArrayList<User> people;
    public ArrayList<Location> places;

    public PlanFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        fragmentManager = fm;
        this.context = context;
    }

    public PlanListFragment getPlanListFragment() {
        if (planListFragment == null) {
            planListFragment = PlanListFragment.newInstance();
            planListFragment.people = people;
            planListFragment.places = places;
            planListFragment.planActivity = planActivity;
            //planFragment.viewPager = this;
        }
        return planListFragment;
    }

    public PlanMapFragment getPlanMapFragment() {
        if (planMapFragment == null) {
            planMapFragment = PlanMapFragment.newInstance();
            planMapFragment.people = people;
            planMapFragment.places = places;
            planMapFragment.planActivity = planActivity;
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


/**
 * Created by ericar on 7/11/17.
 *
 */

