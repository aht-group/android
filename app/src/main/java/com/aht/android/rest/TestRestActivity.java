package com.aht.android.rest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.aht.android.MainActivity;
import com.aht.android.R;

public class TestRestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_rest);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA);
        TextView textView = (TextView)findViewById(R.id.projectStatus);
        textView.setText(message);
    }

}
