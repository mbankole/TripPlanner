package com.example.mbankole.tripplanner.models;

import android.os.Parcel;
import android.os.Parcelable;

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
    public DCLatLng startLoc;
    public DCLatLng endLoc;
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
        if (obj.has("maneuver")) {
            step.maneuver = obj.getString("maneuver");
        } else {
            step.maneuver = null;
        }
        step.startLoc = new DCLatLng(obj.getJSONObject("start_location").getDouble("lat"), obj.getJSONObject("start_location").getDouble("lng"));
        step.endLoc = new DCLatLng(obj.getJSONObject("end_location").getDouble("lat"), obj.getJSONObject("end_location").getDouble("lng"));
        step.travelMode = obj.getString("travel_mode");
        step.polyline = obj.getJSONObject("polyline").getString("points");
    }

    public RouteStep() {
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getHtmlInstructions() {
        return htmlInstructions;
    }

    public void setHtmlInstructions(String htmlInstructions) {
        this.htmlInstructions = htmlInstructions;
    }

    public String getManeuver() {
        return maneuver;
    }

    public void setManeuver(String maneuver) {
        this.maneuver = maneuver;
    }

    public DCLatLng getStartLoc() {
        return startLoc;
    }

    public void setStartLoc(DCLatLng startLoc) {
        this.startLoc = startLoc;
    }

    public DCLatLng getEndLoc() {
        return endLoc;
    }

    public void setEndLoc(DCLatLng endLoc) {
        this.endLoc = endLoc;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public String getPolyline() {
        return polyline;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
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

    protected RouteStep(Parcel in) {
        this.distance = in.readString();
        this.duration = in.readString();
        this.htmlInstructions = in.readString();
        this.maneuver = in.readString();
        this.startLoc = in.readParcelable(DCLatLng.class.getClassLoader());
        this.endLoc = in.readParcelable(DCLatLng.class.getClassLoader());
        this.travelMode = in.readString();
        this.polyline = in.readString();
    }

    public static final Creator<RouteStep> CREATOR = new Creator<RouteStep>() {
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