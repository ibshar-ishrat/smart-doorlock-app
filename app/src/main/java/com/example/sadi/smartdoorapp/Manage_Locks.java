package com.example.sadi.smartdoorapp;

/**
 * Created by Sami Ullah on 3/16/2016.
 */
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

public class Manage_Locks extends FragmentActivity {


    // Fragment TabHost as mTabHost
    private FragmentTabHost mTabHost;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_lock);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("Insert").setIndicator("Insert"),
                Fragment_Tab_Insert.class, null);

        mTabHost.addTab(mTabHost.newTabSpec("Delete").setIndicator("Delete"),
                Fragment_Tab_Delete.class, null);
    }
}
