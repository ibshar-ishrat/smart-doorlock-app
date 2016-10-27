package com.example.sadi.smartdoorapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ibshar on 26/10/2016.
 */

public class DoorInfo3 extends Activity {


    private ArrayList<Integer> id = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.door_info);

        id = (ArrayList<Integer>) getIntent().getSerializableExtra("key");

        TextView tv = (TextView) findViewById(R.id.Door_info);
        tv.setText( String.valueOf(id.get(2)));
    }
}
