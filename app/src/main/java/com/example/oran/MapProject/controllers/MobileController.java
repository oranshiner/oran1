package com.example.oran.MapProject.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.oran.MapProject.activities.SecondActivity;
import com.example.oran.MapProject.fragments.Fragment1;
import com.google.android.gms.maps.model.LatLng;

class MobileController implements Fragment1.IUserActions {

    private Activity activity;

    public MobileController(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onFocusOnLocation(LatLng newLocation) {
        Intent intent = new Intent(activity, SecondActivity.class);
        intent.putExtra("commandName", "focusOnLocation");

        intent.putExtra("latitude", newLocation.latitude);
        intent.putExtra("longitude", newLocation.longitude);

        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", newLocation.latitude);
        bundle.putDouble("longitude", newLocation.longitude);
        intent.putExtras(bundle);

        activity.startActivity(intent);
    }
}
