package com.example.mbankole.tripplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.models.Plan;

/**
 * Created by mbankole on 7/25/17.
 */

public class PlanEditTextFragment extends DialogFragment{
    EditText etTitle;
    EditText etDescription;
    Button btSubmit;
    public PlanListFragment planListFragment;

    private static final String TAG = "PLANEDITTEXTFRAGMENT";

    public PlanEditTextFragment() {}

    public static PlanEditTextFragment newInstance(String title, String description) {
        PlanEditTextFragment frag = new PlanEditTextFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("description", description);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan_edit_text, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etTitle = (EditText) view.findViewById(R.id.etTitle);
        etDescription = (EditText) view.findViewById(R.id.etDescription);
        btSubmit = (Button) view.findViewById(R.id.btSubmit);

        if (!getArguments().getString("title").equals("New Plan")) {
            etTitle.setText(getArguments().getString("title"));
        }
        if (getArguments().getString("description") != null) {
            //something something description
        }

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plan plan = new Plan();
                plan.title = etTitle.getText().toString();
                plan.description = etDescription.getText().toString();
                planListFragment.tvTitle.setText(plan.title);
                PlanEditTextListener listener = (PlanEditTextListener) getActivity();
                listener.onFinishEditText(plan);
                dismiss();
            }
        });
    }

    public interface PlanEditTextListener {
        void onFinishEditText(Plan plan);
    }
}