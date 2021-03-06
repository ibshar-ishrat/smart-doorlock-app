package com.example.sadi.smartdoorapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class sidePanel extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentTabHost mTabHost;

    /**
     * Items entered by the user is stored in this ArrayList variable
     */
    ArrayList<String> list = new ArrayList<String>();

    /**
     * Declaring an ArrayAdapter to set items to ListView
     */
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_panel);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("Activity").setIndicator("Activity"),
                Fragment_Tab_Activity.class, null);

        mTabHost.addTab(mTabHost.newTabSpec("Action").setIndicator("Action"),
                Fragment_Tab_Action.class, null);

        mTabHost.addTab(mTabHost.newTabSpec("Door status").setIndicator("Door status"),
                Fragment_Tab_Door_Status.class, null);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_side_panel_drawer, menu);
        return true;
    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // flate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.side_panel, menu);

        // Getting a reference to Spinner object of the resource activity_main
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        SharedPreferences bb = getSharedPreferences("reg_pref", 0);
        String m = bb.getString("Door_Name", "");

        //Intent i = getIntent();
        //String doorname = i.getStringExtra("text_label");
        list.add(m);

        // Defining the ArrayAdapter to set items to Spinner Widget
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        // Setting the adapter to the ListView
        spinner.setAdapter(adapter);
        // Adding radio buttons for the spinner items
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_home) {


        }
        if (id == R.id.nav_app_settings) {


        }

        if (id == R.id.nav_profile) {

            Intent intent = new Intent(this, Profile_Settings.class);
            startActivity(intent);


        }
        if (id == R.id.nav_security) {

            Intent intent = new Intent(this, Security_Settings.class);
            startActivity(intent);

        }
        if (id == R.id.nav_use_app) {


        }
        if (id == R.id.nav_use_device) {


        }
        if (id == R.id.nav_account) {
            //fragmentManager.beginTransaction().replace(R.id.content_frame,new Account_Settings()).commit();
            Intent intent = new Intent(this, Account_Settings.class);
            startActivity(intent);

        }
        if (id == R.id.nav_manage) {

            Intent intent = new Intent(this, Manage_Doors.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {

            finish();
        }
        //else if (id == R.id.nav_send) {

//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
