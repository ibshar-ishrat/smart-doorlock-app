package com.example.sadi.smartdoorapp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sadia Sami on 6/21/2016.
 */
public class Fragment_Tab_Activity extends Fragment implements AdapterView.OnItemClickListener
{

    View rootview;
    public static String IP_ADDRESS = Main_ScreenActivity.IP_ADDRESS;
    public static final String GET_IMAGE_URL="http://"+IP_ADDRESS+"/db_Activity_Tab.php";
    public static GetImages getImages;
    static String rspStr;
    public static final String BITMAP_ID = "BITMAP_ID";
    private ListView listView;
    public static String[] imageURLs;
    public static Bitmap[] bitmaps;
    public static final String JSON_ARRAY = "result";
    public static final String IMAGE_URL = "url";
    private String json;
    private JSONArray urls;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootview = inflater.inflate(R.layout.activity_listview, container, false);
        listView = (ListView) rootview.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getURLs();
        return rootview;
    }

    private void getURLs()
    {
        class GetURLs extends AsyncTask<String,String,String>
        {
            ProgressDialog loading;
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
               // loading = ProgressDialog.show(ImageListView.this,"Loading...","Please Wait...",true,true);
            }

            @Override
            protected String doInBackground(String... strings)
            {
                BufferedReader bufferedReader = null;
                try
                {
                    URL url = new URL(strings[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null)
                    {
                        sb.append(json + "\n");
                    }

                    rspStr = sb.toString();

                }
                catch(Exception e)
                {
                    Log.e("Buffer Error", "Error converting result " + e.toString());
                    return null;
                }
                return rspStr;
            }
            @Override
            protected void onPostExecute(String s)
            {
                s = rspStr;
                super.onPostExecute(s);
               // loading.dismiss();
                getImages = new GetImages(s);
                getImages();
            }

        }
        GetURLs gu = new GetURLs();
        gu.execute(GET_IMAGE_URL);
    }

    private void getImages()
    {
        class GetImages extends AsyncTask<Void,Void,Void>
        {
            ProgressDialog loading;
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                //loading = ProgressDialog.show(this,"Downloading images...","Please wait...",false,false);
            }

            @Override
            protected Void doInBackground(Void... voids)
            {
                try
                {
                    getImages.getAllImages();

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void v)
            {
                super.onPostExecute(v);
                //loading.dismiss();
                ActivityList customList = new ActivityList(getActivity(), com.example.sadi.smartdoorapp.GetImages.imageURLs,com.example.sadi.smartdoorapp.GetImages.bitmaps);
                listView.setAdapter(customList);
            }
        }
        GetImages getImages = new GetImages();
        getImages.execute();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

    }
}

