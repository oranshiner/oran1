package com.example.oran.MapProject.controllers;

import com.example.oran.MapProject.fragments.Fragment1;
import com.example.oran.MapProject.fragments.IUserActionsOnMap;
import com.google.android.gms.maps.model.LatLng;

class TabletController implements Fragment1.IUserActions {

    private IUserActionsOnMap mapsFragment;

    public TabletController(IUserActionsOnMap mapsFragment){
        this.mapsFragment = mapsFragment;
    }

    public void onFocusOnLocation(LatLng newLocation){
        mapsFragment.onFocusOnLocation(newLocation);
    }
}
