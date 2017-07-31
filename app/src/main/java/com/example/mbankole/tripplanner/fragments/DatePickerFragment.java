package com.example.mbankole.tripplanner.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.DatePicker;

import com.example.mbankole.tripplanner.models.Plan;

import java.util.Date;

public class DatePickerFragment extends android.support.v4.app.DialogFragment
                            implements DatePickerDialog.OnDateSetListener {

    public Plan plan;
    public PlanListFragment planListFragment;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year;
        int month;
        int day;
        // Use the current date as the default date in the picker
        if (plan.startDate != null) {
            year = plan.startDate.getYear();
            month = plan.startDate.getMonth();
            day = plan.startDate.getDate();
        } else {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        if (plan.startDate == null) {
            plan.startDate = new Date();
        }
        plan.startDate.setYear(year - 1900);
        plan.startDate.setMonth(month);
        plan.startDate.setDate(day);
        planListFragment.refreshDate();
    }
}