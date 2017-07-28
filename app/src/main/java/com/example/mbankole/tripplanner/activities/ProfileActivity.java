package com.example.mbankole.tripplanner.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.adapters.ProfileFragmentPagerAdapter;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.models.Plan;
import com.example.mbankole.tripplanner.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    TextView tvUsername;
    ImageView ivProfileImage;
    User user;
    ProfileFragmentPagerAdapter fragmentPager;
    ViewPager viewPager;
    ArrayList<String> friends;
    ArrayList<Location> interests;
    ArrayList<String> plans;
    Button btFriend;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private DatabaseReference mDatabase;
    boolean isCurrentUser = false;
    final static String TAG = "PROFILEACTIVITY";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = getIntent().getParcelableExtra("user");
        if (user == null) {
            user = User.generateChandler(getApplicationContext());
        }

        friends = user.friends;
        interests = user.interests;
        plans = user.plans;

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fragmentPager = new ProfileFragmentPagerAdapter(getSupportFragmentManager(), user);
        viewPager.setAdapter(fragmentPager);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser.getUid().equals(user.getUid())) isCurrentUser = true;

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        tvUsername = (TextView) findViewById(R.id.tvUsername);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        btFriend = (Button) findViewById(R.id.btFriend);

        if (isCurrentUser) btFriend.setVisibility(View.GONE);
        else {
            btFriend.setVisibility(View.VISIBLE);
        }

        tvUsername.setText(user.name);
        if (user.imageUrl != null) {
            Picasso.with(this)
                    .load(user.imageUrl)
                    .fit()
                    .into(ivProfileImage);
        }

        //loadPlans();
    }

    /*public void loadPlans() {
        DatabaseReference ref = mDatabase.child("plans");

        Query planQuery = ref.orderByChild("creatorUid").equalTo(user.getUid());

        planQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                    Plan plan = singleSnapshot.getValue(Plan.class);
                    fixPlan(plan);
                    plans.add(0, plan);
                    fragmentPager.getProfilePlansListFragment().refreshAdd();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: shits fucked");
            }
        });
    }*/

    void fixPlan(Plan plan) {
        if (plan.people == null) plan.people = new ArrayList<>();
        if (plan.places == null) plan.places = new ArrayList<>();
    }
}