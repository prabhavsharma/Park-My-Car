package com.example.prabhav.assignment;

/**
 * Created by Sneha on 26-04-2015.
 */

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;



public class ParkMap extends Activity {

    private static final String TAG = "ParkMapActivity";
    private Db_handler handler = new Db_handler(this,null,null,1);
    private GoogleMap googleMap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            }

            List<String> c;
            c = handler.getAllAddress();


            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            googleMap.addMarker(new MarkerOptions().position(new LatLng(40.7127, -74.0059)));
            Double[] latitude = new Double[c.size()];
            Double[] longitude = new Double[c.size()];
            String[] addrs = new String[c.size()];
            addrs = c.toArray(addrs);
            List<Address> addressList;

            if (addrs != null && addrs.length > 0) {
                for (int i = 0; i < addrs.length; i++) {
                    try {

                        Geocoder geoCoder = new Geocoder(this);
                        addressList = geoCoder.getFromLocationName(addrs[i], 1);
                        if (addressList == null || addressList.isEmpty() || addressList.equals("")) {
                            addressList = geoCoder.getFromLocationName("san francisco", 1);
                        }
                        latitude[i] = addressList.get(0).getLatitude();
                        longitude[i] = addressList.get(0).getLongitude();
                        System.out.println("latitude = " + latitude[i] + " longitude = " + longitude[i]);

                        //Marker tp = googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude[i], longitude[i])));


                    } catch (Exception e) {
                        e.printStackTrace();
                    } // end catch
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    }
