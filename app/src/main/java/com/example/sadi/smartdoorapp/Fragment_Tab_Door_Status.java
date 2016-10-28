package com.example.sadi.smartdoorapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sadia Sami on 6/21/2016.
 */
public class Fragment_Tab_Door_Status extends Fragment {
    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.door_status,container,false);

          /********************************/
         /*    Define all the buttons    */
        /********************************/


        Switch led1 = (Switch) rootview.findViewById(R.id.Led1);


        /*******************************************************/
         /*  Set an onclick/onchange listener for every button  */
        /*******************************************************/

        led1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    /* Switch is led 1 */
                    new Background_get().execute("led1=1");
                } else {
                    new Background_get().execute("led1=0");
                }
            }
        });


        return rootview;


    }


        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            //   getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            //   if (id == R.id.action_settings) {
            //     return true;
            //}

            return super.onOptionsItemSelected(item);
        }

        /*****************************************************/
       /*  This is a background process for connecting      */
      /*   to the arduino server and sending               */
     /*    the GET request withe the added data           */
        /*****************************************************/



        private class Background_get extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                try {
                /* Change the IP to the IP you set in the arduino sketch */
                    URL url = new URL("http://192.168.1.102/?" + params[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder result = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null)
                        result.append(inputLine).append("\n");

                    in.close();
                    connection.disconnect();
                    return result.toString();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
    }




