package com.example.mbankole.tripplanner.activities;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.adapters.ProfileFragmentPagerAdapter;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    TextView tvUsername;
    ImageView ivProfileImage;
    User user;
    ProfileFragmentPagerAdapter fragmentPager;
    ViewPager viewPager;
    ArrayList<String> friends;
    ArrayList<Location> interests;
    ArrayList<String> plans;
    ImageButton btFriend;
    Context context;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    User vUser;
    private DatabaseReference mDatabase;
    boolean isCurrentUser = false;
    final static String TAG = "PROFILEACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        context = this;

        user = getIntent().getParcelableExtra("user");
        if (user == null) {
            user = User.generateChandler(getApplicationContext());
        }

        friends = user.friends;
        interests = user.interests;
        plans = user.plans;

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fragmentPager = new ProfileFragmentPagerAdapter(getSupportFragmentManager(), user);
        viewPager.setAdapter(fragmentPager);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser.getUid().equals(user.getUid())) isCurrentUser = true;

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        int[] icons = {
                R.drawable.ic_globe,
                R.drawable.ic_users
        };
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(icons[i]);
        }

        tvUsername = (TextView) findViewById(R.id.tvUsername);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        btFriend = (ImageButton) findViewById(R.id.btFriend);

        if (isCurrentUser) btFriend.setVisibility(View.GONE);
        else {
            btFriend.setVisibility(View.VISIBLE);
            if (user.friends.contains(currentUser.getUid())) {
                btFriend.setBackground(this.getDrawable(R.drawable.ic_unfriend));
                btFriend.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.darkGrey)));
            }
            else {
                btFriend.setBackground(this.getDrawable(R.drawable.ic_add_friend));
                btFriend.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.colorAccent)));
            }
            btFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user.friends.contains(currentUser.getUid())) {
                        //unfriend them
                        vUser.friends.remove(user.getUid());
                        user.friends.remove(vUser.getUid());
                        mDatabase.child("users").child(vUser.getUid()).setValue(vUser);
                        mDatabase.child("users").child(user.getUid()).setValue(user);
                        btFriend.setBackground(context.getDrawable(R.drawable.ic_add_friend));
                        btFriend.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorAccent)));
                    }
                    else {
                        //friend them
                        vUser.friends.add(user.getUid());
                        user.friends.add(vUser.getUid());
                        mDatabase.child("users").child(vUser.getUid()).setValue(vUser);
                        mDatabase.child("users").child(user.getUid()).setValue(user);
                        btFriend.setBackground(context.getDrawable(R.drawable.ic_unfriend));
                        btFriend.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.darkGrey)));
                    }
                }
            });
        }

        tvUsername.setText(user.name);
        if (user.imageUrl != null) {
            Picasso.with(this)
                    .load(user.imageUrl)
                    .fit()
                    .into(ivProfileImage);
        }

        DatabaseReference ref = mDatabase.child("users");

        Query userQuery = ref.orderByChild("uid").equalTo(currentUser.getUid());

        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                    vUser = singleSnapshot.getValue(User.class);
                    fixUser(vUser);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: shits fucked");
            }
        });
    }

    void fixUser(User user) {
        if (user.interests == null) user.interests = new ArrayList<>();
        if (user.friends == null) user.friends = new ArrayList<>();
        if (user.plans == null) user.plans = new ArrayList<>();
    }
}