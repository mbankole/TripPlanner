package com.example.mbankole.tripplanner;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.mbankole.tripplanner.fragments.MainFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {

    MainFragmentPagerAdapter fragmentPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        fragmentPager = new MainFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this);
        viewPager.setAdapter(fragmentPager);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        //Intent i = new Intent(MainActivity.this, MapDemoActivity.class);
        //startActivity(i);
    }
}
