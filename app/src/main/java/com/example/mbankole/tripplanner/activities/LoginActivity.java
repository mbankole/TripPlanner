package com.example.mbankole.tripplanner.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.models.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private TextView skipLoginButton;
    private ImageView ivBackground;
    private SkipLoginCallback skipLoginCallback;
    private CallbackManager callbackManager;
    private boolean recievedUser;
    Context context;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    User tpUser;

    public static final String TAG = "FBLOGINGACTIVITY";

    public interface SkipLoginCallback {
        void onSkipLoginPressed();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        toExploreActivity(currentUser);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show();
                // Initialize Firebase Auth
                mAuth = FirebaseAuth.getInstance();
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(context, "Login canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(context, "Login error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleFacebookAccessToken(final AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //mDatabase = FirebaseDatabase.getInstance().getReference();
                            //generateUser(user, token);
                            updateUI(user, token);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null, null);
                        }

                    }
                });
    }

    public void updateUI(final FirebaseUser user, final AccessToken token) {
        if (user == null) return;
        recievedUser = false;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = mDatabase.child("users");

        Query planQuery = ref.orderByChild("uid").equalTo(user.getUid());

        planQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                    tpUser = singleSnapshot.getValue(User.class);
                }
                recievedUser = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: shits fucked");
                recievedUser = true;
            }
        });

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (recievedUser) {
                    if (tpUser == null) {
                        generateUser(user, token);
                    }
                    toExploreActivity(user);
                }
                else {
                    handler.postDelayed(this, 500);
                }
            }
        });
    }

    public void generateUser(FirebaseUser user, AccessToken token) {
        User newUser = new User();
        newUser.name = user.getDisplayName();
        newUser.uid = user.getUid();
        newUser.imageUrl = "https://graph.facebook.com/" + token.getUserId() + "/picture?type=square";
        newUser.plans = new ArrayList<>();
        newUser.friends = new ArrayList<>();
        newUser.interests = new ArrayList<>();
        mDatabase.child("users").child(newUser.getUid()).setValue(newUser);
    }

    public void toExploreActivity(FirebaseUser user) {
        if (user == null) return;
        Intent i = new Intent(LoginActivity.this, NewExploreActivity.class);
        startActivity(i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void setSkipLoginCallback(SkipLoginCallback callback) {
        skipLoginCallback = callback;
    }

}
