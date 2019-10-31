package com.cartmell.travis.tcartmelllab7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    AnalogClock clock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clock = findViewById(R.id.clock);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// This adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (clock.getMp() != null) {
            clock.playMe();
        }
        clock.setisStop();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        clock.set(pref.getString("clocks", "None"), pref.getBoolean("check_time_24", false), pref.getBoolean("check_partial_seconds", false));
    }

    @Override
    public void onStop() {
        clock.stopMe();
        //clock.setisStop();
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here
        int id = item.getItemId();
        if (id == R.id.action_about) {
            Toast.makeText(this, "Lab 7, Winter 2019, Travis Cartmell", Toast.LENGTH_SHORT).show();
            return true;
        }

        else if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(Observable o, Object arg) {
        TextView tv = (TextView) findViewById(R.id.time);
        tv.setText(((AnalogClock.AnalogClockObservable) o).get());
    }
}
