package com.example.mbankole.tripplanner.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mbankole on 7/20/17.
 */

public class Plan implements Parcelable {
    public ArrayList<Location> places;
    public ArrayList<User> people;
    public String title;
    public String description;
    public String creatorUid;
    public Date startDate;
    public Date endDate;

    public static Plan generateSeattlePlan(Context context) {
        Plan plan = new Plan();
        plan.places = new ArrayList<>();
        plan.people = new ArrayList<>();
        plan.title = "Trip to Seattle";
        plan.description = "Looking at a couple of landmarks";
        //plan.people.add(User.generateChandler());
        plan.people.add(User.generateRachel());
        plan.people.add(User.generatePhoebe());
        plan.places.add(Location.generateWheel(context));
        plan.places.add(Location.generateArboretum(context));
        //plan.places.add(Location.generateArtMuseum());
        plan.places.add(Location.generateNeedle(context));
        //plan.places.add(Location.generatePopCulture());
        return plan;
    }

    public Plan() {
    }

    public static Plan newPlan() {
        Plan plan = new Plan();
        plan.places = new ArrayList<>();
        plan.people = new ArrayList<>();
        plan.title = "New Plan";
        return plan;
    }

    public static Plan newPlan(String creatorUid) {
        Plan plan = new Plan();
        plan.places = new ArrayList<>();
        plan.people = new ArrayList<>();
        plan.title = "New Plan";
        plan.creatorUid = creatorUid;
        return plan;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatorUid() {
        return creatorUid;
    }

    public void setCreatorUid(String creatorUid) {
        this.creatorUid = creatorUid;
    }

    public ArrayList<Location> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Location> places) {
        this.places = places;
    }

    public ArrayList<User> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<User> people) {
        this.people = people;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.places);
        dest.writeTypedList(this.people);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.creatorUid);
        dest.writeLong(this.startDate != null ? this.startDate.getTime() : -1);
        dest.writeLong(this.endDate != null ? this.endDate.getTime() : -1);
    }

    protected Plan(Parcel in) {
        this.places = in.createTypedArrayList(Location.CREATOR);
        this.people = in.createTypedArrayList(User.CREATOR);
        this.title = in.readString();
        this.description = in.readString();
        this.creatorUid = in.readString();
        long tmpStartDate = in.readLong();
        this.startDate = tmpStartDate == -1 ? null : new Date(tmpStartDate);
        long tmpEndDate = in.readLong();
        this.endDate = tmpEndDate == -1 ? null : new Date(tmpEndDate);
    }

    public static final Creator<Plan> CREATOR = new Creator<Plan>() {
        @Override
        public Plan createFromParcel(Parcel source) {
            return new Plan(source);
        }

        @Override
        public Plan[] newArray(int size) {
            return new Plan[size];
        }
    };
}
