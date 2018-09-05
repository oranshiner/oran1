package com.example.oran.MapProject.controllers;

import android.support.v4.app.FragmentActivity;


import com.example.oran.MapProject.R;
import com.example.oran.MapProject.fragments.Fragment1;
import com.example.oran.MapProject.fragments.IUserActionsOnMap;

public class ControllersFactory {

    public static Fragment1.IUserActions createUserInteractionsController(FragmentActivity activity) {

        // Getting a reference to the maps fragment, ONLY FOUND IN THE TABLET MODE
        IUserActionsOnMap mapFragment = (IUserActionsOnMap) activity.getSupportFragmentManager().findFragmentById(R.id.map);

        // If the fragment exists --> Tablet mode
        if (mapFragment != null) {
            return new TabletController(mapFragment);
        }
        else
        {
            // Fragment is null --> Mobile mode
            return new MobileController(activity);
        }


    }
}
