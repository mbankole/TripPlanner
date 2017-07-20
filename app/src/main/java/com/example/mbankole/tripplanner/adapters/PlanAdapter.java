package com.example.mbankole.tripplanner.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mbankole.tripplanner.ExploreActivity;
import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.models.Plan;

import java.util.List;

/**
 * Created by danahe97 on 7/20/17.
 */

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
    List<Plan> mPlans;
    Context context;
    android.app.FragmentManager fm;
    public ExploreActivity exploreActivity;

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
    public void onBindViewHolder(PlanAdapter.ViewHolder holder, int position) {
        // get the data according to position
        Plan plan = mPlans.get(position);
        // populate the views according to this data
        holder.tvPlanTitle.setText(plan.title);
    }

    @Override
    public int getItemCount() {
        return mPlans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvPlanTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPlanTitle = (TextView) itemView.findViewById(R.id.tvPlanTitle);
        }
    }
}
