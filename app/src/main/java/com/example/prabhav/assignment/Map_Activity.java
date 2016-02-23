package com.example.prabhav.assignment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


import android.app.Activity;

import android.content.Context;
import android.content.Intent;


import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class Map_Activity extends Activity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final String TAG = "MainActivity";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO - fill in here
        Log.i(TAG, "OnCreate called - MapActivity");

        setContentView(R.layout.activity_main);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }
    @Override
    public void onMapReady(GoogleMap map) {
        map.setMyLocationEnabled(true);


        // Get latitude and longitude for current location
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

        // zoom to current location
        double mylat = location.getLatitude();
        double mylongi = location.getLongitude();

        LatLng cp = new LatLng(mylat, mylongi);
        CameraPosition camPos = new CameraPosition.Builder().target(cp).zoom(15).build();
        CameraUpdate cam = CameraUpdateFactory.newCameraPosition(camPos);
        map.animateCamera(cam);
        Db_handler handler = new Db_handler(this, null, null, 1);
        if (handler!=null) {
            ArrayList<Double> c = handler.lat();
            Log.i(TAG, "Value:" + c);
            ArrayList<Double> c1 = handler.longi();
            Log.i(TAG, "Value:" + c1);

            for (int i = 0; i < c.size(); i++) {



                LatLng park = new LatLng(c.get(i), c1.get(i));

                // set custom marker

                map.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_parking))
                        .position(park));
                map.setOnMarkerClickListener(this);
            }

        }
        else
            Toast.makeText(getApplicationContext(), "No Parkings available to show", Toast.LENGTH_LONG).show();
    }

    public void startAddParkingActivity(View view){
        Intent intent=new Intent(this, AddParking.class);
        startActivityForResult(intent,1);

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng pos = marker.getPosition();
        double mlat = pos.latitude;
        double mlong = pos.longitude;
        Intent intent = new Intent(this, ViewParking.class);
        intent.putExtra("Lat",mlat);
        intent.putExtra("longi", mlong);
        startActivity(intent);

        return false;
    }
}
