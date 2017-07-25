package com.example.mbankole.tripplanner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by mbankole on 7/25/17.
 */

public class DCLatLng implements Parcelable {

    public double lat;
    public double lng;

    public LatLng toGLatLng() {
        return new LatLng(lat, lng);
    }

    public DCLatLng() {}

    public DCLatLng(double v1, double v2) {
        lat = v1;
        lng = v2;
    }

    public DCLatLng(LatLng latLng) {
        lat = latLng.latitude;
        lng = latLng.longitude;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lng);
    }

    protected DCLatLng(Parcel in) {
        this.lat = in.readDouble();
        this.lng = in.readDouble();
    }

    public static final Parcelable.Creator<DCLatLng> CREATOR = new Parcelable.Creator<DCLatLng>() {
        @Override
        public DCLatLng createFromParcel(Parcel source) {
            return new DCLatLng(source);
        }

        @Override
        public DCLatLng[] newArray(int size) {
            return new DCLatLng[size];
        }
    };
}
