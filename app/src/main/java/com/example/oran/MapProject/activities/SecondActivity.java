package com.example.oran.MapProject.activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.oran.MapProject.R;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class SecondActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        double latitude = getIntent().getDoubleExtra("latitude", 0);
        double longitude = getIntent().getDoubleExtra("longitude", 0);





//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2);

        // The fragment will read the new arguments and will initialize itself based on them
//        savedInstanceState.put.setArguments(bundleArguments);
    }
}
