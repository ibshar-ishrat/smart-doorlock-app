package com.example.sadi.smartdoorapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

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

    ArrayList<String> Features_Name_Array = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.door_info);

        GetDataJSON_Door_Info g = new GetDataJSON_Door_Info();
        g.execute();
    }

    class GetDataJSON_Door_Info extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute()
        {
            /*super.onPreExecute();
            pDialog = new ProgressDialog(User_Registration2.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/
        }
        @Override
        protected String doInBackground(String... args)
        {
            String url = "http://"+IP_ADDRESS+"/db_Door_Info.php?MAC="+Utils.getMACAddress("wlan0");

            DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
            HttpGet httppost = new HttpGet(url);

            // Depends on your web service
            httppost.setHeader("Content-type", "application/json");

            InputStream inputStream = null;
            String result = null;

            try
            {
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();

                inputStream = entity.getContent();
                // json is UTF-8 by default
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;

                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }

                result = sb.toString().trim();
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }

            finally
            {
                try
                {
                    if (inputStream != null) inputStream.close();
                }
                catch (Exception squish)
                {
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result)
        {
            JSONArray retrievedArray = null;

            try
            {
                JSONObject jsonObj = new JSONObject(result);
                retrievedArray = jsonObj.getJSONArray("result");

                JSONObject c = retrievedArray.getJSONObject( retrievedArray.length() - 3 );
                Door_Status = c.getString("Door Status");

                c = retrievedArray.getJSONObject( retrievedArray.length() - 2 );
                Door_Name = c.getString("Door_Name");

                c = retrievedArray.getJSONObject( retrievedArray.length() - 1 );
                Door_Desc = c.getString("Door Description");


                for(int i=0;i<retrievedArray.length()-3;i++)
                {
                    c = retrievedArray.getJSONObject(i);

                    Features_Name_Array.add(c.getString("Feature Name"));
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            //pDialog.dismiss();
        }
    }
}