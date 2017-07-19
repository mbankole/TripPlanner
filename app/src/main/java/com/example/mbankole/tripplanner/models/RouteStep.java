package com.example.mbankole.tripplanner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by danahe97 on 7/18/17.
 */

public class RouteStep implements Parcelable {

    public String distance;
    public String duration;
    public String htmlInstructions;
    public String maneuver;
    public LatLng startLoc;
    public LatLng endLoc;
    public String travelMode;
    public String polyline;

    public static RouteStep routeStepFromJson(JSONObject obj) throws JSONException {
        RouteStep step = new RouteStep();
        routeStepFromJson(obj, step);
        return step;
    }

    public static void routeStepFromJson(JSONObject obj, RouteStep step) throws JSONException {
        step.distance = obj.getJSONObject("distance").getString("text");
        step.duration = obj.getJSONObject("duration").getString("text");
        step.htmlInstructions = obj.getString("html_instructions").replaceAll("\\<.*?>","");
        step.maneuver = obj.optString("maneuver", null);
        step.startLoc = new LatLng(obj.getJSONObject("start_location").getDouble("lat"), obj.getJSONObject("start_location").getDouble("lng"));
        step.endLoc = new LatLng(obj.getJSONObject("end_location").getDouble("lat"), obj.getJSONObject("end_location").getDouble("lng"));
        step.travelMode = obj.getString("travel_mode");
        step.polyline = obj.getJSONObject("polyline").getString("points");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.distance);
        dest.writeString(this.duration);
        dest.writeString(this.htmlInstructions);
        dest.writeString(this.maneuver);
        dest.writeParcelable(this.startLoc, flags);
        dest.writeParcelable(this.endLoc, flags);
        dest.writeString(this.travelMode);
        dest.writeString(this.polyline);
    }

    public RouteStep() {
    }

    protected RouteStep(Parcel in) {
        this.distance = in.readString();
        this.duration = in.readString();
        this.htmlInstructions = in.readString();
        this.maneuver = in.readString();
        this.startLoc = in.readParcelable(LatLng.class.getClassLoader());
        this.endLoc = in.readParcelable(LatLng.class.getClassLoader());
        this.travelMode = in.readString();
        this.polyline = in.readString();
    }

    public static final Parcelable.Creator<RouteStep> CREATOR = new Parcelable.Creator<RouteStep>() {
        @Override
        public RouteStep createFromParcel(Parcel source) {
            return new RouteStep(source);
        }

        @Override
        public RouteStep[] newArray(int size) {
            return new RouteStep[size];
        }
    };
}