package com.example.lmnop.onemeetingawaymap1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lmnop.onemeetingawaymap1.Adapters.ListMeetingsAdapter;
import com.example.lmnop.onemeetingawaymap1.DataBase.DataSource;
import com.example.lmnop.onemeetingawaymap1.DataBase.MeetingsTable;
import com.example.lmnop.onemeetingawaymap1.model.DataItemMeetings;
import com.example.lmnop.onemeetingawaymap1.sample.SampleDataProvider;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.clustering.ClusterManager;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{

    //declare constants
    private static final String TAG = "MapsActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15;

    public List<DataItemMeetings> dataItemMeetingsList;
    DataSource mDataSource;

    public String Meeting = "2807 w viewmont way w, settle wa 98199";
    public String Name = "My old house";
    public GoogleMap mMap;
    private ClusterManager<MyItem> mClusterManager;


    public FusedLocationProviderClient fusedLocationProviderClient;

    //added for navbar
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        dataItemMeetingsList = SampleDataProvider.dataItemMeetingsList;
        mDataSource = new DataSource(this);
        mDataSource.open();
        mDataSource.seedDataBase(dataItemMeetingsList);

        getLocationPermission();

        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);
//        View.OnClickListener onClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent;
//                switch (v.getId()){
//                    case R.id.nav_list:
//                        intent = new Intent(MapsActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        Toast.makeText(getApplicationContext(), "OnClick: ShowListView", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.main_frame:
//                        intent = new Intent(ListMeetingsAdapter.this, MapsActivity.class);
//                        startActivity(intent);
//                        break;
//                }
//            }
//        };
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();

                Toast.makeText(getApplicationContext(), "ItemID: " + item.getItemId(), Toast.LENGTH_LONG).show();

                Intent intent;
                switch (item.getItemId()){
                    case 2131230831:
                        intent = new Intent(MapsActivity.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "OnClick: ShowListView", Toast.LENGTH_SHORT).show();
                        break;
//                        fragmentManager.beginTransaction()
//                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
//                                .show(getSupportFragmentManager().findFragmentById(R.id.recyclerview))
//                                .commit();

//                    case 2131230832:
//                        fragmentManager.beginTransaction()
//                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
//                                .show(getSupportFragmentManager().findFragmentById(R.id.recyclerview))
//                                .commit();
                }
                return false;
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
        }




        new LongRunningTask().execute();


        //added for navbar


//        mMainNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
//            @Override
//            public void onNavigationItemReselected(@NonNull MenuItem item) {
//                FragmentManager fragmentManager = getSupportFragmentManager();
//
//                switch (item.getItemId()){
//                    case R.id.nav_map:
//                        fragmentManager.beginTransaction()
//                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
//                                .show(getSupportFragmentManager().findFragmentById(R.id.map))
//                                .commit();
//                        //
//                    case R.id.nav_list:
//                        fragmentManager.beginTransaction()
//                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
//                                .show(getSupportFragmentManager().findFragmentById(R.id.recyclerview))
//                                .commit();
//                }
//
//            }
//
//
//        });

    }




    private class LongRunningTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //start loading icon
            //let user know something will happen
            Log.d(TAG, "onPreExecute: Before Task");
        }


        @Override
        protected Void doInBackground(Void... voids) {
            //AWS network call
            Log.d(TAG, "doInBackground: AWS CALL");

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {

                    setUpClusterer();

                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //update UI
            Log.d(TAG, "onPostExecute: After Task");
        }
    }




    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {

                final Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);
                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }


    //method to move the camera
    private void moveCamera(LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }


    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapsActivity.this);
    }

    private void getLocationPermission() {
        String[] permissions = {FINE_LOCATION,
                COURSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionsGranted = true;
                    //initialize map
                    initMap();
                }
            }
        }
    }

    private void setUpClusterer() {

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyItem>(this, mMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        addItems();
    }

    private void addItems() {

        List<DataItemMeetings> meet = mDataSource.getPins();
        for (int i = 0; i < meet.size(); i++) {
            String slat = meet.get(i).getLat();
            String slng = meet.get(i).getLng();
            double lat = Double.parseDouble(slat);
            double lng = Double.parseDouble(slng);
            MyItem setItem = new MyItem(lng, lat, meet.get(i).getMeetingName(), meet.get(i).getDay());
            mClusterManager.addItem(setItem);

        }
        mClusterManager.cluster();
//        mClusterManager.setRenderer(new OwnIconRendered(this.getApplicationContext(), mMap, mClusterManager));
    }





}
