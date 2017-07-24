package com.example.mbankole.tripplanner.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mbankole.tripplanner.fragments.PlaceholderFragment;
import com.example.mbankole.tripplanner.fragments.ProfileFriendsListFragment;
import com.example.mbankole.tripplanner.fragments.ProfileLocationsListFragment;
import com.example.mbankole.tripplanner.fragments.ProfilePlansListFragment;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.models.Plan;
import com.example.mbankole.tripplanner.models.User;

import java.util.ArrayList;

/**
 * Created by mbankole on 7/21/17.
 */

public class ProfileFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"plans", "friends", "interests"};
    FragmentManager fragmentManager;
    ProfilePlansListFragment profilePlansListFragment;
    ProfileFriendsListFragment profileFriendsListFragment;
    ProfileLocationsListFragment profileLocationsListFragment;
    ArrayList<Plan> plans;
    ArrayList<User> friends;
    ArrayList<Location> interests;

    public ProfileFragmentPagerAdapter(FragmentManager fm, ArrayList<Plan> plans, ArrayList<User> friends, ArrayList<Location> interests) {
        super(fm);
        fragmentManager = fm;
        this.plans = plans;
        this.friends = friends;
        this.interests = interests;
    }

    public ProfilePlansListFragment getProfilePlansListFragment() {
        if (profilePlansListFragment == null) {
            profilePlansListFragment = ProfilePlansListFragment.newInstance();
            profilePlansListFragment.plans = plans;
        }
        return profilePlansListFragment;
    }

    public ProfileFriendsListFragment getProfileFriendsListFragment() {
        if (profileFriendsListFragment == null) {
            profileFriendsListFragment = ProfileFriendsListFragment.newInstance();
            profileFriendsListFragment.friends = friends;
        }
        return profileFriendsListFragment;
    }

    public ProfileLocationsListFragment getProfileLocationsListFragment() {
        if (profileLocationsListFragment == null) {
            //profileLocationsListFragment = ProfileLocationsListFragment.newInstance();
            //profileLocationsListFragment.locations = interests;
        }
        return profileLocationsListFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                //return new PlaceholderFragment();
                return getProfilePlansListFragment();
            case 1:
                //return new PlaceholderFragment();
                return getProfileFriendsListFragment();
            case 2:
                return new PlaceholderFragment();
                //return getProfileLocationsListFragment();
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