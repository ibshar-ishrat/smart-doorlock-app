package com.example.sadi.smartdoorapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.content.SharedPreferences;

/**
 * Created by Sami Ullah on 3/16/2016.
 */
public class User_Registration extends Main_ScreenActivity
{
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
                switch (v.getId())
                {
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
        String sFirstName = firstName.getText().toString().trim();
        String sLastName = lastName.getText().toString().trim();
        String sCountry = country.getText().toString().trim();
        String sCity = city.getText().toString().trim();

        //********* VALIDATION CODE ***********//
        if(sFirstName.length() == 0 || sLastName.length() == 0 || sCity.length() == 0 || sCountry.length() == 0 || sFirstName.length() > 35 || sLastName.length() > 35)
        {
            if( sFirstName.length() == 0 )
                firstName.setError( "First name is required!" );

            else if( sFirstName.length() > 35 )
                firstName.setError( "First name is too long!" );

            if( sLastName.length() == 0 )
                lastName.setError( "Last name is required!" );

            else if( sLastName.length() > 35 )
                lastName.setError( "Last name is too long!" );

            if( sCountry.length() == 0 )
                country.setError( "Country is required!" );

            if( sCity.length() == 0 )
                city.setError( "City is required!" );
        }
        else {
            //Here define all your shared preferences code with key and value
            //create object of SharedPreferences
            SharedPreferences reg_Pref = getSharedPreferences("reg_pref", Context.MODE_PRIVATE);

            SharedPreferences.Editor edit = reg_Pref.edit(); //Now get editor

            //takes values from edit text
            edit.putString("F_Name", sFirstName);
            edit.putString("L_Name", sLastName);
            edit.putString("Country", sCountry);
            edit.putString("City", sCity);

            edit.commit(); //commits your edit

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
}