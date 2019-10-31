package com.cartmell.travis.tcartmelllab2;

import android.content.res.TypedArray;
import android.provider.ContactsContract;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener {
    DrawerLayout dl;
    ListView lv;
    ActionBarDrawerToggle abdt;
    TypedArray typedImgs;
    int[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        lv = (ListView) findViewById(R.id.nav_view);
        final String close = getTitle().toString();
        final String shown = "Select a page";
        abdt = new ActionBarDrawerToggle(this, dl, R.string.str_open, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setTitle(R.string.str_open);
                invalidateOptionsMenu();
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                setTitle(R.string.app_name);
                invalidateOptionsMenu();
            }
        };

        dl.addDrawerListener(abdt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true) ;
        getSupportActionBar().setHomeButtonEnabled(true);
        abdt.syncState() ;

        ListView navView = (ListView) findViewById(R.id.nav_view);

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, R.layout.nav_list_row, R.id.textName, getResources().getStringArray(R.array.names));

        navView.setAdapter(listAdapter);

        typedImgs = getResources().obtainTypedArray(R.array.image_ids);
        images = new int[typedImgs.length()] ;
        for (int i=0 ; i<typedImgs.length() ; i++) {
            images[i] = typedImgs.getResourceId(i, 0) ;
        }

        if (savedInstanceState != null) {
            ImageView image = (ImageView) findViewById(R.id.imageCindy);
            image.setTag(savedInstanceState.getInt("key"));
            image.setImageResource(images[savedInstanceState.getInt("key")]);
        }

        //setOnItemClickListener(this);
        /* this works, and i understand why, but i don't understand why my implementation
        in a separate method doesn't work.
         */
        navView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView image = (ImageView) findViewById(R.id.imageCindy);
                image.setImageResource(images[position]);
                image.setTag(position);
            }
        });

    }

    private void setOnItemClickListener(MainActivity mainActivity) {
        ImageView image = (ImageView) findViewById(R.id.imageCindy);
        ListView navView = (ListView) findViewById(R.id.nav_view);
        //image.setImageResource(images[1]);
        onItemClick(navView, image, navView.getSelectedItemPosition(), navView.getSelectedItemId());
    }

    public void onItemClick(AdapterView<?> av, View v, int position, long id) {
        ImageView image = (ImageView) v;
        image.setImageResource(images[position]);
        Toast.makeText(this, String.valueOf(position), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        ImageView iv = (ImageView) findViewById(R.id.imageCindy);
        int id;

        if (iv.getTag() == null)
            id = 0;
        else
            id = (int) iv.getTag();

        outState.putInt("key", id);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (dl.isDrawerOpen(lv)) {
            menu.findItem(R.id.action_about).setVisible(false);
            return true;
        }

        return super.onPrepareOptionsMenu(menu);
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

        if (abdt.onOptionsItemSelected(item))
            return true ;

        if (id == R.id.action_about) {
            Toast.makeText(this, "Lab 2, Winter 2019, Travis Cartmell", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
