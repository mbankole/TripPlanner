package com.example.mbankole.tripplanner.ApiClients;

import com.example.mbankole.tripplanner.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by danahe97 on 7/26/17.
 */

public class FirebaseClient {

    DatabaseReference mDatabase;

    User user;

    String TAG = "FIREBASE_CLIENT";

    public FirebaseClient(DatabaseReference ref) {
        mDatabase = ref;
    }

    public User getUserByUid(String uid) {
        user = null;
        DatabaseReference ref = mDatabase.child("users");
        Query planQuery = ref.orderByChild("uid").equalTo(uid);

        planQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    user = singleSnapshot.getValue(User.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.e(TAG, "onCancelled: shits fucked");
            }
        });
        return user;
    }

    public static void sendNotificationUser(String uid, final String title, final String message) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference notifications = mDatabase.child("notificationRequests");
        Map notification = new HashMap<>();
        notification.put("uid", "user_" + uid);
        notification.put("title", title);
        notification.put("message", message);
        notifications.push().setValue(notification);
    }
    public static void sendNotificationTopic(String uid, final String title, final String message) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference notifications = mDatabase.child("notificationRequests");
        Map notification = new HashMap<>();
        notification.put("uid", uid);
        notification.put("title", title);
        notification.put("message", message);
        notifications.push().setValue(notification);
    }
}