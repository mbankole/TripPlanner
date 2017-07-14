package com.example.mbankole.tripplanner;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.mbankole.tripplanner.adapters.PlanFragmentPagerAdapter;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.models.User;

import java.util.ArrayList;

public class PlanActivity extends AppCompatActivity {

    PlanFragmentPagerAdapter fragmentPager;
    ArrayList<User> people;
    ArrayList<Location> places;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        people = new ArrayList<>();
        places = new ArrayList<>();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        fragmentPager = new PlanFragmentPagerAdapter(getSupportFragmentManager(),
                PlanActivity.this);
        viewPager.setAdapter(fragmentPager);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //Intent i = new Intent(MainActivity.this, MapDemoActivity.class);
        //startActivity(i);
    }
}
