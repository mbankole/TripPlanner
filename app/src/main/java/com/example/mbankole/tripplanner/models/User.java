package com.example.mbankole.tripplanner.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by danahe97 on 7/11/17.
 */

//
public class User implements Parcelable {

    public String name;
    public int uid;
    public String imageUrl;
    public ArrayList<User> friends;
    public ArrayList<Location> interests;
    public ArrayList<Plan> plans;

    public static User generateAdam(Context context) {
        User user = new User();
        user.name = "Adam";
        user.uid = 1;
        user.friends = new ArrayList<>();
        user.friends.add(generateTom());
        user.friends.add(generatePhilp());
        user.interests = new ArrayList<>();
        user.interests.add(Location.generatePopCulture(context));
        user.interests.add(Location.generateNeedle(context));
        user.plans = new ArrayList<>();
        user.plans.add(Plan.generateSeattlePlan(context));
        return user;
    }

    public static User generateTom() {
        User user = new User();
        user.name = "Tom";
        user.uid = 2;
        user.friends = new ArrayList<>();
        //user.friends.add(1);
        return user;
    }

    public static User generatePhilp() {
        User user = new User();
        user.name = "Philp";
        user.uid = 3;
        user.friends = new ArrayList<>();
        //user.friends.add(2);
        return user;
    }

    public User() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.uid);
        dest.writeString(this.imageUrl);
        dest.writeTypedList(this.friends);
        dest.writeTypedList(this.interests);
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.uid = in.readInt();
        this.imageUrl = in.readString();
        this.friends = in.createTypedArrayList(User.CREATOR);
        this.interests = in.createTypedArrayList(Location.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
//list friend names
//enter and show profile with list of interests