package com.example.mbankole.tripplanner.fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.activities.PlanEditActivity;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.utility.gradient;
import com.squareup.picasso.Picasso;

/**
 * Created by mbankole on 7/12/17.
 *
 */
//make a close button
//make on click listener
    //



public class LocationDetailFragment extends DialogFragment implements  View.OnClickListener{
    TextView tvName;
    TextView tvAddress;
    TextView tvPhone;
    TextView tvNowOpen;
    TextView tvHours;
    TextView tvprice;
    ImageView ivPhoto;
    Button btAdd;
    ImageButton btRemove;
    Button btClose;


    public PlanMapFragment planMapFragment;
    PlanEditActivity planEditActivity;

    private final String TAG = "POIDETAILFRAGMENT";

    public LocationDetailFragment() {}

    public static LocationDetailFragment newInstance(Location loc, Boolean plan) {
        LocationDetailFragment frag = new LocationDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("location", loc);
        args.putBoolean("plan", plan);
        frag.setArguments(args);
        return frag;
    }
    //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location_detail, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        final View viewref = view;
        final Location loc = getArguments().getParcelable("location");
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvAddress = (TextView) view.findViewById(R.id.tvAddress);
        tvHours = (TextView) view.findViewById(R.id.tvHours);
        tvPhone = (TextView) view.findViewById(R.id.tvPhone);
        tvNowOpen = (TextView) view.findViewById(R.id.tvNowOpen);
        tvprice = (TextView) view.findViewById(R.id.tvPrice);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        btAdd = (Button) view.findViewById(R.id.btAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planMapFragment.addLocation(loc);
                Snackbar.make(viewref, "Added!", Snackbar.LENGTH_SHORT).show();
                planEditActivity.refreshAdd();
            }
        });
        btRemove = (ImageButton) view.findViewById(R.id.ibRemove);
        btRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planEditActivity.removeLocation(loc);
            }
        });


        btClose = (Button) view.findViewById(R.id.btClose);
        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ivPhoto = (ImageView)view.findViewById(R.id.ivPhoto);
        tvName.setText(loc.name);
        tvAddress.setText(loc.address);
        tvPhone.setText(loc.phoneNumber);
        if (loc.openNow == null) {
            tvNowOpen.setVisibility(View.GONE);
        } else if (loc.openNow) {
            tvNowOpen.setText("Open now");
        } else {
            tvNowOpen.setText("Now closed");
        }

        //
        tvHours.setText(loc.hours);
        String priceString = "";
        for (int i = 0; i < loc.price; i++) {
            priceString += "$";
        }

        tvprice.setText(priceString);
        if (loc.rating != -1) {
            ratingBar.setRating(loc.rating);
        } else {
            ratingBar.setVisibility(View.GONE);
        }
        if (loc.photoUrl != null) {
            Picasso.with(getContext())
                    .load(loc.photoUrl)
                    //.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
                    .transform(new gradient())
                    .into(ivPhoto);
        }
        Boolean plan = getArguments().getBoolean("plan");
        if (plan) {
            btAdd.setVisibility(View.GONE);
            btRemove.setVisibility(View.VISIBLE);
        }
    }

    public void debug(String message) {
        Log.d(TAG, message);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btAdd.getId()) {
            addToList();
        }
    }

    void addToList() {
        dismiss();
    }
}
//make a close button
//make on click listener