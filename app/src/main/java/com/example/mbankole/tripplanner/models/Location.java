package com.example.mbankole.tripplanner.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mbankole.tripplanner.ApiClients.GmapPlaceDetailClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PointOfInterest;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ericar on 7/11/17.
 */

public class Location implements Parcelable {

    static final String TAG = "LOCATIONMODEL";

    public int uid;
    public String name;
    public String address;
    public String phoneNumber;
    public String iconUrl;
    public LatLng latLng;
    public String desc;
    public String googleId;
    public String photoRef;
    public String photoUrl;
    public Bitmap photo;
    public double rating;

    public Location() {}


    public Location(PointOfInterest poi) {
        name = poi.name;
        latLng = poi.latLng;
        googleId = poi.placeId;
    }

    public static Location locationFromJson(JSONObject obj) throws JSONException{
        Location loc = new Location();
        JSONObject result = obj.getJSONObject("result");
        loc.name = result.getString("name");
        loc.address = result.getString("formatted_address");
        loc.phoneNumber = result.getString("formatted_phone_number");
        loc.iconUrl = result.getString("icon");
        JSONArray photoData = result.getJSONArray("photos");
        loc.photoRef = photoData.getJSONObject(0).getString("photo_reference");
        loc.photoUrl = GmapPlaceDetailClient.generateImageUrl(loc.photoRef);
        JSONObject latlngObj = result.getJSONObject("geometry").getJSONObject("location");
        loc.latLng = new LatLng(latlngObj.getDouble("lat"), latlngObj.getDouble("lng"));

        return loc;
    }

    public static void loadPhoto(final Location loc, final ImageView iv) {
        if (loc.photo != null) {
            iv.setImageBitmap(loc.photo);
            return;
        }
        GmapPlaceDetailClient.getImageFromReference(loc.photoRef, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d(TAG, "onSuccess: " + responseBody.toString());
                loc.photo = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                //iv.setImageBitmap(loc.photo);
                //iv.setVisibility(View.VISIBLE);
                //iv.invalidate();
                //iv.refreshDrawableState();
                Glide.with(iv.getContext()).load(responseBody).asBitmap().override(300, 300).fitCenter().into(iv);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "onFailure: " + responseBody.toString());
            }
        });
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
        dest.writeString(this.iconUrl);
        dest.writeParcelable(this.latLng, flags);
        dest.writeString(this.desc);
        dest.writeString(this.googleId);
        dest.writeString(this.photoRef);
        dest.writeDouble(this.rating);
    }

    protected Location(Parcel in) {
        this.uid = in.readInt();
        this.name = in.readString();
        this.address = in.readString();
        this.phoneNumber = in.readString();
        this.iconUrl = in.readString();
        this.latLng = in.readParcelable(LatLng.class.getClassLoader());
        this.desc = in.readString();
        this.googleId = in.readString();
        this.photoRef = in.readString();
        this.rating = in.readDouble();
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