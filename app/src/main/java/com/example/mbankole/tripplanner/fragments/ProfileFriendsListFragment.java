package com.example.mbankole.tripplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.adapters.UserAdapter;
import com.example.mbankole.tripplanner.models.User;

import java.util.ArrayList;

/**
 * Created by mbankole on 7/21/17.
 */

public class ProfileFriendsListFragment extends Fragment {
    public ProfileFriendsListFragment() {
    }

    UserAdapter userAdapter;
    RecyclerView rvUsers;
    public ArrayList<User> friends;
    android.app.FragmentManager fm;

    public static ProfileFriendsListFragment newInstance() {
        Bundle args = new Bundle();
        //args.putParcelable("user", user);
        ProfileFriendsListFragment fragment = new ProfileFriendsListFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the layout
        View v = inflater.inflate(R.layout.fragment_people, container, false);

        fm = getActivity().getFragmentManager();
        // find RecyclerView
        rvUsers = (RecyclerView) v.findViewById(R.id.rvUsers);
        // init the arraylist (data source)
        //friends = new ArrayList<>();
        // construct the adapter from this data source
        userAdapter = new UserAdapter(friends);
        userAdapter.setFm(fm);
        // RecyclerView setup (layout manager, use adapter)
        rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        // set the adapter
        rvUsers.setAdapter(userAdapter);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {}

    public void addItems(ArrayList<User> response) {
        for (int i = 0; i < response.size(); i++) {
            User user = response.get(i);
            friends.add(user);
            userAdapter.notifyItemInserted(friends.size() - 1);
        }
    }
}
