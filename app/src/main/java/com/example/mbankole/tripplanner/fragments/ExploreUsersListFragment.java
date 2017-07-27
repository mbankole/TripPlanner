package com.example.mbankole.tripplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.adapters.UserAdapter;
import com.example.mbankole.tripplanner.models.User;

import java.util.ArrayList;

/**
 * Created by mbankole on 7/20/17.
 */

public class ExploreUsersListFragment extends Fragment {

    public ExploreUsersListFragment() {
    }

    UserAdapter userAdapter;
    ArrayList<User> users;
    RecyclerView rvUsers;
    ArrayList<User> friends;
    android.app.FragmentManager fm;

    public static ExploreUsersListFragment newInstance() {
        Bundle args = new Bundle();
        //args.putParcelable("user", user);
        ExploreUsersListFragment fragment = new ExploreUsersListFragment();
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
        users = new ArrayList<>();
        friends = new ArrayList<>();
        // construct the adapter from this data source
        userAdapter = new UserAdapter(users);
        userAdapter.setFm(fm);
        // RecyclerView setup (layout manager, use adapter)
        rvUsers.setLayoutManager(new GridLayoutManager(getContext(), 2));
        // set the adapter
        rvUsers.setAdapter(userAdapter);
        for (int i=0; i < 1; i++) {
            //User friend = new User();
            friends.add(User.generateChandler(getContext()));
            friends.add(User.generateJoey());
            friends.add(User.generateMonica());
            friends.add(User.generatePhoebe());
            friends.add(User.generateRachel());
            friends.add(User.generateRoss());
        }

        addItems(friends);
        return v;
    }

    public void refresh() {
        userAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {}

    public void addItems(ArrayList<User> response) {
        for (int i = 0; i < response.size(); i++) {
            User user = response.get(i);
            users.add(user);
            userAdapter.notifyItemInserted(users.size() - 1);
        }
    }
}