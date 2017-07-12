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

    public static User generateTom() {
        User user = new User();
        user.name = "Tom";
        user.uid = 2;
        user.friends = new ArrayList<Integer>();
        user.friends.add(1);
        return user;
    }

    public static User generatePhilp() {
        User user = new User();
        user.name = "Philp";
        user.uid = 3;
        user.friends = new ArrayList<Integer>();
        user.friends.add(2);
        return user;
    }
}
