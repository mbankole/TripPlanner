package com.example.mbankole.tripplanner.models;

import android.os.Parcel;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PointOfInterest;

import java.util.ArrayList;

/**
 * Created by ericar on 7/11/17.
 */

public class Location {

    public String name;
    public int uid;
    public ArrayList<Integer> places;

    public String name;
    public LatLng latLng;
    public String desc;
    public String googleId;

    public Location() {}

    public Location(PointOfInterest poi) {
        name = poi.name;
        latLng = poi.latLng;
        googleId = poi.placeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeParcelable(this.latLng, flags);
        dest.writeString(this.desc);
        dest.writeString(this.googleId);
    }

    public static Location generateEiffelTowe() {
        Location location = new Location();
        location.name = "EiffelTower";
        location.uid = 1;
        location.places = new ArrayList<Integer>();
        location.places.add(2);
        return location;
    }

    public static Location generateStauteOfLiberty() {
        Location location = new Location();
        location.name = "StauteOfLiberty";
        location.uid = 2;
        location.places = new ArrayList<Integer>();
        location.places.add(1);
        return location;
    }

    public static Location generateTajMahal() {
        Location location = new Location();
        location.name = "TajMahal";
        location.uid = 3;
        location.places = new ArrayList<Integer>();
        location.places.add(2);
        return location;
    }

    protected Location(Parcel in) {
        this.name = in.readString();
        this.latLng = in.readParcelable(LatLng.class.getClassLoader());
        this.desc = in.readString();
        this.googleId = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
