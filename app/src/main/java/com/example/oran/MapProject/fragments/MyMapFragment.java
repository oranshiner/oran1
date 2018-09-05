package com.example.oran.MapProject.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.oran.MapProject.R;
import com.example.oran.MapProject.adapters.PlacesAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.Context.MODE_PRIVATE;


public class MyMapFragment extends Fragment implements OnMapReadyCallback, IUserActionsOnMap, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private LatLng currentLocation;
    private LatLng placelatLng, places_latLng;
    private SharedPreferences sp;
    private Marker marker;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This section will need refactoring on the long run

        LatLng defaultLocation = new LatLng(33, 34);
        this.currentLocation = defaultLocation;

        // Exreacrting the intent from the wrapping activity
        Intent intent = ((Activity) getContext()).getIntent();

        // Initializing the map's location based on a command sent by the previous activity
        // This code segment is ONLY relevant to mobile mode, and not tablet mode
        String commandName = intent.getStringExtra("commandName");
        if (commandName != null && commandName.equals("focusOnLocation")) {
            double latitude = intent.getDoubleExtra("latitude", 0);
            double longitude = intent.getDoubleExtra("longitude", 0);
            this.currentLocation = new LatLng(latitude, longitude);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment, connect and show map
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);

        //get map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        //connect to my custom created sp
        sp = getContext().getSharedPreferences("PlaceSp", MODE_PRIVATE);
        return view;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(this.currentLocation));

        // implement the interface, making the map clickable
        mMap.setOnMapClickListener(this);

        // check if we have permission for location
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // change the map type (satellite, normal, hybrid)
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.setMyLocationEnabled(true);

        placelatLng = new LatLng(sp.getFloat("ClickedPlaceLat", 34.65f), sp.getFloat("ClickedPlaceLng", 32.25f));
        places_latLng = new LatLng(sp.getFloat("mylat", 34.65f), sp.getFloat("mylng", 32.25f));
        String name = sp.getString("place_name", "Going Places!");


        // add marker
        marker = mMap.addMarker(new MarkerOptions().position(placelatLng).title(name).alpha(0.7f));


    }

    @Override
    public void onFocusOnLocation(LatLng newLocation) {
        this.currentLocation = newLocation;

        if (mMap != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLng(newLocation));
            mMap.addMarker(new MarkerOptions().position(newLocation));
        }
    }


    @Override
    public void onMapClick(LatLng latLng) {
        // show the clicked location with a Toast
        Toast.makeText(this.getContext(), "Lat: " + String.format("%.3f", latLng.latitude) + ", Lon: " + String.format("%.3f", latLng.longitude), Toast.LENGTH_SHORT).show();

        // calculate the distance from my place to clicked location and show with a Toast
        double distance = PlacesAdapter.distance(places_latLng.latitude, latLng.latitude, places_latLng.longitude, latLng.longitude, 0, 0);
        //connect to the default sp
        sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        String units = sp.getString("units_key", "km");
        if (units.equals("miles")) {
            //convert distance to miles
            distance = distance * 0.621371;
        }
        Toast.makeText(this.getContext(), "Distance from my spot: " + String.format("%.2f", distance), Toast.LENGTH_SHORT).show();

    }
}
