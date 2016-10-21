package com.example.sadi.smartdoorapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sadia Sami on 10/18/2016.
 */
public class Manage_Doors extends ActionBarActivity {
    String myJSON; //string where we will store the JSON string from server.
    private static final String TAG_RESULTS="result"; //final String TAGS for getting the values from JSON String
    private static final String TAG_NAME = "name";

    JSONArray Doors = null; // storing the JSON Array and
    //An array list for creating a list from JSON

    private static final String urlString = "http://192.168.0.104/smart_door_lock/db_fetch.php";
    ArrayList<HashMap<String, String>> doorList;

    ListView list; //ListView for displaying the list in the Activity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_door_settings);
        //initialized our list object and personList.
        list = (ListView) findViewById(R.id.listView);
        doorList = new ArrayList<HashMap<String,String>>();
        //Finally we called the getData() method which will fetch the JSON string from the server.
        getData();
    }

    // getData is executing an AsyncTask. In the AyncTask class the doInBackgroundMethod will return the JSON String.
    //In onPostExecute method we will store that String to myJSON.
    // Then we will call a new showList() method to show the JSON string in the ListView after parsing.

    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                URL url = null;
                URLConnection conn = null;
                String result = null;
                try {
                    url = new URL(urlString);
                    conn = url.openConnection();
                    conn.setRequestProperty("method", "GET");
                    conn.setRequestProperty("Content-type", "application/json");
                    InputStream is = conn.getInputStream();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return result;
            }


            @Override
            protected void onPostExecute(String result){
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }


    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            Doors = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<Doors.length();i++){
                JSONObject c = Doors.getJSONObject(i);
                String name = c.getString(TAG_NAME);


                HashMap<String,String> device_name = new HashMap<String,String>();


                device_name.put(TAG_NAME,name);


                doorList.add(device_name);
            }

            ListAdapter adapter = new SimpleAdapter(
                    Manage_Doors.this, doorList, R.layout.list_door_name,
                    new String[]{TAG_NAME, },
                    new int[]{R.id.name,}
            );

            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
