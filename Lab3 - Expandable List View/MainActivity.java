package com.cartmell.travis.tcartmelllab3;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements ExpandableListView.OnChildClickListener{
    ArrayList<Manufacturer> manList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            manList = (ArrayList<Manufacturer>) savedInstanceState.getSerializable("key");
        }

        else {
            try {
                parseFile("make-model.txt");
            } catch (IOException e) {
                Toast.makeText(this, "Parse was unsuccessful", Toast.LENGTH_SHORT).show();
            }
        }

        Adapter adapter = new Adapter(this, manList);
        ExpandableListView expand = findViewById(R.id.list_expandable);
        expand.setAdapter(adapter);

        expand.setOnChildClickListener(this);
    }

    private boolean parseFile(String filename) throws IOException {
        AssetManager manager = getResources().getAssets();
        Scanner sc;
        String input;

            sc = new Scanner(manager.open(filename));


        while (sc.hasNext()) {
            input = sc.nextLine();
            String[] data = input.split(",");
            ArrayList<String> models = new ArrayList<>();

            for (int i = 1; i < data.length; i++) {
                models.add(data[i]);
                Log.e("***DEBUG***", data[i]);
            }
            manList.add(new Manufacturer(data[0], models));
        }
        return true;
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
            Toast.makeText(this, "Lab 3, Winter 2019, Travis Cartmell", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("key", manList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Toast.makeText(this, "Make: " + manList.get(groupPosition).getManufacturerName() + " Model: " + manList.get(groupPosition).getModelName(childPosition), Toast.LENGTH_SHORT).show();
        return true;
    }
}
