package com.example.mbankole.tripplanner.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mbankole on 7/18/17.
 */

public class TransportOption implements Parcelable {
    public String name;
    //public Location start;
    //public Location end;
    public DCLatLng startLatLong;
    public DCLatLng endLatLong;
    public String startId;
    public String endId;
    public String encodedPolyLine;
    public Route route;

    public enum Mode {
        DRIVING,
        WALKING,
        TRANSIT,
        BLANK
    }

    public Mode mode;

    public TransportOption() {}

    public static TransportOption newInstance() {
        TransportOption transportOption = new TransportOption();
        transportOption.name = "";
        transportOption.mode = Mode.BLANK;
        return transportOption;
    }

    public TransportOption(Location start, Location end) {
        //this.start = start;
        //this.end = end;
        this.startLatLong = start.latLng;
        this.endLatLong = end.latLng;
        this.startId = start.googleId;
        this.endId = end.googleId;
    }

    public TransportOption(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DCLatLng getStartLatLong() {
        return startLatLong;
    }

    public void setStartLatLong(DCLatLng startLatLong) {
        this.startLatLong = startLatLong;
    }

    public DCLatLng getEndLatLong() {
        return endLatLong;
    }

    public void setEndLatLong(DCLatLng endLatLong) {
        this.endLatLong = endLatLong;
    }

    public String getStartId() {
        return startId;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public String getEndId() {
        return endId;
    }

    public void setEndId(String endId) {
        this.endId = endId;
    }

    public String getEncodedPolyLine() {
        return encodedPolyLine;
    }

    public void setEncodedPolyLine(String encodedPolyLine) {
        this.encodedPolyLine = encodedPolyLine;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeParcelable(this.startLatLong, flags);
        dest.writeParcelable(this.endLatLong, flags);
        dest.writeString(this.startId);
        dest.writeString(this.endId);
        dest.writeString(this.encodedPolyLine);
        dest.writeParcelable(this.route, flags);
        dest.writeInt(this.mode == null ? -1 : this.mode.ordinal());
    }

    protected TransportOption(Parcel in) {
        this.name = in.readString();
        this.startLatLong = in.readParcelable(DCLatLng.class.getClassLoader());
        this.endLatLong = in.readParcelable(DCLatLng.class.getClassLoader());
        this.startId = in.readString();
        this.endId = in.readString();
        this.encodedPolyLine = in.readString();
        this.route = in.readParcelable(Route.class.getClassLoader());
        int tmpMode = in.readInt();
        this.mode = tmpMode == -1 ? null : Mode.values()[tmpMode];
    }

    public static final Creator<TransportOption> CREATOR = new Creator<TransportOption>() {
        @Override
        public TransportOption createFromParcel(Parcel source) {
            return new TransportOption(source);
        }

        @Override
        public TransportOption[] newArray(int size) {
            return new TransportOption[size];
        }
    };
}
