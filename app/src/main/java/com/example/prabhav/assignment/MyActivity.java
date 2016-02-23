package com.example.prabhav.assignment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


/**
 * Created by Prabhav on 10-05-2015.
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my);
        Parse.initialize(this, "dPVkrOx9fSuEgKkkbMcF9GiRPYz8pG2rLFHZ7c4a", "E2dW29wwnvDFmDMCgFIXFYYqAJW4N9FGEhY6d4lA");

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            ParseUser.getCurrentUser().logOut();
            startActivity(new Intent(MyActivity.this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void toApp(View v){
        Intent i = new Intent(this,Map_Activity.class );
        startActivity(i);
    }
}
