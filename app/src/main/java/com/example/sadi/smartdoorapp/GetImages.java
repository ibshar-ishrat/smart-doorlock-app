package com.example.sadi.smartdoorapp;

/**
 * Created by Ibshar on 09/11/2016.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.sadi.smartdoorapp.Main_ScreenActivity.IP_ADDRESS;

/**
 * Created by Ibshar Ishrat on 26/10/2016.
 */

public class GetImages
{
    public static String[] imageURLs;
    public static Bitmap[] bitmaps;

    public static final String JSON_ARRAY = "result";
    public static final String IMAGE_URL = "Visitor_Image";
    private String json;
    private JSONArray urls;


    public GetImages(String json)
    {
        this.json = json;
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            urls = jsonObject.getJSONArray(JSON_ARRAY);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void getAllImages() throws JSONException
    {
        bitmaps = new Bitmap[urls.length()];

        imageURLs = new String[urls.length()];

        for (int i = 0; i < urls.length(); i++)
        {
            imageURLs[i] = urls.getJSONObject(i).getString(IMAGE_URL);
            URL url = null;
            Bitmap image = null;
            try
            {
                url = new URL("http://"+IP_ADDRESS+imageURLs[i]);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;

                image = BitmapFactory.decodeStream(url.openConnection().getInputStream(),null,options);
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            bitmaps[i]=image;
        }
    }
}