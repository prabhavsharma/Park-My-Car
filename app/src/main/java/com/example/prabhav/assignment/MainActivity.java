package com.example.prabhav.assignment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseUser;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "8TUxv6ZFCPGeO4VPmptd5fpWMx9MlK29nTAonqq8", "dcPjNdMcUcJMz8H4y3YkzTIkUmfPFJ7sjIyOZiDm");
        // Check if there is current user info
        if (ParseUser.getCurrentUser() != null) {
            // Start an intent for the logged in activity
            startActivity(new Intent(this, MyActivity.class));
        } else {
            // Start and intent for the logged out activity
            startActivity(new Intent(this, SignUpOrLoginActivity.class));
        }
    }
}