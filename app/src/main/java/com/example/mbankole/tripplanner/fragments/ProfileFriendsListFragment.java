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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by mbankole on 7/21/17.
 */

public class ProfileFriendsListFragment extends Fragment {
    public ProfileFriendsListFragment() {
    }

    UserAdapter userAdapter;
    RecyclerView rvUsers;
    public User user;
    public ArrayList<User> friends;
    android.app.FragmentManager fm;

    private FirebaseAuth mAuth;
    private FirebaseAnalytics mFirebaseAnalytics;
    private DatabaseReference mDatabase;

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

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        fm = getActivity().getFragmentManager();
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
        getFriends();
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

    void fixUser(User user) {
        if (user.interests == null) user.interests = new ArrayList<>();
        if (user.friends == null) user.friends = new ArrayList<>();
        if (user.plans == null) user.plans = new ArrayList<>();
    }

    public void getFriends() {
        DatabaseReference ref = mDatabase.child("users");

        for (String userId: user.friends) {
            Query userQuery = ref.orderByChild("uid").equalTo(userId);

            userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                        user = singleSnapshot.getValue(User.class);
                        fixUser(user);
                        friends.add(user);
                        userAdapter.notifyItemInserted(friends.size() - 1);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "onCancelled: shits fucked");
                }
            });
        }
    }
}
