package com.example.mbankole.tripplanner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PointOfInterest;

/**
 * Created by ericar on 7/11/17.
 */

public class Location implements Parcelable {

    public int uid;
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

    public static Location generateEiffelTower() {
        Location location = new Location();
        location.name = "EiffelTower";
        location.uid = 1;
        return location;
    }

    public static Location generateStatueOfLiberty() {
        Location location = new Location();
        location.name = "StauteOfLiberty";
        location.uid = 2;
        return location;
    }

    public static Location generateTajMahal() {
        Location location = new Location();
        location.name = "TajMahal";
        location.uid = 3;
        return location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.uid);
        dest.writeString(this.name);
        dest.writeParcelable(this.latLng, flags);
        dest.writeString(this.desc);
        dest.writeString(this.googleId);
    }

    protected Location(Parcel in) {
        this.uid = in.readInt();
        this.name = in.readString();
        this.latLng = in.readParcelable(LatLng.class.getClassLoader());
        this.desc = in.readString();
        this.googleId = in.readString();
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
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
