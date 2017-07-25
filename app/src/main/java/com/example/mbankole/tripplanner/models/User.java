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

    public static User generateChandler(Context context) {
        User user = new User();
        user.name = "Chandler Bing";
        user.uid = 1;
        user.imageUrl = "https://vignette4.wikia.nocookie.net/friends/images/c/cc/Square_Chandler.jpg/revision/latest?cb=20111216200026";
        user.friends = new ArrayList<>();
        user.friends.add(generateJoey());
        user.friends.add(generateMonica());
        user.friends.add(generatePhoebe());
        user.friends.add(generateRachel());
        user.friends.add(generateRoss());
        user.interests = new ArrayList<>();
        user.interests.add(Location.generatePopCulture(context));
        user.interests.add(Location.generateNeedle(context));
        user.plans = new ArrayList<>();
        user.plans.add(Plan.generateSeattlePlan(context));
        return user;
    }

    public static User generatePhoebe() {
        User user = new User();
        user.name = "Phoebe Buffay";
        user.imageUrl = "https://vignette2.wikia.nocookie.net/friends/images/f/f5/Square_Phoebe.jpg/revision/latest?cb=20111216200026";
        user.uid = 2;
        user.friends = new ArrayList<>();
        user.interests = new ArrayList<>();
        user.plans = new ArrayList<>();
        //user.friends.add(1);
        return user;
    }

    public static User generateRachel() {
        User user = new User();
        user.name = "Rachel Green";
        user.uid = 3;
        user.imageUrl = "http://cdn.playbuzz.com/cdn/08267c4e-1034-4f96-8a76-47e3be15f1dc/7bf6d62a-46b7-49d1-a179-f065b935e364.png";
        user.friends = new ArrayList<>();
        user.interests = new ArrayList<>();
        user.plans = new ArrayList<>();
        //user.friends.add(2);
        return user;
    }

    public static User generateJoey() {
        User user = new User();
        user.name = "Joey Tribbiani";
        user.uid = 4;
        user.imageUrl = "https://vignette1.wikia.nocookie.net/friends/images/f/f5/JoeyTribbiani.jpg/revision/latest?cb=20070426103739";
        user.friends = new ArrayList<>();
        user.interests = new ArrayList<>();
        user.plans = new ArrayList<>();
        return user;
    }

    public static User generateRoss() {
        User user = new User();
        user.name = "Ross Geller";
        user.uid = 5;
        user.imageUrl = "http://images4.fanpop.com/image/photos/16400000/Ross-Geller-ross-and-monica-geller-16406304-2115-2560.jpg";
        user.friends = new ArrayList<>();
        user.interests = new ArrayList<>();
        user.plans = new ArrayList<>();
        return user;
    }

    public static User generateMonica() {
        User user = new User();
        user.name = "Monica Geller";
        user.uid = 6;
        user.imageUrl = "https://s-media-cache-ak0.pinimg.com/originals/52/36/4a/52364a297b8f265af554b4c810889236.jpg";
        user.friends = new ArrayList<>();
        user.interests = new ArrayList<>();
        user.plans = new ArrayList<>();
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
        dest.writeTypedList(this.plans);
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.uid = in.readInt();
        this.imageUrl = in.readString();
        this.friends = in.createTypedArrayList(User.CREATOR);
        this.interests = in.createTypedArrayList(Location.CREATOR);
        this.plans = in.createTypedArrayList(Plan.CREATOR);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public ArrayList<Location> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<Location> interests) {
        this.interests = interests;
    }

    public ArrayList<Plan> getPlans() {
        return plans;
    }

    public void setPlans(ArrayList<Plan> plans) {
        this.plans = plans;
    }
}