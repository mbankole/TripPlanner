package com.example.mbankole.tripplanner.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.example.mbankole.tripplanner.adapters.PlanLocationsAdapter;
import com.example.mbankole.tripplanner.models.Location;

import java.util.Date;

public class TimePickerFragment extends android.support.v4.app.DialogFragment
                            implements TimePickerDialog.OnTimeSetListener {


    public Location location;
    public PlanLocationsAdapter adapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int hour;
        int minute;
        if (location.startTime != null) {
            hour = location.startTime.getHours();
            minute = location.startTime.getMinutes();
        } else {
            hour = 12;
            minute = 0;
        }

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (location.startTime == null) {
            location.startTime = new Date();
        }
        location.startTime.setHours(hourOfDay);
        location.startTime.setMinutes(minute);
        adapter.update();
    }
}