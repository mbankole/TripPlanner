package com.example.mbankole.tripplanner.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.adapters.ProfileFragmentPagerAdapter;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.models.Plan;
import com.example.mbankole.tripplanner.models.User;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    TextView tvUsername;
    ImageView ivProfileImage;
    User user;
    ProfileFragmentPagerAdapter fragmentPager;
    ViewPager viewPager;
    ArrayList<User> friends;
    ArrayList<Location> interests;
    ArrayList<Plan> plans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = getIntent().getParcelableExtra("user");
        if (user == null) {
            user = User.generateAdam(getApplicationContext());
        }

        friends = user.friends;
        interests = user.interests;
        plans = user.plans;

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fragmentPager = new ProfileFragmentPagerAdapter(getSupportFragmentManager(), plans, friends, interests);
        viewPager.setAdapter(fragmentPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        tvUsername = (TextView) findViewById(R.id.tvUsername);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

        tvUsername.setText(user.name);

    }
}
