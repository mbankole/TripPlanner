package com.example.mbankole.tripplanner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by mbankole on 7/18/17.
 */

public class TransportOption implements Parcelable {
    public String name;
    public Location start;
    public Location end;
    public LatLng startLatLong;
    public LatLng endLatLong;
    public String startId;
    public String endId;

    public enum Mode {
        DRIVING,
        WALKING,
        TRANSIT,
        BLANK
    }

    public Mode mode;

    public TransportOption() {}

    public TransportOption(Location start, Location end) {
        this.start = start;
        this.end = end;
        this.startLatLong = start.latLng;
        this.endLatLong = end.latLng;
        this.startId = start.googleId;
        this.endId = end.googleId;
    }

    public TransportOption(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeParcelable(this.start, flags);
        dest.writeParcelable(this.end, flags);
        dest.writeParcelable(this.startLatLong, flags);
        dest.writeParcelable(this.endLatLong, flags);
        dest.writeString(this.startId);
        dest.writeString(this.endId);
        dest.writeInt(this.mode == null ? -1 : this.mode.ordinal());
    }

    protected TransportOption(Parcel in) {
        this.name = in.readString();
        this.start = in.readParcelable(Location.class.getClassLoader());
        this.end = in.readParcelable(Location.class.getClassLoader());
        this.startLatLong = in.readParcelable(LatLng.class.getClassLoader());
        this.endLatLong = in.readParcelable(LatLng.class.getClassLoader());
        this.startId = in.readString();
        this.endId = in.readString();
        int tmpMode = in.readInt();
        this.mode = tmpMode == -1 ? null : Mode.values()[tmpMode];
    }

    public static final Parcelable.Creator<TransportOption> CREATOR = new Parcelable.Creator<TransportOption>() {
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
