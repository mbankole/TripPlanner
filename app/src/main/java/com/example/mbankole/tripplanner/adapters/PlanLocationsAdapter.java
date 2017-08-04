
package com.example.mbankole.tripplanner.adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.activities.PlanEditActivity;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.models.TransportOption;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ericar on 7/11/17.
 */

public class PlanLocationsAdapter extends RecyclerView.Adapter<PlanLocationsAdapter.ViewHolder> {

    List<Location> mLocations;
    Context context;
    public PlanEditActivity planEditActivity;
    PlanLocationsAdapter adapter;
    android.app.FragmentManager fm;

    public PlanLocationsAdapter(List<Location> locations) {
        mLocations = locations;
    }

    public void setFm(android.app.FragmentManager fm) {
        this.fm = fm;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        adapter = this;
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View locationView = inflater.inflate(R.layout.item_plan_location, parent, false);
        ViewHolder viewHolder = new ViewHolder(locationView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get the data according to position
        Location location = mLocations.get(position);
        // populate the views according to this data
        holder.tvLocationname.setText(location.name);
        String reportTime;
        if (location.endTime != null) {
            // Create an instance of SimpleDateFormat used for formatting the string representation of date (month/day/year)
            DateFormat df = new SimpleDateFormat("hh:mm a");
            // Using DateFormat format method we can create a string representation of a date with the defined format.
            reportTime = df.format(location.startTime) + " - " + df.format(location.endTime);
            holder.tvTime.setText(reportTime);
            holder.tvTime.setVisibility(View.VISIBLE);
        }
        else {
            holder.tvTime.setVisibility(View.GONE);
        }
        Picasso.with(context)
                .load(location.photoUrl)
                .fit()
                .into(holder.ivLocationImage);
        holder.removeTransport();
        if (location.transport != null) holder.addTransport(location.transport);
        else holder.removeTransport();
    }

    @Override
    public int getItemCount() {return mLocations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvLocationname;
        public TextView tvTime;
        public ImageView ivLocationImage;
        public LinearLayout llTransport;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvLocationname = (TextView) itemView.findViewById(R.id.tvLocationname);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            ivLocationImage = (ImageView) itemView.findViewById(R.id.ivLocationImage);
            llTransport = (LinearLayout) itemView.findViewById(R.id.llTransport);
        }


        void addTransport(TransportOption transportOption) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_transport_select, llTransport, false);
            viewHolderTransportSetup(v);
            switch (transportOption.mode) {
                case WALKING:
                    ((RadioButton)v.findViewById(R.id.rbWalk)).setChecked(true);
                    break;
                case DRIVING:
                    ((RadioButton)v.findViewById(R.id.rbDrive)).setChecked(true);
                    break;
                case TRANSIT:
                    ((RadioButton)v.findViewById(R.id.rbTransit)).setChecked(true);
                    break;
                case BLANK:
                default:
            }
            llTransport.addView(v);
        }

        void removeTransport() {
            if (llTransport.getChildCount() > 0) llTransport.removeAllViews();
        }

        public void viewHolderTransportSetup(View itemView) {
            //itemView.setOnClickListener(this);

            final String TAG = "VIEWHOLDERTRANSPORT";

            final RadioButton rbWalk = (RadioButton) itemView.findViewById(R.id.rbWalk);
            final RadioButton rbDrive = (RadioButton) itemView.findViewById(R.id.rbDrive);
            final RadioButton rbTransit = (RadioButton) itemView.findViewById(R.id.rbTransit);

            View.OnClickListener radioClick = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        TransportOption transportOption = mLocations.get(position).transport;
                        int id = view.getId();
                        if (id == rbWalk.getId()) {
                            boolean checked = ((RadioButton) view).isChecked();
                            Log.d(TAG, "onClick: rbWalk");
                            if (checked) {
                                Log.d(TAG, "onClick: rbWalk selected");
                                transportOption.mode = TransportOption.Mode.WALKING;
                            }
                        } else if (id == rbDrive.getId()) {
                            boolean checked = ((RadioButton) view).isChecked();
                            Log.d(TAG, "onClick: rbDrive");
                            if (checked) {
                                transportOption.mode = TransportOption.Mode.DRIVING;
                            }
                        } else if (id == rbTransit.getId()) {
                            boolean checked = ((RadioButton) view).isChecked();
                            Log.d(TAG, "onClick: rbTransit");
                            if (checked) {
                                transportOption.mode = TransportOption.Mode.TRANSIT;
                            }
                        }
                        update();
                    }
                }
            };
            rbWalk.setOnClickListener(radioClick);
            rbDrive.setOnClickListener(radioClick);
            rbTransit.setOnClickListener(radioClick);
        }

        class TimeChangeAdapter implements TimePickerDialog.OnTimeSetListener {

            public Location location;
            public PlanLocationsAdapter adapter;

            public TimeChangeAdapter(Location location, PlanLocationsAdapter adapter) {
                this.location = location;
                this.adapter = adapter;
            }

            public void show(FragmentManager fm, String title) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd;
                if (location.startTime != null && location.endTime != null) {
                    tpd = TimePickerDialog.newInstance(
                            this,
                            location.startTime.getHours(),
                            location.startTime.getMinutes(),
                            false,
                            location.endTime.getHours(),
                            location.endTime.getMinutes()
                    );
                } else {
                    tpd = TimePickerDialog.newInstance(
                            this,
                            now.get(Calendar.HOUR_OF_DAY),
                            now.get(Calendar.MINUTE),
                            false
                    );
                }
                tpd.show(fm, title);
            }

            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
                if (location.startTime == null) {
                    location.startTime = new Date();
                }
                location.startTime.setHours(hourOfDay);
                location.startTime.setMinutes(minute);
                if (location.endTime == null) {
                    location.endTime = new Date();
                }
                location.endTime.setHours(hourOfDayEnd);
                location.endTime.setMinutes(minuteEnd);
                adapter.update();
            }
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Location location =  mLocations.get(position);
                /*TimePickerFragment frag = new TimePickerFragment();
                frag.location = location;
                frag.adapter = adapter;
                frag.show(fm, "name");*/
                TimeChangeAdapter thing = new TimeChangeAdapter(location, adapter);
                thing.show(fm, "");
            }
        }
    }

    public void update() {
        planEditActivity.refresh();
    }
}