package com.example.mbankole.tripplanner.models;

import java.util.ArrayList;

/**
 * Created by danahe97 on 7/11/17.
 */

public class User {

    public String name;
    public int uid;
    public ArrayList<Integer> friends;

    public static User generateAdam() {
        User user = new User();
        user.name = "Adam";
        user.uid = 1;
        user.friends = new ArrayList<Integer>();
        user.friends.add(2);
        return user;
    }
}
