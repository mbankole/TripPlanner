package com.example.mbankole.tripplanner.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.models.RouteStep;

import java.util.ArrayList;

/**
 * Created by danahe97 on 7/18/17.
 */

public class RouteStepAdapter extends RecyclerView.Adapter<RouteStepAdapter.ViewHolder> {

    Context context;
    public ArrayList<RouteStep> mSteps;

    public RouteStepAdapter(ArrayList<RouteStep> steps) {
        mSteps = steps;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View stepView = inflater.inflate(R.layout.item_route_step, parent, false);
        ViewHolder viewHolder = new RouteStepAdapter.ViewHolder(stepView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get the data according to position
        RouteStep step = mSteps.get(position);
        // populate the views according to this data
        holder.tvDirection.setText(step.htmlInstructions);
        holder.tvDistance.setText(step.distance);
        if (step.maneuver != null) {
            if (step.maneuver.equals("turn-right") || step.maneuver.equals("turn-slight-right") || step.maneuver.equals("ramp-right")) {
                holder.ivManeuver.setImageResource(R.drawable.ic_right);
            } else if (step.maneuver.equals("turn-left") || step.maneuver.equals("turn-slight-left") || step.maneuver.equals("ramp-left")) {
                holder.ivManeuver.setImageResource(R.drawable.ic_left);
            } else if (step.maneuver.equals("merge")) {
                holder.ivManeuver.setImageResource(R.drawable.ic_merge);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvDirection;
        public TextView tvDistance;
        public ImageView ivManeuver;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDirection = (TextView) itemView.findViewById(R.id.tvDirections);
            tvDistance = (TextView) itemView.findViewById(R.id.tvDistance);
            ivManeuver = (ImageView) itemView.findViewById(R.id.ivManuever);
        }
    }
}
