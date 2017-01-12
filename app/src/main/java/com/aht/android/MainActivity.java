package com.aht.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aht.android.rest.RestConnection;
import com.aht.android.rest.TestRestActivity;

import java.io.File;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA = "nix.Extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void testRest(View view) throws Exception {
        RestConnection rc = new RestConnection();
        rc.connect();
        rc.saveProjectStatus();

        if(new File(RestConnection.HOME_DIR + RestConnection.FS + "status.xml").exists())
        {
            Intent intent = new Intent(this, TestRestActivity.class);
            intent.putExtra(EXTRA,/*rc.loadProjectStatus().get(0).getCode()*/ "Test");
            startActivity(intent);
        }
        else
        {
            TextView textView = (TextView)findViewById(R.id.textView);
            textView.setText(R.string.failed);
        }
    }
}
