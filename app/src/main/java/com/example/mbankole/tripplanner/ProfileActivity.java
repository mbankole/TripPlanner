package com.example.mbankole.tripplanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.mbankole.tripplanner.models.User;


public class ProfileActivity extends AppCompatActivity {

    TextView tvUsername;
    TextView tvInterests;
    User self;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvUsername = (TextView) findViewById(R.id.tvUsernameProfiles);
        tvInterests = (TextView) findViewById(R.id.tvInterests);
        self = User.generateAdam();
        tvUsername.setText(self.name);
        String interestString = "";
        for (int i=0; i<self.interests.size(); i++) {
            interestString += self.interests.get(i) + ", ";
        }
        tvUsername.setText(",");
        tvInterests.setText(",");
    }

    private static int count = 0;

    // number of objects in memory

    // initialize user, add 1 to static count and
    // output String indicating that constructor was called

    public ProfileActivity( String first, String last )
    {
        firstName = first;
        lastName = last;

        count++;
        // increment static count of Users
        System.out.printf( "User constructor: %s %s; count = %d\n",
                firstName, lastName, count );
    }

    // end User constructor


    private double startingProfile;
    private String firstName;
    private String lastName;
    private String username;
    private String password;}


    //another constructer
    //public ProfileActivity( Double, String first, String last, String name, String pass)




    //User username = new User(Double startingProfile,
    //String fName, String lName, String userName, String passWord);

    //username.setBalance(Double balanceAmt); or username.getBalance();
    //Map<String, User> users = <String, User>();
    //users.put("betty", new User(...));
    //users.get("betty").setBalance(20.00);

//another constructer
//public ProfileActivity( Double sMoney, String first, String last, String name, String pass)





