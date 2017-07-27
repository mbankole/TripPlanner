package com.example.mbankole.tripplanner.ApiClients;

import com.example.mbankole.tripplanner.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

    public User getUserByUid (String uid) {
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
}