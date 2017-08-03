package com.example.mbankole.tripplanner.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mbankole.tripplanner.fragments.PlaceholderFragment;
import com.example.mbankole.tripplanner.fragments.ProfileFriendsListFragment;
import com.example.mbankole.tripplanner.fragments.ProfilePlansListFragment;
import com.example.mbankole.tripplanner.models.User;

/**
 * Created by mbankole on 7/21/17.
 */

public class ProfileFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"", "", ""};
    FragmentManager fragmentManager;
    ProfilePlansListFragment profilePlansListFragment;
    ProfileFriendsListFragment profileFriendsListFragment;
    User user;

    public ProfileFragmentPagerAdapter(FragmentManager fm, User user) {
        super(fm);
        fragmentManager = fm;
        this.user = user;
    }

    public ProfilePlansListFragment getProfilePlansListFragment() {
        if (profilePlansListFragment == null) {
            profilePlansListFragment = ProfilePlansListFragment.newInstance();
            profilePlansListFragment.user = user;
        }
        return profilePlansListFragment;
    }

    public ProfileFriendsListFragment getProfileFriendsListFragment() {
        if (profileFriendsListFragment == null) {
            profileFriendsListFragment = ProfileFriendsListFragment.newInstance();
            profileFriendsListFragment.user = user;
        }
        return profileFriendsListFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return getProfilePlansListFragment();
            case 1:
                return getProfileFriendsListFragment();
            case 2:
                return new PlaceholderFragment();
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