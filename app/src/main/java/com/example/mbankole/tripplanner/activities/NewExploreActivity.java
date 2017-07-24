package com.example.mbankole.tripplanner.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

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
        fragmentPager = new NewExploreFragmentPagerAdapter(getSupportFragmentManager(), plans);
        viewPager.setAdapter(fragmentPager);


        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        /*int[] icons = {
                R.drawable.ic_globe,
                R.drawable.ic_user_black
        };
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(icons[i]);
        }*/
        //viewPager.setCurrentItem(0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Menu menu = toolbar.getMenu();
        onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_new_explore, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //searchView.setVisibility(View.GONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                switch (viewPager.getCurrentItem()) {
                    case 0:
                        //map view
                    case 1:
                        //
                    case 2:
                        //people view
                }
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
                Intent intent = new Intent(context, com.example.mbankole.tripplanner.activities.ProfileActivity.class);
                startActivity(intent);
                return false;
            }
        });

        return true;
    }
}
