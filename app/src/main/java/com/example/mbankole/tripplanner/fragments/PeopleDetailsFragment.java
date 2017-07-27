package com.example.mbankole.tripplanner.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.models.User;

/**
 * Created by danahe97 on 7/12/17.
 */

public class PeopleDetailsFragment extends DialogFragment {

    TextView tvUsername;
    TextView tvInterests;
    ImageButton ibAddUser;
    String interestsString;


    public static PeopleDetailsFragment newInstance(User user) {
        PeopleDetailsFragment frag = new PeopleDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("user", user);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_people_detail, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        final User user = getArguments().getParcelable("user");
        tvUsername = (TextView) view.findViewById(R.id.tvUserDetailsName);
        tvInterests = (TextView) view.findViewById(R.id.tvInterests);
        ibAddUser = (ImageButton) view.findViewById(R.id.ibAddUser);
        /*ibAddUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                exploreActivity.addUser(user);

            }
        });*/
        if (user.interests != null) {
            interestsString = "";
            for (int i = 0; i < user.interests.size(); i++) {
                Location location = user.interests.get(i);
                interestsString += location.name + ", ";
            }
        } else {
            interestsString = "This user has not added any interests.";
        }
        tvUsername.setText(user.name);
        tvInterests.setText(interestsString);

    }
}
//people details