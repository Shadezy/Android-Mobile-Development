package com.cartmell.travis.tcartmelllab1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends TracerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if (intent!=null) {
            String action = intent.getAction();
            String type = intent.getType();
            TextView tv = (TextView)findViewById(R.id.text_results);

            if (action.equals(Intent.ACTION_SEND) && type.equals("text/plain")) {
                tv.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
            }

        }
    }

    public void surveyHandler(View v) {
        EditText edit = (EditText) findViewById(R.id.edit_name);
        if (edit.getText().toString().equals("")) {
            Toast.makeText(this,
                    "Please enter a name",
                    Toast.LENGTH_SHORT)
                    .show();
        }
        else {
            String name = edit.getText().toString();
            Toast.makeText(this,
                    name,
                    Toast.LENGTH_SHORT)
                    .show();

            Intent survey = new Intent(this, SurveyActivity.class);
            survey.putExtra("name", name);
            startActivityForResult(survey, 1);
        }

        /*Toast.makeText(this,
                "You want to take a survey?",
                Toast.LENGTH_SHORT)
                .show();*/
    }

    public void websiteHandler(View v) {
    Uri uri = Uri.parse("https://sites.google.com/site/pschimpf99/");
    Intent website = new Intent(Intent.ACTION_VIEW,  uri);
    startActivity(website);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        int age;
        TextView result = (TextView) findViewById(R.id.text_results);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            age = data.getIntExtra("age", 0);

            if (age < 40) {
                result.setText("You're under 40, so you're trustworthy");
            }
            else {
                result.setText("You're NOT under 40, so you're NOT trustworthy");
            }
        }
        else {
            Toast.makeText(this,
                    "bad data",
                    Toast.LENGTH_SHORT)
                    .show();
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
            Toast.makeText(this,
                    "Lab 1, Winter 2019, Travis Cartmell",
                    Toast.LENGTH_SHORT)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
