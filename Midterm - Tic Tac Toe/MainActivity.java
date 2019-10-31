package com.cartmell.travis.tcartmellmidterm;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener {

    ImageView topLeft;
    ImageView topMid;
    ImageView topRight;
    ImageView midLeft;
    ImageView midMid;
    ImageView midRight;
    ImageView botLeft;
    ImageView botMid;
    ImageView botRight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView X = findViewById(R.id.imageX);
        ImageView O = findViewById(R.id.imageO);
        X.setOnTouchListener(this);
        O.setOnTouchListener(this);
        O.setTag(R.drawable.o);
        X.setTag(R.drawable.x);

        topLeft = findViewById(R.id.imageBlank1);
        topMid = findViewById(R.id.imageBlank3);
        topRight = findViewById(R.id.imageBlank5);
        midLeft = findViewById(R.id.imageBlank6);
        midMid = findViewById(R.id.imageBlank8);
        midRight = findViewById(R.id.imageBlank10);
        botLeft = findViewById(R.id.imageBlank11);
        botMid = findViewById(R.id.imageBlank13);
        botRight = findViewById(R.id.imageBlank15);
        topLeft.setOnDragListener(this);
        topMid.setOnDragListener(this);
        topRight.setOnDragListener(this);
        midLeft.setOnDragListener(this);
        midMid.setOnDragListener(this);
        midRight.setOnDragListener(this);
        botLeft.setOnDragListener(this);
        botMid.setOnDragListener(this);
        botRight.setOnDragListener(this);

        if (savedInstanceState != null) {
            topLeft.setTag(savedInstanceState.getInt("topLeft"));
            topMid.setTag(savedInstanceState.getInt("topMid"));
            topRight.setTag(savedInstanceState.getInt("topRight"));
            midLeft.setTag(savedInstanceState.getInt("midLeft"));
            midMid.setTag(savedInstanceState.getInt("midMid"));
            midRight.setTag(savedInstanceState.getInt("midRight"));
            botLeft.setTag(savedInstanceState.getInt("botLeft"));
            botMid.setTag(savedInstanceState.getInt("botMid"));
            botRight.setTag(savedInstanceState.getInt("botRight"));

            topLeft.setImageResource(savedInstanceState.getInt("topLeft"));
            topMid.setImageResource(savedInstanceState.getInt("topMid"));
            topRight.setImageResource(savedInstanceState.getInt("topRight"));
            midLeft.setImageResource(savedInstanceState.getInt("midLeft"));
            midMid.setImageResource(savedInstanceState.getInt("midMid"));
            midRight.setImageResource(savedInstanceState.getInt("midRight"));
            botLeft.setImageResource(savedInstanceState.getInt("botLeft"));
            botMid.setImageResource(savedInstanceState.getInt("botMid"));
            botRight.setImageResource(savedInstanceState.getInt("botRight"));
        }
        else {
            topLeft.setTag(R.drawable.blank);
            topMid.setTag(R.drawable.blank);
            topRight.setTag(R.drawable.blank);
            midLeft.setTag(R.drawable.blank);
            midMid.setTag(R.drawable.blank);
            midRight.setTag(R.drawable.blank);
            botLeft.setTag(R.drawable.blank);
            botMid.setTag(R.drawable.blank);
            botRight.setTag(R.drawable.blank);
        }
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
            Toast.makeText(this,"Midterm, Winter 2019, Travis Cartmell", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(null,dragShadowBuilder,v,0);
            return true;
        }
        return false;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        if (event.getAction() == DragEvent.ACTION_DROP) {
            if ((int) v.getTag() != R.drawable.blank) {
                Toast.makeText(this,"Can't play in this cell", Toast.LENGTH_SHORT).show();
                return true;
            }

            View temp = (View) event.getLocalState();
            int id = (int) temp.getTag();

            ((ImageView) v).setImageResource(id);
            v.setTag(id);
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("topLeft", (int) topLeft.getTag());
        outState.putInt("topMid", (int) topMid.getTag());
        outState.putInt("topRight", (int) topRight.getTag());
        outState.putInt("midLeft", (int) midLeft.getTag());
        outState.putInt("midMid", (int) midMid.getTag());
        outState.putInt("midRight", (int) midRight.getTag());
        outState.putInt("botLeft", (int) botLeft.getTag());
        outState.putInt("botMid", (int) botMid.getTag());
        outState.putInt("botRight", (int) botRight.getTag());

        super.onSaveInstanceState(outState);
    }
}
