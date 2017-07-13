package com.example.mbankole.tripplanner;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.mbankole.tripplanner.adapters.MainFragmentPagerAdapter;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.models.User;

import java.util.ArrayList;

public class ExploreActivity extends AppCompatActivity {

    MainFragmentPagerAdapter fragmentPager;
    ArrayList<User> people;
    ArrayList<Location> places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        people = new ArrayList<>();
        places = new ArrayList<>();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        fragmentPager = new MainFragmentPagerAdapter(getSupportFragmentManager(),
                ExploreActivity.this);
        viewPager.setAdapter(fragmentPager);
        fragmentPager.exploreActivity = this;

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //Intent i = new Intent(MainActivity.this, MapDemoActivity.class);
        //startActivity(i);
    }

    public void addUser(User user) {
        people.add(user);
        String ToastString = "";
        for (int i=0; i<people.size(); i++) {
            ToastString += people.get(i).name;
        }
        Toast toast = Toast.makeText(this, ToastString, Toast.LENGTH_LONG);
        toast.show();
    }

    public void addLocation(Location location) {
        places.add(location);
        String ToastString = "";
        for (int i=0; i<places.size(); i++) {
            ToastString += places.get(i).name;
        }
        Toast toast = Toast.makeText(this, ToastString, Toast.LENGTH_LONG);
        toast.show();
    }
}
