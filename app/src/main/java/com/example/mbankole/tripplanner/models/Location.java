package com.example.mbankole.tripplanner.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.mbankole.tripplanner.ApiClients.GmapClient;
import com.example.mbankole.tripplanner.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PointOfInterest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

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
    public DCLatLng latLng;
    public String desc;
    public String googleId;
    public String photoRef;
    public String photoUrl;
    public float rating;
    public ArrayList<User> people;
    public TransportOption transport;
    public Date startTime;
    public long duration; // in seconds prob since idk
    public Date endTime;

    public Location() {}


    public Location(PointOfInterest poi) {
        name = poi.name;
        latLng = new DCLatLng(poi.latLng);
        googleId = poi.placeId;
    }

    public static Location locationFromJson(JSONObject obj) throws JSONException{
        Location loc = new Location();
        locationFromJson(obj,loc);
        return loc;
    }

    public static void locationFromJson(JSONObject obj, Location loc) throws JSONException{
        JSONObject result = obj;
        //Log.d(TAG, "locationFromJson: " + result.toString());
        if (obj.has("result")) result = obj.getJSONObject("result");
        loc.name = result.optString("name", "Name not found");
        loc.address = result.optString("formatted_address", "Address not found");
        loc.phoneNumber = result.optString("formatted_phone_number", "phone number not found");
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
        JSONObject latlngObj = result.optJSONObject("geometry");
        if (latlngObj != null) latlngObj = latlngObj.optJSONObject("location");
        if (latlngObj != null) loc.latLng = new DCLatLng(latlngObj.getDouble("lat"), latlngObj.getDouble("lng"));
        loc.people = new ArrayList<>();
        loc.googleId = result.optString("place_id");
    }

    public static Location generatePopCulture(Context context) {
        String jsonString = context.getString(R.string.popCultureJson);
        try {
            return Location.locationFromJson(new JSONObject(jsonString));
        } catch (JSONException e) {
            Log.d(TAG, "generateArboretum: " + e.toString());
        }
        return null;
    }

    public static Location generateNeedle(Context context) {
        String jsonString = context.getString(R.string.spaceNeedleJson);
        try {
            return Location.locationFromJson(new JSONObject(jsonString));
        } catch (JSONException e) {
            Log.d(TAG, "generateArboretum: " + e.toString());
        }
        return null;
    }

    public static Location generateArtMuseum(Context context) {
        String jsonString = context.getString(R.string.artMuseumJson);
        try {
            return Location.locationFromJson(new JSONObject(jsonString));
        } catch (JSONException e) {
            Log.d(TAG, "generateArboretum: " + e.toString());
        }
        return null;
    }

    public static Location generateWheel(Context context) {
        String jsonString = context.getString(R.string.wheelJson);
        try {
            return Location.locationFromJson(new JSONObject(jsonString));
        } catch (JSONException e) {
            Log.d(TAG, "generateArboretum: " + e.toString());
        }
        return null;
    }

    public static Location generateArboretum(Context context) {
        String jsonString = context.getString(R.string.arboretumJson);
        try {
            return Location.locationFromJson(new JSONObject(jsonString));
        } catch (JSONException e) {
            Log.d(TAG, "generateArboretum: " + e.toString());
        }
        return null;
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

    public void setLatLng(DCLatLng latLng) {
        this.latLng = latLng;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getPhotoRef() {
        return photoRef;
    }

    public void setPhotoRef(String photoRef) {
        this.photoRef = photoRef;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public ArrayList<User> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<User> people) {
        this.people = people;
    }

    public TransportOption getTransport() {
        return transport;
    }

    public void setTransport(TransportOption transport) {
        this.transport = transport;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}