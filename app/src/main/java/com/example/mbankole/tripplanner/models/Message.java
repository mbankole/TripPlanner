package com.example.mbankole.tripplanner.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mbankole on 7/31/17.
 */

public class Message implements Parcelable {
    String senderUid;
    String body;
    String senderUsername;
    String senderProfileUrl;
    boolean received;
    boolean request;
    String requestType;
    String locationName;
    String lcoationImageUrl;
    String requestTargetGid;
    String dbUid;

    public String getDbUid() {
        return dbUid;
    }

    public void setDbUid(String dbUid) {
        this.dbUid = dbUid;
    }

    public Message () {}

    public String getSenderUid() {
        return senderUid;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLcoationImageUrl() {
        return lcoationImageUrl;
    }

    public void setLcoationImageUrl(String lcoationImageUrl) {
        this.lcoationImageUrl = lcoationImageUrl;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getSenderProfileUrl() {
        return senderProfileUrl;
    }

    public void setSenderProfileUrl(String senderProfileUrl) {
        this.senderProfileUrl = senderProfileUrl;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public boolean isRequest() {
        return request;
    }

    public void setRequest(boolean request) {
        this.request = request;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestTargetGid() {
        return requestTargetGid;
    }

    public void setRequestTargetGid(String requestTargetGid) {
        this.requestTargetGid = requestTargetGid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.senderUid);
        dest.writeString(this.body);
        dest.writeString(this.senderUsername);
        dest.writeString(this.senderProfileUrl);
        dest.writeByte(this.received ? (byte) 1 : (byte) 0);
        dest.writeByte(this.request ? (byte) 1 : (byte) 0);
        dest.writeString(this.requestType);
        dest.writeString(this.locationName);
        dest.writeString(this.lcoationImageUrl);
        dest.writeString(this.requestTargetGid);
        dest.writeString(this.dbUid);
    }

    protected Message(Parcel in) {
        this.senderUid = in.readString();
        this.body = in.readString();
        this.senderUsername = in.readString();
        this.senderProfileUrl = in.readString();
        this.received = in.readByte() != 0;
        this.request = in.readByte() != 0;
        this.requestType = in.readString();
        this.locationName = in.readString();
        this.lcoationImageUrl = in.readString();
        this.requestTargetGid = in.readString();
        this.dbUid = in.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
