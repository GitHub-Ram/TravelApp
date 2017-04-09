package com.ixigo.travelapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ixigo.travelapp.R;
import com.ixigo.travelapp.activity.PlanActivity;
import com.ixigo.travelapp.activity.TripActivity;
import com.ixigo.travelapp.utill.TripInfo;
import com.ixigo.travelapp.utill.Utility;

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
        LinearLayout layout = (LinearLayout)rootView.findViewById(R.id.add_current_trip_Layout);
        RecyclerView currentTripList = (RecyclerView)rootView.findViewById(R.id.current_trip_recyclerView);
        Button addCurrentTrip = (Button) rootView.findViewById(R.id.add_current_trip_button);
        addCurrentTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PlanActivity.class);
                startActivity(i);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent i = new Intent(getContext(), PlanActivity.class);
                startActivity(i);
            }
        });
        TripInfo ti = Utility.getCurrentTrip(getContext());
        if(ti.getDestination().equals("NA")){
            currentTripList.setVisibility(View.GONE);
            currentTripList.setVisibility(View.VISIBLE);
        }else{
            currentTripList.setVisibility(View.VISIBLE);
            currentTripList.setVisibility(View.GONE);
        }
        return rootView;
    }
}
