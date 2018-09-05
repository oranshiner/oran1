package com.example.oran.MapProject.activities;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.design.widget.Snackbar;


import com.example.oran.MapProject.R;
import com.example.oran.MapProject.adapters.MyAdapter;
import com.example.oran.MapProject.controllers.ControllersFactory;
import com.example.oran.MapProject.dao.PlacesDao;
import com.example.oran.MapProject.fragments.Fragment1;
import com.example.oran.MapProject.receiver.PowerConnectionReceiver;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements Fragment1.IUserActions, LocationListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Fragment1.IUserActions userActionsController;
    private PlacesDao placesDao;
    private LocationManager locationManager;
    private SharedPreferences sp;
    private String[] data;



    PowerConnectionReceiver broadcastReceiver = new PowerConnectionReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        this.data = new String[]{"Avi", "Dani", "Roni", "Eli", "Shelly", "Maya", "Oren", "Eyal_1"};

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(data, new MyRecyclerViewOnClickListener());
        mRecyclerView.setAdapter(mAdapter);
        // Getting a tablet or a mobile controller
        // the factory hides the decision making.
        userActionsController = ControllersFactory.createUserInteractionsController(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        this.registerReceiver(broadcastReceiver, filter);

        // get the location manager service
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // initiate my custom shared preferences
        sp = getSharedPreferences("PlaceSp", MODE_PRIVATE);

        // runtime permission check
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // if no permission to location ask permission from the user
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 99);
            }
            return;
        }

        // start listening to location
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 50, this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // what to do when user clicks on menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_example:
                break;
            // Launch preference screen
            case R.id.setting:
                Intent intentPref = new Intent(this, PreferencesActivity.class);
                if(intentPref != null) {
                    startActivity(intentPref);
                }
                break;

            case R.id.deleteAll:
                try {
                    placesDao.deleteAllPlaces();
                } catch (NullPointerException e) {
                    Toast.makeText(this, "There are no places in your favourites list yet", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.quit:
                break;
                default:
                    return super.onOptionsItemSelected(item);

        }
        return true;
    }

    @Override
    public void onFocusOnLocation(LatLng newLocation) {
        // Carrying out the user's request, using our controller
        userActionsController.onFocusOnLocation(newLocation);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onLocationChanged(Location location) {

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        sp.edit().putFloat("mylat", (float) latitude).apply();
        sp.edit().putFloat("mylng", (float) longitude).apply();
    }


    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class MyRecyclerViewOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int itemPosition = mRecyclerView.getChildLayoutPosition(view);

            // We extract the item which the user clicked on (in this example i use an array, may as
            // well be using a list of course
            String item = data[itemPosition];

            Snackbar.make(view, item.toString(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}




//##### DAO TESTER #####
//
//        Place place = new Place("name", "address", 33.3, 44.4, "urlimage");
//        PlacesDao placesDao = new PlacesDao(this);
//        long id = placesDao.addPlace(place);
//        Place place2 = placesDao.getPlace(id);
//        placesDao.deletePlace(id);
//        place2 = placesDao.getPlace(id);
//
//        if (place2 != null){
//            Log.e("TesterActivity", "Failed to delete place id = " + id);
//        }
//
//        place = new Place("name", "address", 33.3, 44.4, "urlimage");
//        long id1 = placesDao.addPlace(place);
//
//        Place place3 = new Place("name2", "address2", 33.3, 44.4, "urlimage2");
//        long id3 = placesDao.addPlace(place);
//
//        placesDao.deleteAllPlaces();
//        place = placesDao.getPlace(id1);
//        place3 = placesDao.getPlace(id3);
//
//        if (place!=null || place3!=null){
//            Log.e("TesterActivity", "Failed to delete all places");
//        }