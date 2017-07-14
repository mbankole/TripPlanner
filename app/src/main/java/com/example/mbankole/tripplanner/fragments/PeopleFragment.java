package com.example.mbankole.tripplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mbankole.tripplanner.ExploreActivity;
import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.adapters.UserAdapter;
import com.example.mbankole.tripplanner.models.User;

import java.util.ArrayList;

/**
 * Created by mbankole on 7/11/17.
 *
 */

public class PeopleFragment extends Fragment {

    public PeopleFragment() {
    }

    UserAdapter userAdapter;
    ArrayList<User> users;
    RecyclerView rvUsers;
    ArrayList<User> friends;
    android.app.FragmentManager fm;
    public ExploreActivity exploreActivity;

    public static PeopleFragment newInstance() {
        Bundle args = new Bundle();
        //args.putParcelable("user", user);
        PeopleFragment fragment = new PeopleFragment();
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
        userAdapter.exploreActivity = exploreActivity;
        // RecyclerView setup (layout manager, use adapter)
        rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        // set the adapter
        rvUsers.setAdapter(userAdapter);
        for (int i=0; i < 20; i++) {
            User friend = new User();
            friends.add(User.generateAdam());
            friends.add(User.generatePhilp());
            friends.add(User.generateTom());
        }

        //Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        //((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //setHasOptionsMenu(true);

        addItems(friends);
        return v;
    }
    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_people, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        MenuItem miProfile = menu.findItem(R.id.miProfile);
        miProfile.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }*/

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