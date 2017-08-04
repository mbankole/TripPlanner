package com.example.mbankole.tripplanner.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mbankole.tripplanner.ApiClients.FirebaseClient;
import com.example.mbankole.tripplanner.ApiClients.GmapClient;
import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.activities.PlanEditActivity;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.models.Plan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import jp.wasabeef.picasso.transformations.Blur;

/**
 * Created by danahe97 on 7/20/17.
 */

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
    List<Plan> mPlans;
    Context context;
    android.app.FragmentManager fm;
    FirebaseClient client;
    FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    //User user;

    public static final int EDIT_PLAN_REQUEST_CODE = 30;
    public PlanAdapter(List<Plan> plans) {
        mPlans = plans;
    }


    public void setFm(android.app.FragmentManager fm) {
        this.fm = fm;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View userView = inflater.inflate(R.layout.item_plan, parent, false);
        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PlanAdapter.ViewHolder holder, int position) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        client = new FirebaseClient(mDatabase);
        // get the data according to position
        Plan plan = mPlans.get(position);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        // populate the views according to this data
        holder.tvPlanTitle.setText(plan.title);
        if (plan.startDate != null) {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String reportDate = df.format(plan.startDate);
            holder.tvDate.setText(reportDate);
            holder.tvDate.setVisibility(View.VISIBLE);
        } else {
            holder.tvDate.setVisibility(View.GONE);
        }
        if (plan.description != null) {
            holder.tvDescription.setText(plan.description);
        } else {
            holder.tvDescription.setVisibility(View.GONE);
        }
        if (plan.people.contains(currentUser.getUid())) {
            holder.ibAdd.setImageResource(R.drawable.ic_star_filled);
        }
        else holder.ibAdd.setImageResource(R.drawable.ic_star_unfilled);

        holder.tvCreator.setText("Created by " + plan.creatorUserName);
        holder.clearLocations();
        for (int i = 0; i < plan.places.size(); i++) {
            holder.addLocation(plan.places.get(i));
        }
        holder.ivBackground.setColorFilter(Color.argb(75, 0, 0, 0));
        if (plan.places.size() > 0) {
            Location loc = plan.places.get(0);
            loc.photoUrl = GmapClient.generateImageUrl(loc.photoRef);
            Picasso.with(context)
                    .load(loc.photoUrl)
                    .fit()
                    .transform(new Blur(context))
                    .into(holder.ivBackground);
        } else {
            holder.ivBackground.setImageResource(R.color.black);
        }
    }

    public void clear() {
        mPlans.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mPlans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvPlanTitle;
        public TextView tvDate;
        public TextView tvCreator;
        public TextView tvDescription;
        public LinearLayout llLocations;
        public ImageButton ibAdd;
        public ImageView ivBackground;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPlanTitle = (TextView) itemView.findViewById(R.id.tvPlanTitle);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvCreator = (TextView) itemView.findViewById(R.id.tvCreator);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            llLocations = (LinearLayout) itemView.findViewById(R.id.llLocations);
            ibAdd = (ImageButton) itemView.findViewById(R.id.ibAdd);
            ivBackground = (ImageView) itemView.findViewById(R.id.ivPlanBackground);
            itemView.setOnClickListener(this);
            ibAdd.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final Integer position = getAdapterPosition();
            final Plan plan = mPlans.get(position);
            if (v.getId() == R.id.ibAdd) {
                if (plan.people.contains(currentUser.getUid())) {
                    plan.people.remove(currentUser.getUid());
                    ibAdd.setImageResource(R.drawable.ic_star_unfilled);
                    Snackbar.make(v, "\"" + plan.title + "\" removed.", Snackbar.LENGTH_SHORT).show();
                    mDatabase.child("plans").child(plan.uid).setValue(plan);
                } else {
                    plan.people.add(currentUser.getUid());
                    Snackbar.make(v, "\"" + plan.title + "\" added!", Snackbar.LENGTH_SHORT).show();
                    ibAdd.setImageResource(R.drawable.ic_star_filled);
                    mDatabase.child("plans").child(plan.uid).setValue(plan);
                }
            } else {
                if (position != RecyclerView.NO_POSITION) {
                    Intent i = new Intent(context, PlanEditActivity.class);
                    i.putExtra("plan", plan);
                    i.putExtra("position", position);
                    ((Activity)context).startActivityForResult(i, EDIT_PLAN_REQUEST_CODE);
//                    ((Activity)context).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
                }
            }
        }

        void addLocation(Location location) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_location, llLocations, false);
            final TextView tvLocationName = (TextView)v.findViewById(R.id.tvLocationname);
            final ImageView ivLocationImage = (ImageView)v.findViewById(R.id.ivLocationImage);
            if (location.photoUrl != null) {
                location.photoUrl = GmapClient.generateImageUrl(location.photoRef);
                Picasso.with(context)
                        .load(location.photoUrl)
                        .fit()
                        .into(ivLocationImage);
            }
            tvLocationName.setText(location.name);
            llLocations.addView(v);
        }

        void clearLocations() {
            if((llLocations).getChildCount() > 0)
                (llLocations).removeAllViews();
        }
    }
}