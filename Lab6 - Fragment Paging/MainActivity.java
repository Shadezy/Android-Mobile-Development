package com.cartmell.travis.tcartmelllab6;

import android.app.Activity;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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

public class MainActivity extends AppCompatActivity implements ExpandableListView.OnChildClickListener {
    ArrayList<Manufacturer> manList = new ArrayList<>();
    SectionPagerAdapter adapter;
    ViewPager vp;
    Model selectedModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            manList = (ArrayList<Manufacturer>) savedInstanceState.getSerializable("key");
            selectedModel = (Model) savedInstanceState.getSerializable("selectedModel");
        }

        else {
            try {
                parseFile("make-model.txt");
            } catch (IOException e) {
                Toast.makeText(this, "Parse was unsuccessful", Toast.LENGTH_SHORT).show();
            }

            selectedModel = manList.get(0).muscle_car_models.get(0);
        }

        adapter = new SectionPagerAdapter(getSupportFragmentManager());
        vp = findViewById(R.id.view_pager);
        vp.setAdapter(adapter);
        /*Adapter adapter = new Adapter(this, manList);
        ExpandableListView expand = findViewById(R.id.list_expandable);
        expand.setAdapter(adapter);

        expand.setOnChildClickListener(this);*/
    }

    public Model getSelectedModel() {
        return selectedModel;
    }

    public class SectionPagerAdapter extends FragmentPagerAdapter {
        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if (i == 0)
                return ListFragment.newInstance(manList);
            return new DetailFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }

    public void changePage(Model model) {
        selectedModel = model;
        vp.setCurrentItem(1);
        adapter.notifyDataSetChanged();
    }

    private boolean parseFile(String filename) throws IOException {
        AssetManager manager = getResources().getAssets();
        Scanner sc;
        String input;

            sc = new Scanner(manager.open(filename));


        while (sc.hasNext()) {
            input = sc.nextLine();
            String[] data = input.split(",");
            ArrayList<Model> models = new ArrayList<>();

            for (int i = 1; i < data.length; i = i+3) {
                int picid = getResources().getIdentifier( data[i].toLowerCase().replaceAll(" ",""), "drawable", getPackageName() );
                models.add(new Model(data[i], data[i+1], data[i+2], picid));
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
            Toast.makeText(this, "Lab 6, Winter 2019, Travis Cartmell", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("key", manList);
        outState.putSerializable("selectedModel", selectedModel);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Toast.makeText(this, "Make: " + manList.get(groupPosition).getManufacturerName() + " Model: " + manList.get(groupPosition).getModelName(childPosition), Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (vp.getCurrentItem() == 1)
            vp.setCurrentItem(0);
        else
            super.onBackPressed();
    }
}
