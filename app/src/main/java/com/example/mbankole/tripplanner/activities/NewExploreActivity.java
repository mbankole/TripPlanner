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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.adapters.NewExploreFragmentPagerAdapter;
import com.example.mbankole.tripplanner.adapters.PlanAdapter;
import com.example.mbankole.tripplanner.fragments.ExplorePlansListFragment;
import com.example.mbankole.tripplanner.models.Plan;
import com.example.mbankole.tripplanner.models.User;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewExploreActivity extends AppCompatActivity {

    final static String TAG = "EXPLOREACTIVITY";

    Context context;
    ArrayList<Plan> plans;
    ViewPager viewPager;
    NewExploreFragmentPagerAdapter fragmentPager;
    private FirebaseAuth mAuth;
    private FirebaseAnalytics mFirebaseAnalytics;
    private DatabaseReference mDatabase;
    User user;
    boolean recievedUser;
    Plan testPlan;


    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_plans);

        context = this;
        plans = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        currentUser = mAuth.getCurrentUser();

        mFirebaseAnalytics.setUserProperty("name", currentUser.getDisplayName());

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fragmentPager = new NewExploreFragmentPagerAdapter(getSupportFragmentManager(), plans);
        viewPager.setAdapter(fragmentPager);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = mDatabase.child("users");

        Query userQuery = ref.orderByChild("uid").equalTo(currentUser.getUid());

        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                    user = singleSnapshot.getValue(User.class);
                    fixUser(user);
                }
                recievedUser = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: shits fucked");
                recievedUser = true;
            }
        });

        //mDatabase.child("test").child(currentUser.getUid()).setValue(Plan.generateSeattlePlan(context));

        /*DatabaseReference ref = mDatabase.child("test");

        Query planQuery = ref.orderByChild("title").equalTo("Trip to Seattle");

        planQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                    //testPlan = singleSnapshot.getValue(Plan.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: shits fucked");
            }
        });*/


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

        Toast.makeText(context, "Signed in as " + currentUser.getDisplayName(),
                Toast.LENGTH_LONG).show();
        Toast.makeText(context, "Signed in as " + currentUser.getUid(),
                Toast.LENGTH_LONG).show();
    }

    void fixUser(User user) {
        if (user.interests == null) user.interests = new ArrayList<>();
        if (user.friends == null) user.friends = new ArrayList<>();
        if (user.plans == null) user.plans = new ArrayList<>();
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
                intent.putExtra("user", user);
                startActivity(intent);
                return false;
            }
        });

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ExplorePlansListFragment.PLAN_REQUEST_CODE){
            Plan newPlan = data.getExtras().getParcelable("plan");
            plans.add(0, newPlan);
            fragmentPager.refreshAdd();
        }
        if (resultCode == RESULT_OK && requestCode == PlanAdapter.EDIT_PLAN_REQUEST_CODE){
            Plan newPlan = data.getExtras().getParcelable("plan");
            int position = data.getExtras().getInt("position");
            plans.set(position, newPlan);
            fragmentPager.refresh();
        }
    }
}
