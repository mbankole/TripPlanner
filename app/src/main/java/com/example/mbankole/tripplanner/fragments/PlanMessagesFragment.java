package com.example.mbankole.tripplanner.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.activities.PlanEditActivity;
import com.example.mbankole.tripplanner.adapters.ChatUserAdapter;
import com.example.mbankole.tripplanner.adapters.PlanMessagesAdapter;
import com.example.mbankole.tripplanner.models.Message;
import com.example.mbankole.tripplanner.models.Plan;
import com.example.mbankole.tripplanner.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.Blur;

/**
 * Created by mbankole on 7/31/17.
 */

public class PlanMessagesFragment extends Fragment{

    String planUid;
    ArrayList<User> people;
    public Plan plan;
    public PlanEditActivity planEditActivity;
    TextView tvTitle;
    TextView tvNumberUsers;
    RecyclerView rvUsers;
    ImageView ivBackground;
    RecyclerView rvMessages;
    EditText etBody;
    Button btSend;
    public ArrayList<Object> objects;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    PlanMessagesAdapter messagesAdapter;
    private DatabaseReference mDatabase;
    DatabaseReference messagesRef;
    User user;
    User currentTPUser;
    ChatUserAdapter userAdapter;

    private static final String TAG = "PLANMESSAGESFRAGMENT";

    public static PlanMessagesFragment newInstance(String planUid) {
        Bundle args = new Bundle();
        args.putString("planUid", planUid);
        PlanMessagesFragment fragment = new PlanMessagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_plan_messages, container, false);

        objects = new ArrayList<>();

        planUid = getArguments().getString("planUid");

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        tvTitle = (TextView) v.findViewById(R.id.tvPlanName);
        tvNumberUsers = (TextView) v.findViewById(R.id.tvNumberUsers);
        rvUsers = (RecyclerView) v.findViewById(R.id.rvChatUsers);
        ivBackground = (ImageView) v.findViewById(R.id.ivPlanBackground);

        tvTitle.setText(plan.title + " Chatroom");
        if (plan.people.size() == 1) {
            tvNumberUsers.setText("1 Person:");
        } else {
            tvNumberUsers.setText((plan.people.size() + " People:"));
        }
        if (plan.places.size() > 0) {
            ivBackground.setColorFilter(Color.argb(65, 0, 0, 0));
            Picasso.with(getContext())
                    .load(plan.places.get(0).photoUrl)
                    .fit()
                    .transform(new Blur(getContext()))
                    .into(ivBackground);
        }
        // construct the adapter from this data source
        people = new ArrayList<>();
        for (int i = 0; i < plan.people.size(); i++) {
            final Query userQuery = mDatabase.child("users").orderByChild("uid").equalTo(plan.people.get(i));
            userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                        user = singleSnapshot.getValue(User.class);
                        fixUser(user);
                        people.add(user);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "shits fucked");
                }
            });
        }
        userAdapter = new ChatUserAdapter(people);
        // RecyclerView setup (layout manager, use adapter)
        rvUsers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        // set the adapter
        rvUsers.setAdapter(userAdapter);

        etBody = (EditText) v.findViewById(R.id.etMessage);
        btSend = (Button) v.findViewById(R.id.btSend);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etBody.getText().toString().equals("")) {
                    String text = etBody.getText().toString();
                    Message newMessage = new Message();
                    newMessage.setBody(text);
                    newMessage.setSenderUid(currentUser.getUid());
                    newMessage.setSenderUsername(currentUser.getDisplayName());
                    newMessage.setSenderProfileUrl(currentTPUser.getImageUrl());
                    DatabaseReference newMessagedb = mDatabase.child("plan_data").child(planUid).child("messages").push();
                    newMessagedb.setValue(newMessage);
                    etBody.setText("");
                }
            }
        });
        rvMessages = (RecyclerView) v.findViewById(R.id.rvMessages);
        messagesAdapter = new PlanMessagesAdapter(objects);
        messagesAdapter.planEditActivity = planEditActivity;
        rvMessages.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        rvMessages.setAdapter(messagesAdapter);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = mDatabase.child("users");

        Query userQuery = ref.orderByChild("uid").equalTo(currentUser.getUid());

        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                    currentTPUser = singleSnapshot.getValue(User.class);
                    fixUser(currentTPUser);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: shits fucked");
            }
        });

        return v;
    }

    void fixUser(User user) {
        if (user.interests == null) user.interests = new ArrayList<>();
        if (user.friends == null) user.friends = new ArrayList<>();
        if (user.plans == null) user.plans = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMessages();
    }

    ChildEventListener messagesListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
            Message message = dataSnapshot.getValue(Message.class);
            if (message.getSenderUid().equals(currentUser.getUid())) {
                message.setReceived(false);
            }
            else message.setReceived(true);
            objects.add(0, message);
            messagesAdapter.notifyItemInserted(0);
            rvMessages.smoothScrollToPosition(0);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e(TAG, "onCancelled: shits fucked");
        }
    };

    public void loadMessages() {
        objects.clear();

        messagesAdapter.notifyDataSetChanged();

        messagesRef = mDatabase.child("plan_data").child(planUid).child("messages");

        messagesRef.addChildEventListener(messagesListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        messagesRef.removeEventListener(messagesListener);
    }
}
