package com.example.sadi.smartdoorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

public class sidePanel extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_panel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        TabHost tabhost=(TabHost) findViewById(R.id.tabHost);
        tabhost.setup();
        TabHost.TabSpec tabspec= tabhost.newTabSpec("Activity");
        tabspec.setContent(R.id.Activity);
        tabspec.setIndicator("Activity");
        tabhost.addTab(tabspec);
        tabspec= tabhost.newTabSpec("Action");
        tabspec.setContent(R.id.Action);
        tabspec.setIndicator("Action");
        tabhost.addTab(tabspec);
        tabspec= tabhost.newTabSpec("Door Settings");
        tabspec.setContent(R.id.DoorLog);
        tabspec.setIndicator("Door Settings");
        tabhost.addTab(tabspec);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

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
        if (id == R.id.nav_exit) {


        }
        if (id == R.id.nav_profile) {

            Intent intent = new Intent(this, User_Registration.class);
            startActivity(intent);


        }
        if (id == R.id.nav_security) {

            Intent intent = new Intent(this, User_Registration3.class);
            startActivity(intent);

        }
        if (id == R.id.nav_use_app) {


        }
        if (id == R.id.nav_use_device) {


        }
       if (id == R.id.nav_account) {
           //fragmentManager.beginTransaction().replace(R.id.content_frame,new Account_Settings()).commit();
           Intent intent = new Intent(this, User_Registration2.class);
           startActivity(intent);

        }
        if (id == R.id.nav_manage) {

            Intent intent = new Intent(this, Manage_Locks.class);
            startActivity(intent);

        }
       else if (id == R.id.nav_logout) {

            finish();
        }
        //else if (id == R.id.nav_send) {

//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
