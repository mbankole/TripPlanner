
package com.example.mbankole.tripplanner.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mbankole.tripplanner.ExploreActivity;
import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.models.TransportOption;
import com.example.mbankole.tripplanner.models.User;
import com.example.mbankole.tripplanner.utility.gradient;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ericar on 7/11/17.
 */

public class PlanLocationsAdapter extends RecyclerView.Adapter<PlanLocationsAdapter.ViewHolder> {

    List<Location> mLocations;
    Context context;
    public ExploreActivity exploreActivity;
    android.app.FragmentManager fm;

    public PlanLocationsAdapter(List<Location> locations) {
        mLocations = locations;
    }

    public void setFm(android.app.FragmentManager fm) {
        this.fm = fm;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        Picasso.with(context)
                .load(location.photoUrl)
                //.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
                .transform(new gradient())
                .into(holder.ivLocationImage);
        if (location.transport != null) holder.addTransport(location.transport);
        else holder.removeTransport();

    }

    @Override
    public int getItemCount() {return mLocations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvLocationname;
        public ImageView ivLocationImage;
        public LinearLayout llPeople;
        public LinearLayout llTransport;
        public RelativeLayout rlInfo;
        public Button expand;
        public Button add;
        public Button close;

        public boolean expanded = false;

        public ViewHolder(View itemView) {
            super(itemView);
            tvLocationname = (TextView) itemView.findViewById(R.id.tvLocationname);
            ivLocationImage = (ImageView) itemView.findViewById(R.id.ivLocationImage);
            llPeople = (LinearLayout) itemView.findViewById(R.id.llPeople);
            llTransport = (LinearLayout) itemView.findViewById(R.id.llTransport);
            rlInfo = (RelativeLayout) itemView.findViewById(R.id.rlInfo);
            expand = (Button) itemView.findViewById(R.id.btExpand);
            add = (Button) itemView.findViewById(R.id.btAdd);
            close = (Button) itemView.findViewById(R.id.btClose);

            rlInfo.setVisibility(View.GONE);

            expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expanded) {
                        rlInfo.setVisibility(View.GONE);
                        expanded = false;
                    }
                    else {
                        rlInfo.setVisibility(View.VISIBLE);
                        expanded = true;
                    }
                }
            });

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rlInfo.setVisibility(View.GONE);
                    expanded = false;
                }
            });

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addUser(User.generateAdam());
                }
            });
        }

        void addUser(User user) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_user_slim, llPeople, false);
            ((TextView)v.findViewById(R.id.tvUsername)).setText(user.name);
            llPeople.addView(v);
        }

        void addTransport(TransportOption transportOption) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_transport_select, llTransport, false);
            viewHolderTransportSetup(v);
            llTransport.addView(v);
        }

        void removeTransport() {

        }

        public void viewHolderTransportSetup(View itemView) {
            //itemView.setOnClickListener(this);

            final String TAG = "VIEWHOLDERTANSPORT";

            TextView tvName;
            ImageView ivImage;

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);

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
                    }
                }
            };

            rbWalk.setOnClickListener(radioClick);
            rbDrive.setOnClickListener(radioClick);
            rbTransit.setOnClickListener(radioClick);

        }

        @Override
        public void onClick(View view) {

        }
    }
}