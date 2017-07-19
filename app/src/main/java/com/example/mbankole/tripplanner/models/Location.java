package com.example.mbankole.tripplanner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mbankole.tripplanner.ApiClients.GmapClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PointOfInterest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ericar on 7/11/17.
 */

public class Location implements Parcelable {

    static final String TAG = "LOCATIONMODEL";

    public int uid;
    public String name;
    public String address;
    public String phoneNumber;
    public Boolean openNow;
    public String hours;
    public int price;
    public String iconUrl;
    public LatLng latLng;
    public String desc;
    public String googleId;
    public String photoRef;
    public String photoUrl;
    public float rating;
    public ArrayList<User> people;
    public TransportOption transport;

    public Location() {}


    public Location(PointOfInterest poi) {
        name = poi.name;
        latLng = poi.latLng;
        googleId = poi.placeId;
    }

    public static Location locationFromJson(JSONObject obj) throws JSONException{
        Location loc = new Location();
        locationFromJson(obj,loc);
        return loc;
    }

    public static void locationFromJson(JSONObject obj, Location loc) throws JSONException{
        JSONObject result = obj;
        if (obj.has("result")) result = obj.getJSONObject("result");
        loc.name = result.getString("name");
        loc.address = result.optString("formatted_address");
        loc.phoneNumber = result.optString("formatted_phone_number");
        if (result.has("opening_hours")) {
            JSONObject openingHours = result.optJSONObject("opening_hours");
            if (openingHours.has("open_now")) {
                loc.openNow = openingHours.getBoolean("open_now");
            }
            JSONArray hours = openingHours.optJSONArray("weekday_text");
            loc.hours = "";
            for (int i = 0; i < hours.length(); i++) {
                loc.hours += hours.getString(i) + "\n";
            }
        }
        loc.price = result.optInt("price_level");
        loc.rating = (float)result.optDouble("rating", -1);
        loc.iconUrl = result.optString("icon", null);
        JSONArray photoData = result.optJSONArray("photos");
        if (photoData != null) loc.photoRef = photoData.getJSONObject(0).optString("photo_reference", null);
        if (loc.photoRef != null) loc.photoUrl = GmapClient.generateImageUrl(loc.photoRef);
        JSONObject latlngObj = result.getJSONObject("geometry").getJSONObject("location");
        loc.latLng = new LatLng(latlngObj.getDouble("lat"), latlngObj.getDouble("lng"));
        loc.people = new ArrayList<>();
        loc.googleId = result.getString("place_id");
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

    //some placeIds
    // museum of pop culture - ChIJAAAAAAAAAAAREthJEc0p6dE
    // space needle - ChIJAAAAAAAAAAARDZLQnmioK9s
    // seattle art museum - ChIJAAAAAAAAAAARxI1KoO7oZHs
    // wheel - ChIJAAAAAAAAAAARxtrx3nOQIKU
    // arboretum - ChIJAAAAAAAAAAARin5kehZcTqI

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.uid);
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.phoneNumber);
        dest.writeValue(this.openNow);
        dest.writeString(this.hours);
        dest.writeInt(this.price);
        dest.writeString(this.iconUrl);
        dest.writeParcelable(this.latLng, flags);
        dest.writeString(this.desc);
        dest.writeString(this.googleId);
        dest.writeString(this.photoRef);
        dest.writeString(this.photoUrl);
        dest.writeFloat(this.rating);
        dest.writeTypedList(this.people);
        dest.writeParcelable(this.transport, flags);
    }

    protected Location(Parcel in) {
        this.uid = in.readInt();
        this.name = in.readString();
        this.address = in.readString();
        this.phoneNumber = in.readString();
        this.openNow = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.hours = in.readString();
        this.price = in.readInt();
        this.iconUrl = in.readString();
        this.latLng = in.readParcelable(LatLng.class.getClassLoader());
        this.desc = in.readString();
        this.googleId = in.readString();
        this.photoRef = in.readString();
        this.photoUrl = in.readString();
        this.rating = in.readFloat();
        this.people = in.createTypedArrayList(User.CREATOR);
        this.transport = in.readParcelable(TransportOption.class.getClassLoader());
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