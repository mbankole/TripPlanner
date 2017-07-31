package com.example.mbankole.tripplanner.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbankole.tripplanner.ApiClients.GmapClient;
import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.activities.PlanEditActivity;
import com.example.mbankole.tripplanner.adapters.PlanLocationsAdapter;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.models.Plan;
import com.example.mbankole.tripplanner.models.TransportOption;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;

import jp.wasabeef.picasso.transformations.Blur;

/**
 * Created by mbankole on 7/13/17.
 */

public class PlanListFragment extends Fragment{

    public Plan plan;
    TextView tvTitle;
    TextView tvDate;
    TextView tvCreator;
    ImageView ivBackground;
    RecyclerView rvPlanList;
    public PlanLocationsAdapter listAdapter;
    FragmentManager fm;
    public PlanEditActivity planEditActivity;
    PlanListFragment self;

    public static PlanListFragment newInstance() {
        Bundle args = new Bundle();
        //args.putParcelable("user", user);
        PlanListFragment fragment = new PlanListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the layout
        View v = inflater.inflate(R.layout.fragment_listsview, container, false);
        self = this;
        fm = getActivity().getSupportFragmentManager();
        // find RecyclerView
        tvTitle = (TextView) v.findViewById(R.id.tvPlanName);
        tvDate = (TextView) v.findViewById(R.id.tvPlanDate);
        tvCreator = (TextView) v.findViewById(R.id.tvPlanCreator);
        ivBackground = (ImageView) v.findViewById(R.id.ivPlanBackground);
        rvPlanList = (RecyclerView) v.findViewById(R.id.rvPlanList);

        tvTitle.setText(plan.title);
        if (plan.startDate != null) {
            refreshDate();
        }
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment frag = new DatePickerFragment();
                frag.plan = plan;
                frag.planListFragment = self;
                frag.show(fm, "name");
            }
        });
        tvCreator.setText("Created by " + plan.creatorUserName);
        ivBackground.setColorFilter(Color.argb(65, 0, 0, 0));
        if (plan.places.size() > 0) {
            Location loc = plan.places.get(0);
            loc.photoUrl = GmapClient.generateImageUrl(loc.photoRef);
            Picasso.with(getContext())
                    .load(loc.photoUrl)
                    .fit()
                    .transform(new Blur(getContext()))
                    .into(ivBackground);
        }

        // construct the adapter from this data source
        listAdapter = new PlanLocationsAdapter(plan.places);
        listAdapter.planEditActivity = planEditActivity;
        listAdapter.setFm(fm);
//        locationAdapter.exploreActivity = exploreActivity;
        // RecyclerView setup (layout manager, use adapter)
        rvPlanList.setLayoutManager(new LinearLayoutManager(getContext()));
        // set the adapter
        rvPlanList.setAdapter(listAdapter);

        // Extend the Callback class
        ItemTouchHelper.Callback _ithCallback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP );
            }

            //and in your implementaion of
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // get the viewHolder's and target's positions in your adapter data, swap them
                Collections.swap(plan.places, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                // and notify the adapter that its dataset has changed
                listAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                planEditActivity.refresh();
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };
        ItemTouchHelper ith = new ItemTouchHelper(_ithCallback);
        ith.attachToRecyclerView(rvPlanList);

        listAdapter.notifyItemInserted(plan.places.size() - 1);

        refresh();

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {}

    public void refresh() {
        if (plan.places != null && plan.places.size() > 1) {
            for (int i = 0; i < plan.places.size() - 1; i++) {
                Location loc1 = plan.places.get(i);
                Location loc2 = plan.places.get(i + 1);
                if (loc1.transport == null || loc1.transport.endId != loc2.googleId) {
                    loc1.transport = new TransportOption(loc1, loc2);
                    loc1.transport.mode = TransportOption.Mode.BLANK;
                }
            }
            plan.places.get(plan.places.size() - 1).transport = null;
        }
        listAdapter.notifyDataSetChanged();
    }

    public void refreshDate() {
        // Create an instance of SimpleDateFormat used for formatting the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        // Using DateFormat format method we can create a string representation of a date with the defined format.
        String reportDate = df.format(plan.startDate);
        tvDate.setText(reportDate);
    }

    public void addItem() {
        listAdapter.notifyItemInserted(plan.places.size() - 1);
        refresh();
    }
}