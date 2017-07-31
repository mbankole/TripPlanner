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

    public Message () {}

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
    }

    protected Message(Parcel in) {
        this.senderUid = in.readString();
        this.body = in.readString();
        this.senderUsername = in.readString();
        this.senderProfileUrl = in.readString();
        this.received = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public String getSenderUid() {
        return senderUid;
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
}
