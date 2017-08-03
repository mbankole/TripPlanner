package com.example.mbankole.tripplanner.adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbankole.tripplanner.ApiClients.GmapClient;
import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.activities.PlanEditActivity;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mbankole on 7/31/17.
 */

public class PlanMessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Object> mObjects;
    Context context;
    android.app.FragmentManager fm;
    public PlanEditActivity planEditActivity;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private final int RECEIVED = 0, SENT = 1, REQUEST = 2;

    public PlanMessagesAdapter(List<Object> objects) {
        mObjects = objects;
    }

    public void setFm(android.app.FragmentManager fm) {
        this.fm = fm;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        context = parent.getContext();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        switch (viewType) {
            case RECEIVED:
                View v1 = inflater.inflate(R.layout.item_message_received, parent, false);
                viewHolder = new ViewHolderReceived(v1);
                break;
            case SENT:
                View v2 = inflater.inflate(R.layout.item_message_sent, parent, false);
                viewHolder = new ViewHolderSent(v2);
                break;
            case REQUEST:
                View v3 = inflater.inflate(R.layout.item_request, parent, false);
                viewHolder = new ViewHolderRequest(v3);
                break;
            default:
                View v = inflater.inflate(R.layout.item_message_received, parent, false);
                viewHolder = new ViewHolderReceived(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case RECEIVED:
                ViewHolderReceived vh1 = (ViewHolderReceived) holder;
                configureViewHolderReceived(vh1, position);
                break;
            case SENT:
                ViewHolderSent vh2 = (ViewHolderSent) holder;
                configureViewHolderSent(vh2, position);
                break;
            case REQUEST:
                ViewHolderRequest vh3 = (ViewHolderRequest) holder;
                configureViewHolderRequest(vh3, position);
                break;
            default:
                ViewHolderReceived vh = (ViewHolderReceived) holder;
                configureViewHolderReceived(vh, position);
                break;
        }
    }

    public void configureViewHolderReceived(ViewHolderReceived holder, int position) {
        Message message = (Message) mObjects.get(position);
        holder.tvBody.setText(message.getBody());
        Picasso.with(context).load(message.getSenderProfileUrl()).into(holder.ivProfileImage);
    }

    public void configureViewHolderSent(ViewHolderSent holder, int position) {
        Message message = (Message) mObjects.get(position);
        holder.tvBody.setText(message.getBody());
        Picasso.with(context).load(message.getSenderProfileUrl()).into(holder.ivProfileImage);
    }

    public void configureViewHolderRequest(ViewHolderRequest holder, final int position) {
        final Message message = (Message) mObjects.get(position);
        String requestBody = message.getRequestType() + " " + message.getLocationName();
        String userTitle = message.getSenderUsername() + "wants to ";
        Picasso.with(context).load(message.getLcoationImageUrl()).into(holder.ivPreview);
        holder.tvRequestBody.setText(requestBody);
        holder.tvRequester.setText(userTitle);
        holder.btApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser.getUid().equals(planEditActivity.plan.getCreatorUid())) {
                    GmapClient.getDetailFromId(message.getRequestTargetGid(), new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Location loc = null;
                            try {
                                loc = Location.locationFromJson(response);
                                if (message.getRequestType().equals("add")) {
                                    planEditActivity.plan.places.add(loc);
                                    planEditActivity.refreshAdd();
                                    mObjects.remove(position);
                                    notifyItemRemoved(position);
                                    mDatabase.child("plan_data").child(planEditActivity.plan
                                            .getUid()).child("messages").child(message.getDbUid())
                                            .removeValue();
                                    Snackbar.make(planEditActivity.viewPager, "ADDED!", Snackbar.LENGTH_SHORT).show();
                                }
                                else if (message.getRequestType().equals("remove")) {
                                    planEditActivity.plan.places.remove(loc);
                                    planEditActivity.refreshAdd();
                                    mObjects.remove(position);
                                    notifyItemRemoved(position);
                                    mDatabase.child("plan_data").child(planEditActivity.plan
                                            .getUid()).child("messages").child(message.getDbUid())
                                            .removeValue();
                                    Snackbar.make(planEditActivity.viewPager, "REMOVED!", Snackbar.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                else {
                    Snackbar.make(planEditActivity.viewPager, "You do not have permission", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        holder.btDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser.getUid().equals(planEditActivity.plan.getCreatorUid())) {
                    mObjects.remove(position);
                    notifyItemRemoved(position);
                    Snackbar.make(planEditActivity.viewPager , "DENIED!", Snackbar.LENGTH_SHORT).show();
                }
                else {
                    Snackbar.make(planEditActivity.viewPager, "You do not have permission", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        Object object = mObjects.get(position);
        if (object instanceof Message) {
            if (((Message)object).isRequest()) return REQUEST;
            if (((Message)object).isReceived()) return RECEIVED;
            else return SENT;
        }
        return -1;
    }

    @Override
    public int getItemCount() {return mObjects.size();
    }

    public class ViewHolderReceived extends RecyclerView.ViewHolder {

        public TextView tvBody;
        public ImageView ivProfileImage;

        public ViewHolderReceived(View itemView) {
            super(itemView);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
        }
    }

    public class ViewHolderSent extends RecyclerView.ViewHolder {

        public TextView tvBody;
        public ImageView ivProfileImage;

        public ViewHolderSent(View itemView) {
            super(itemView);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
        }
    }

    public class ViewHolderRequest extends RecyclerView.ViewHolder {
        public TextView tvRequester;
        public TextView tvRequestBody;
        public Button btApprove;
        public Button btDeny;
        public ImageView ivPreview;

        public ViewHolderRequest(View itemView) {
            super(itemView);
            tvRequester = (TextView) itemView.findViewById(R.id.tvRequester);
            tvRequestBody = (TextView) itemView.findViewById(R.id.tvRequestBody);
            btApprove = (Button) itemView.findViewById(R.id.btApprove);
            btDeny = (Button) itemView.findViewById(R.id.btDeny);
            ivPreview = (ImageView) itemView.findViewById(R.id.ivPreview);
        }
    }
}