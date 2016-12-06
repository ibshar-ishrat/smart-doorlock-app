package com.example.sadi.smartdoorapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Sadia Sami on 6/21/2016.
 */
public class Fragment_Tab_Action extends Fragment
{
    private static ListView lv_Actions;

    ArrayList<String> Actions_Array = new ArrayList<String>();
    private static ArrayAdapter<String> adapter;

    public static String IP_ADDRESS = Main_ScreenActivity.IP_ADDRESS;

    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootview = inflater.inflate(R.layout.action_tab,container,false);

        lv_Actions = (ListView) rootview.findViewById(R.id.lv_action_tab);

        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, Actions_Array);

        GetDataJSON_Action g = new GetDataJSON_Action();
        g.execute();

        return rootview;
    }

    class GetDataJSON_Action extends AsyncTask<String, String, String> {

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
        protected String doInBackground(String... args)
        {
            String url = "http://" + IP_ADDRESS + "/db_Action_Tab.php";

            InputStream inputStream = null;
            String result = null;

            try {
                DefaultHttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url);

                // Depends on your web service
                //httppost.setHeader("Content-type", "application/json");

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

                JSONObject c;

                lv_Actions.setAdapter(null);

                for (int i = 0; i < retrievedArray.length(); i++) {
                    c = retrievedArray.getJSONObject(i);

                    Actions_Array.add(c.getString("Action"));
                }

                lv_Actions.setAdapter(adapter);

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            //pDialog.dismiss();
        }
    }
}
