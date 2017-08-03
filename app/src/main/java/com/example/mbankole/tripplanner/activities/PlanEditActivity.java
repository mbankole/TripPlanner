package com.example.mbankole.tripplanner.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.adapters.PlanEditPagerAdapter;
import com.example.mbankole.tripplanner.fragments.PlanEditTextFragment;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.models.Plan;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.ArrayList;

/**
 * Created by mbankole on 7/20/17.
 */

public class PlanEditActivity extends AppCompatActivity implements PlanEditTextFragment.PlanEditTextListener {

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    private GoogleApiClient mGoogleApiClient;

    public PlanEditPagerAdapter fragmentPager;
    ArrayList<String> people;
    ArrayList<Location> places;
    public ViewPager viewPager;
    Context context;
    public Plan plan;
    int position;
    FloatingActionButton fabDone;
    final Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager.getCurrentItem() == 2) {
                fabDone.setVisibility(View.GONE);
                handler.postDelayed(this, 200);
            }
            else {
                fabDone.setVisibility(View.VISIBLE);
                handler.postDelayed(this, 200);
            }
        }
    };

    private static final String TAG = "PLANEDITACTIVITY";

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        boolean newPlan = false;

        //if plan is null, make a new empty one
        plan = getIntent().getParcelableExtra("plan");
        position = getIntent().getIntExtra("position", -1);
        if (plan == null) {
            String creatorUid = getIntent().getStringExtra("creatorUID");
            String creatorUserName = getIntent().getStringExtra("creatorUserName");
            plan = Plan.newPlan(creatorUid, creatorUserName);
            newPlan = true;
        }

        context = this;

        people = plan.people;
        places = plan.places;

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fragmentPager = new PlanEditPagerAdapter(getFragmentManager(), getSupportFragmentManager(), plan);
        viewPager.setAdapter(fragmentPager);
        fragmentPager.planEditActivity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.logo_white);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.logo_white);
        actionBar.setTitle("");

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        int[] icons = {
                R.drawable.ic_marker_black,
                R.drawable.ic_list,
                R.drawable.ic_chat,
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
                i.putExtra("position", position);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        Menu menu = toolbar.getMenu();
        onCreateOptionsMenu(menu);

        if (!newPlan) {
            viewPager.setCurrentItem(1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_plan_edit, menu);

        MenuItem miSearch = menu.findItem(R.id.miSearch);
        final PlanEditActivity planEditActivity = this;
        miSearch.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(planEditActivity);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    Log.e(TAG, "onMenuItemClick: Play services broken", e);
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, "onMenuItemClick: Play services not available", e);
                }
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                viewPager.setCurrentItem(0);
                fragmentPager.getPlanMapFragment().zoomPlace(place);
                Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.post(runnable);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onFinishEditText(Plan plan) {
        this.plan.title = plan.title;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo_white);
        getSupportActionBar().setTitle("");
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
        refresh();
    }
}