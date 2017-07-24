package com.example.mbankole.tripplanner.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by mbankole on 7/20/17.
 */

public class Plan implements Parcelable {
    public ArrayList<Location> places;
    public ArrayList<User> people;
    public String title;

    public static Plan generateSeattlePlan(Context context) {
        Plan plan = new Plan();
        plan.places = new ArrayList<>();
        plan.people = new ArrayList<>();
        plan.title = "Trip to Seattle";
        //plan.people.add(User.generateAdam());
        plan.people.add(User.generatePhilp());
        plan.people.add(User.generateTom());
        plan.places.add(Location.generateArboretum(context));
        //plan.places.add(Location.generateArtMuseum());
        plan.places.add(Location.generateNeedle(context));
        //plan.places.add(Location.generatePopCulture());
        plan.places.add(Location.generateWheel(context));
        return plan;
    }

    public Plan() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.places);
        dest.writeTypedList(this.people);
        dest.writeString(this.title);
    }

    protected Plan(Parcel in) {
        this.places = in.createTypedArrayList(Location.CREATOR);
        this.people = in.createTypedArrayList(User.CREATOR);
        this.title = in.readString();
    }

    public static final Creator<Plan> CREATOR = new Creator<Plan>() {
        @Override
        public Plan createFromParcel(Parcel source) {
            return new Plan(source);
        }

        @Override
        public Plan[] newArray(int size) {
            return new Plan[size];
        }
    };
}
