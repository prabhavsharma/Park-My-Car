package com.example.prabhav.assignment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by Sneha on 09-05-2015.
 */
public class ViewParking extends Activity {
    private static final String TAG = "ViewParking";
    private Db_handler h = new Db_handler(this, null,null,1);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO - fill in here
        Log.i(TAG, "OnCreate called - ViewParking");

        setContentView(R.layout.activity_view_park);
        Intent i = this.getIntent();
        try {
            long tid = this.getParkId(i);

            if (tid == 0) {
                throw new Exception();
            } else {
                Contact tobj = h.getOneContact(tid);
                this.viewTrip(tobj);
            }
                
        } catch (Exception e) {
            Toast.makeText(this, "No parkings to show!!!", Toast.LENGTH_LONG).show();
        }
    }




    public long getParkId(Intent i) {

        // TODO - fill in here
        if (i != null) {
            Bundle b = new Bundle();
            b = i.getExtras();
            double mlat = b.getDouble("Lat");
            double mlong = b.getDouble("longi");

           long trip_id = h.getId(mlat,mlong);
            return trip_id;
        }
        return 0;

    }

    public void viewTrip(Contact c) {
        TextView t = (TextView)findViewById(R.id.textView6);
        t.setText(c.getName());
        TextView t1 = (TextView)findViewById(R.id.textView7);
        t1.setText(c.getPhone());
        TextView t2 = (TextView)findViewById(R.id.textView8);
        t2.setText(c.getEmail());
        ImageView i = (ImageView)findViewById(R.id.imageView);
        i.setImageURI(c.getImageURI());

    }

   public void direction(View view) {
       LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
       Criteria criteria = new Criteria();
       Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
       Intent i1 = this.getIntent();
       Bundle b1;
       b1 = i1.getExtras();

       String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)", location.getLatitude(), location.getLongitude(), "My current location", b1.getDouble("Lat"), b1.getDouble("longi"), "Where the parking is!!!");
       Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
       intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
       try {
           startActivity(intent);
       } catch (ActivityNotFoundException ex) {
           try {
               Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
               startActivity(unrestrictedIntent);
           } catch (ActivityNotFoundException innerEx) {
               Toast.makeText(this, "Please install a maps application", Toast.LENGTH_LONG).show();
           }
       }
   }
}
