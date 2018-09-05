package com.example.oran.MapProject.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.oran.MapProject.R;
import com.google.android.gms.maps.model.LatLng;

public class Fragment1 extends Fragment {

    private IUserActions parentActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_fragment1, container, false);
        Button button = (Button) fragmentView.findViewById(R.id.searchButton);

        // The fragment delegates the click event, to the activity
        // Because in this specific excercise, we've decided that the button's behavior context related
        // context related = the activity decides (the behavior can change in different activities)
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng newLocation = new LatLng(32.109333, 34.855499);
                parentActivity.onFocusOnLocation(newLocation);

            }
        });
        return fragmentView;
    }

    @Override
    // The context is in fact the activity which hosts the fragment
    // This function is being called after the activity is being created
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IUserActions) {
            // If the activity which holds the current fragment, obeys to the rules in the
            // "contract", defined in the interface ("IUserActions"), then we save a
            // reference to the external activity, in order to call it, each time the button
            // had been pressed
            this.parentActivity = (IUserActions) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IUserActions");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parentActivity = null;
    }

    public interface IUserActions {
        public void onFocusOnLocation(LatLng newLocation);
    }
}
