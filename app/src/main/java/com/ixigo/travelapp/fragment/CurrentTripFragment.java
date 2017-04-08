package com.ixigo.travelapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ixigo.travelapp.R;
import com.ixigo.travelapp.activity.TripActivity;

/**
 * Created by F49558B on 4/8/2017.
 */

public class CurrentTripFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public CurrentTripFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trip, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        return rootView;
    }
}
