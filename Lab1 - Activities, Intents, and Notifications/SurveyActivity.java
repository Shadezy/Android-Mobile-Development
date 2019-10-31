package com.cartmell.travis.tcartmelllab1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        String name = getIntent().getExtras().getString("name");
        TextView dest = (TextView) findViewById(R.id.text_hello);
        dest.append(" " + name);
    }

    public void submitHandler(View v) {
        EditText edit = (EditText) findViewById(R.id.edit_age);
        if (edit.getText().toString().equals("")) {
            Toast.makeText(this,
                    "Please enter an age",
                    Toast.LENGTH_SHORT)
                    .show();
        }
        else {
            int age = Integer.parseInt(edit.getText().toString());

            Intent result = new Intent();
            result.putExtra("age", age);
            setResult(Activity.RESULT_OK, result);
            finish();
        }
    }
}
