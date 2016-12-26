package com.example.sadi.smartdoorapp;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;

/**
 * Created by Ibshar on 26/10/2016.
 */

public class Door_Info extends Activity
{
    public static String IP_ADDRESS = Main_ScreenActivity.IP_ADDRESS;

    private static String Door_Name = "";
    private static String Door_Desc = "";
    private static String Door_Status = "";

    private static TextView tv_Door_Name;
    private static TextView tv_Door_Desc;
    private static ListView lv_Features_Name;

    ArrayList<String> Features_Name_Array = new ArrayList<String>();

    private static ArrayAdapter<String> adapter;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.door_info);

        tv_Door_Name = (TextView) findViewById(R.id.tv_Door_Name);
        tv_Door_Desc = (TextView) findViewById(R.id.tv_Door_Desc);

        lv_Features_Name = (ListView) findViewById(R.id.lv_Features_Name);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, Features_Name_Array);

        GetDataJSON_Door_Info g = new GetDataJSON_Door_Info();
        g.execute();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Door_Info Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    class GetDataJSON_Door_Info extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            /*super.onPreExecute();
            pDialog = new ProgressDialog(User_Registration2.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/
        }

        @Override
        protected String doInBackground(String... args) {
            String url = "http://" + IP_ADDRESS + "/db_Door_Info.php?MAC=" + Utils.getMACAddress("wlan0");

            DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
            HttpGet httppost = new HttpGet(url);

            // Depends on your web service
            httppost.setHeader("Content-type", "application/json");

            InputStream inputStream = null;
            String result = null;

            try {
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();

                inputStream = entity.getContent();
                // json is UTF-8 by default
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                result = sb.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) inputStream.close();
                } catch (Exception squish) {
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONArray retrievedArray = null;

            try {
                JSONObject jsonObj = new JSONObject(result);
                retrievedArray = jsonObj.getJSONArray("result");

                JSONObject c = retrievedArray.getJSONObject(retrievedArray.length() - 3);
                Door_Status = c.getString("Door Status");

                c = retrievedArray.getJSONObject(retrievedArray.length() - 2);
                Door_Name = c.getString("Door_Name");

                tv_Door_Name.setText(Door_Name);

                c = retrievedArray.getJSONObject(retrievedArray.length() - 1);
                Door_Desc = c.getString("Door Description");

                tv_Door_Desc.setText(Door_Desc);


                for (int i = 0; i < retrievedArray.length() - 3; i++) {
                    c = retrievedArray.getJSONObject(i);

                    Features_Name_Array.add(c.getString("Feature Name"));
                }

                lv_Features_Name.setAdapter(adapter);

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            //pDialog.dismiss();
        }
    }
}