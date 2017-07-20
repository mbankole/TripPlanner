package com.example.mbankole.tripplanner.models;

import java.util.ArrayList;

/**
 * Created by mbankole on 7/20/17.
 */

public class Plan {
    public ArrayList<Location> places;
    public ArrayList<User> people;
    public String title;

    public static Plan generateSeattlePlan() {
        Plan plan = new Plan();
        plan.places = new ArrayList<>();
        plan.people = new ArrayList<>();
        plan.title = "Trip to Seattle";
        plan.people.add(User.generateAdam());
        plan.people.add(User.generatePhilp());
        plan.people.add(User.generateTom());
        plan.places.add(Location.generateArboretum());
        plan.places.add(Location.generateArtMuseum());
        plan.places.add(Location.generateNeedle());
        plan.places.add(Location.generatePopCulture());
        plan.places.add(Location.generateWheel());
        return plan;
    }
}
