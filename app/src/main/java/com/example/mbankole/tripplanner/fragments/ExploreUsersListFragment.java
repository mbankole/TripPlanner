package com.example.mbankole.tripplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.adapters.UserAdapter;
import com.example.mbankole.tripplanner.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by mbankole on 7/20/17.
 */

public class ExploreUsersListFragment extends Fragment {

    public ExploreUsersListFragment() {
    }

    UserAdapter userAdapter;
    RecyclerView rvUsers;
    ArrayList<User> friends;
    android.app.FragmentManager fm;
    private DatabaseReference mDatabase;

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

        mDatabase = FirebaseDatabase.getInstance().getReference();

        // find RecyclerView
        rvUsers = (RecyclerView) v.findViewById(R.id.rvUsers);
        // init the arraylist (data source)
        friends = new ArrayList<>();
        // construct the adapter from this data source
        userAdapter = new UserAdapter(friends);
        userAdapter.setFm(fm);
        // RecyclerView setup (layout manager, use adapter)
        rvUsers.setLayoutManager(new GridLayoutManager(getContext(), 2));
        // set the adapter
        rvUsers.setAdapter(userAdapter);
        /*for (int i=0; i < 1; i++) {
            //User friend = new User();
            friends.add(User.generateChandler(getContext()));
            friends.add(User.generateJoey());
            friends.add(User.generateMonica());
            friends.add(User.generatePhoebe());
            friends.add(User.generateRachel());
            friends.add(User.generateRoss());
        }*/
        getUsers();
        return v;
    }

    public void refresh() {
        userAdapter.notifyDataSetChanged();
    }

    public void getUsers() {
        DatabaseReference ref = mDatabase.child("users");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                    User user = singleSnapshot.getValue(User.class);
                    fixUser(user);
                    friends.add(0, user);
                    userAdapter.notifyItemInserted(friends.size() - 1);
                    rvUsers.smoothScrollToPosition(0);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: shits fucked");
            }
        });
    }

    void fixUser(User user) {
        if (user.interests == null) user.interests = new ArrayList<>();
        if (user.friends == null) user.friends = new ArrayList<>();
        if (user.plans == null) user.plans = new ArrayList<>();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {}
}