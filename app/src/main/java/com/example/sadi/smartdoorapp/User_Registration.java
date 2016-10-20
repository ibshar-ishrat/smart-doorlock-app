package com.example.sadi.smartdoorapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sami Ullah on 3/16/2016.
 */
public class User_Registration extends Main_ScreenActivity
{
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    private static Button button_next1;

    private static final int RESULT_LOAD_IMAGE = 1;
    public static ImageView imageToUpload;
    public static Button bUploadImage;

    public static EditText firstName;
    public static EditText lastName;
    public static EditText country;
    public static EditText city;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        bUploadImage=(Button)findViewById(R.id.bUploadImage);
        firstName = (EditText)findViewById(R.id.editText_firstname);
        lastName = (EditText)findViewById(R.id.editText_lastname);
        city = (EditText)findViewById(R.id.editText_city);
        country = (EditText)findViewById(R.id.editText_country);

     /*   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        UploadImage();
    }

    public void UploadImage()
    {
        imageToUpload=(ImageView)findViewById(R.id.imageToUpload);

        imageToUpload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.imageToUpload:
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
                        break;
                    case R.id.bUploadImage:
                        break;
                }
            }
        });
    }

    public void ButtonNext1(View view)
    {
        String first_name = firstName.getText().toString().trim();
        String last_name = lastName.getText().toString().trim();
        String uCountry = country.getText().toString().trim();
        String uCity = city.getText().toString().trim();

        //********* VALIDATION CODE ***********//
        if(first_name.length() == 0 || last_name.length() == 0 || uCity.length() == 0 || uCountry.length() == 0 || first_name.length() > 35 || last_name.length() > 35)
        {
            if( first_name.length() == 0 )
                firstName.setError( "First name is required!" );

            else if( first_name.length() > 35 )
                firstName.setError( "First name is too long!" );

            if( last_name.length() == 0 )
                lastName.setError( "Last name is required!" );

            else if( last_name.length() > 35 )
                lastName.setError( "Last name is too long!" );

            if( uCountry.length() == 0 )
                country.setError( "Country is required!" );

            if( uCity.length() == 0 )
                city.setError( "City is required!" );
        }

        else
        {
            /*CHECK FOR SUCCESSFUL INTERNET CONNECTIVITY ON PHONE THEN GO FOR REGISTRATION*/
            ConnectivityManager connMgr = (ConnectivityManager)
            getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected())
            {
                Toast.makeText(User_Registration.this,"No active internet connection. Please try again later", Toast.LENGTH_SHORT).show();
            }
            else
            {
                new Create_User().execute(first_name, last_name, uCity);
            }
            //************** VALIDATION SUCCESSFUL SO GO TO NEXT PAGE *****************//

            Intent next1 = new Intent("com.example.sadi.smartdoorapp.activity_registration2");
            startActivity(next1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK && data!=null)
        {
            Uri selectedImage = data.getData();
            imageToUpload.setImageURI(selectedImage);
        }
    }

    class Create_User extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(User_Registration.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... args)
        {
            String first_name = args[0];
            String last_name = args[1];
            String uCity = args[2];

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("editText_firstname", first_name));
            params.add(new BasicNameValuePair("editText_lastname", last_name));
            params.add(new BasicNameValuePair("editText_city", uCity));



            JSONObject json = jsonParser.makeHttpRequest("http://192.168.8.102/db_create.php", "POST", params);

            try
            {
                int success = json.getInt("success");

                if (success == 1)
                {
                    finish();
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url)
        {
            pDialog.dismiss();
           // Intent next1 = new Intent("com.example.sadi.smartdoorapp.activity_registration2");
           // startActivity(next1);
        }
    }
}