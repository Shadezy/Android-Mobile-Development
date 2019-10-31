package com.cartmell.travis.tcartmelllab8;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnLongClickListener {

    private GoogleMap mMap;
    private static final int PERMISSION_REQUEST = 1;
    int mark = 0;
    float zoom = 11;
    ArrayList<MarkerOptions> markerList;
    ArrayList<Marker> markers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        markerList = new ArrayList<MarkerOptions>();
        markers = new ArrayList<Marker>();

        Button btn_mark = findViewById(R.id.btn_mark);
        btn_mark.setOnLongClickListener(this);

        if (savedInstanceState != null) {
            markerList = (ArrayList<MarkerOptions>) savedInstanceState.getSerializable("markerList");
            zoom = savedInstanceState.getFloat("zoom");
            mark = savedInstanceState.getInt("mark");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        initMap();
    }

    private void initMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST);
            return;
        }
        for (MarkerOptions mo : markerList)
            markers.add(mMap.addMarker(mo));

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Location location = mMap.getMyLocation();//getLocation() ;
                if (location!=null) {
                    //Toast.makeText(getApplicationContext(), String.valueOf(location), Toast.LENGTH_LONG).show();
                    moveToLocation(location) ;
                }
                else
                    Toast.makeText(getApplicationContext(),
                            "No Location, try the zoom to button", Toast.LENGTH_SHORT).show();
            }
        };

        Handler handler = new Handler();
        handler.postDelayed(runnable, 2000);

        mMap.setMyLocationEnabled(true);
        UiSettings settings = mMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
    }

    private void moveToLocation(Location location) {
        //CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 11);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), zoom));
    }
    @Override
    public void onRequestPermissionsResult(int rqst, String perms[], int[] res){
        if (rqst == PERMISSION_REQUEST) {
            // if the request is cancelled, the result arrays are empty.
            if (res.length>0 && res[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted! We can now init the map
                initMap() ;
            } else {
                Toast.makeText(this, "This app is useless without loc permissions", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void changeType(View v) {
        if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL)
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        else if (mMap.getMapType() == GoogleMap.MAP_TYPE_SATELLITE)
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        else
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void addMarker(View v) {
        Location location = mMap.getMyLocation();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions mo = new MarkerOptions();
        mo.position(latLng);
        mo.title("Mark " + String.valueOf(mark++));
        markerList.add(mo);
        markers.add(mMap.addMarker(mo));
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("markerList",markerList);
        outState.putFloat("zoom",mMap.getCameraPosition().zoom);
        outState.putInt("mark", mark);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// This adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here
        int id = item.getItemId();
        if (id == R.id.action_about) {
            Toast.makeText(this, "Lab 8, Winter 2019, Travis Cartmell", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onAboutClick(View v) {
        Toast.makeText(this, "Lab 8, Winter 2019, Travis Cartmell", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View v) {
        //Toast.makeText(this, "I made it!", Toast.LENGTH_SHORT).show();
        if (markers.size() == 0)
            return true;
        markers.remove(markers.size()-1).remove();
        markerList.remove(markerList.size()-1);
        mark--;
        return true;
    }
}
