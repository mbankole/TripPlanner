package com.example.mbankole.tripplanner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danahe97 on 7/17/17.
 */

public class Route implements Parcelable {

    public String overviewPolyline;
    public List<DCLatLng> latLongArray;
    public ArrayList<RouteStep> steps;
    public String distance;
    public String duration;
    public String startAddress;
    public String endAddress;
    public DCLatLng startLoc;
    public DCLatLng endLoc;

    public static Route routeFromJson(JSONObject obj) throws JSONException {
        Route route = new Route();
        routeFromJson(obj,route);
        return route;
    }

    public static void routeFromJson(JSONObject obj, Route route) throws JSONException{
        route.steps = new ArrayList<>();
        JSONObject result = obj.getJSONArray("routes").getJSONObject(0);
        route.overviewPolyline = result.getJSONObject("overview_polyline").getString("points");
        ArrayList<LatLng> tempLatLngArray = (ArrayList) PolyUtil.decode(route.overviewPolyline);
        route.latLongArray = new ArrayList<>();
        for (int i = 0; i < tempLatLngArray.size(); i++) {
            route.latLongArray.add(new DCLatLng(tempLatLngArray.get(i)));
        }
        JSONObject legs = result.getJSONArray("legs").getJSONObject(0);

        JSONArray steps = legs.getJSONArray("steps");
        for (int i = 0; i < steps.length(); i++) {
            route.steps.add(RouteStep.routeStepFromJson(steps.getJSONObject(i)));
        }

        route.distance = legs.getJSONObject("distance").getString("text");
        route.duration = legs.getJSONObject("duration").getString("text");
        route.startAddress = legs.getString("start_address");
        route.endAddress = legs.getString("end_address");
        route.startLoc = new DCLatLng(legs.getJSONObject("start_location").getDouble("lat"),
                legs.getJSONObject("start_location").getDouble("lng"));
        route.endLoc = new DCLatLng(legs.getJSONObject("end_location").getDouble("lat"),
                legs.getJSONObject("end_location").getDouble("lng"));

    }

    public Route() {
    }

    public String getOverviewPolyline() {
        return overviewPolyline;
    }

    public void setOverviewPolyline(String overviewPolyline) {
        this.overviewPolyline = overviewPolyline;
    }

    public List<DCLatLng> getLatLongArray() {
        return latLongArray;
    }

    public void setLatLongArray(List<DCLatLng> latLongArray) {
        this.latLongArray = latLongArray;
    }

    public ArrayList<RouteStep> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<RouteStep> steps) {
        this.steps = steps;
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

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.overviewPolyline);
        dest.writeTypedList(this.latLongArray);
        dest.writeTypedList(this.steps);
        dest.writeString(this.distance);
        dest.writeString(this.duration);
        dest.writeString(this.startAddress);
        dest.writeString(this.endAddress);
        dest.writeParcelable(this.startLoc, flags);
        dest.writeParcelable(this.endLoc, flags);
    }

    protected Route(Parcel in) {
        this.overviewPolyline = in.readString();
        this.latLongArray = in.createTypedArrayList(DCLatLng.CREATOR);
        this.steps = in.createTypedArrayList(RouteStep.CREATOR);
        this.distance = in.readString();
        this.duration = in.readString();
        this.startAddress = in.readString();
        this.endAddress = in.readString();
        this.startLoc = in.readParcelable(LatLng.class.getClassLoader());
        this.endLoc = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Creator<Route> CREATOR = new Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel source) {
            return new Route(source);
        }

        @Override
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };
}
