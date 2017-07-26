package com.example.mbankole.tripplanner.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.adapters.PlanEditPagerAdapter;
import com.example.mbankole.tripplanner.fragments.PlanEditTextFragment;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.models.Plan;
import com.example.mbankole.tripplanner.models.User;

import java.util.ArrayList;

/**
 * Created by mbankole on 7/20/17.
 */

public class PlanEditActivity extends AppCompatActivity implements PlanEditTextFragment.PlanEditTextListener {

    PlanEditPagerAdapter fragmentPager;
    ArrayList<User> people;
    ArrayList<Location> places;
    ViewPager viewPager;
    Context context;
    Plan plan;
    FloatingActionButton fabDone;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);



        //if plan is null, make a new empty one
        plan = getIntent().getParcelableExtra("plan");
        if (plan == null) {
            String creatorUid = getIntent().getStringExtra("creatorUid");
            plan = Plan.newPlan(creatorUid);
        }

        context = this;

        people = plan.people;
        places = plan.places;

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fragmentPager = new PlanEditPagerAdapter(getSupportFragmentManager(), plan.people, plan.places);
        viewPager.setAdapter(fragmentPager);
        fragmentPager.planEditActivity = this;
        fragmentPager.people = plan.people;
        fragmentPager.places = plan.places;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(plan.title);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        int[] icons = {
                R.drawable.ic_marker_black,
                R.drawable.ic_list,
        };
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(icons[i]);
        }

        fabDone = (FloatingActionButton) findViewById(R.id.fabDone);
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("plan", plan);
                setResult(RESULT_OK, i);
                finish();
            }
        });
        //Intent i = new Intent(MainActivity.this, MapDemoActivity.class);
        //startActivity(i);
        Menu menu = toolbar.getMenu();
        onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_plan_edit, menu);
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
        MenuItem miEditText = menu.findItem(R.id.miEditText);
        miEditText.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                PlanEditTextFragment frag = PlanEditTextFragment.newInstance(plan.title, plan.description);
                frag.show(getSupportFragmentManager(), "edit");
                return false;
            }
        });
        /*MenuItem miSave = menu.findItem(R.id.miSave);
        miSave.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent data = new Intent();
                data.putExtra("plan", plan);
                setResult(RESULT_OK, data);
                finish();
                return false;
            }
        });*/

        return true;
    }

    @Override
    public void onFinishEditText(Plan plan) {
        this.plan.title = plan.title;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(plan.title);
        this.plan.description = plan.description;
    }

    public void refresh() {
        fragmentPager.getPlanListFragment().refresh();
        fragmentPager.getPlanMapFragment().refresh();
    }

    public void refreshAdd() {
        fragmentPager.getPlanListFragment().addItem();
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