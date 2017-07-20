package com.example.mbankole.tripplanner;
//
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
import android.widget.Toast;

import com.example.mbankole.tripplanner.adapters.PlanFragmentPagerAdapter;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.models.User;

import java.util.ArrayList;

public class PlanActivity extends AppCompatActivity {

    PlanFragmentPagerAdapter fragmentPager;
    ArrayList<User> people;
    ArrayList<Location> places;
    ViewPager viewPager;
    public ExploreActivity exploreActivity;
    Context context;

//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        Intent getI = getIntent();
        people = getI.getParcelableArrayListExtra("people");
        places = getI.getParcelableArrayListExtra("places");
        
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fragmentPager = new PlanFragmentPagerAdapter(getSupportFragmentManager(),
                PlanActivity.this);
        fragmentPager.planActivity = this;
        fragmentPager.exploreActivity = exploreActivity;
        viewPager.setAdapter(fragmentPager);
        fragmentPager.people = people;
        fragmentPager.places = places;

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_plan, menu);
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
                return false;
            }
        });
        MenuItem miExplore = menu.findItem(R.id.miPlan);
        miExplore.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                finish();
                return false;
            }
        });

        return true;
    }

    public void refresh() {
        fragmentPager.getPlanListFragment().refresh();
        fragmentPager.getPlanMapFragment().refresh();
    }

    public void removeLocation(Location location) {
        places.remove(location);
        String ToastString = "";
        for (int i=0; i<places.size(); i++) {
            ToastString += places.get(i).name;
        }
        Toast toast = Toast.makeText(this, ToastString, Toast.LENGTH_LONG);
        toast.show();
        fragmentPager.getPlanListFragment().refresh();
    }
}
//
//
//
//
