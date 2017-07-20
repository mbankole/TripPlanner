package com.example.mbankole.tripplanner.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.adapters.NewExploreFragmentPagerAdapter;
import com.example.mbankole.tripplanner.models.Plan;

import java.util.ArrayList;

public class NewExploreActivity extends AppCompatActivity {

    Context context;
    ArrayList<Plan> plans;
    ViewPager viewPager;
    NewExploreFragmentPagerAdapter fragmentPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_plans);

        context = this;
        plans = new ArrayList<>();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fragmentPager = new NewExploreFragmentPagerAdapter(getSupportFragmentManager(), NewExploreActivity.this);
        viewPager.setAdapter(fragmentPager);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        int[] icons = {
                R.drawable.ic_globe,
                R.drawable.ic_marker_black,
                R.drawable.ic_user_black
        };
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(icons[i]);
        }
        //viewPager.setCurrentItem(0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Intent i = new Intent(MainActivity.this, MapDemoActivity.class);
        //startActivity(i);
    }
}
