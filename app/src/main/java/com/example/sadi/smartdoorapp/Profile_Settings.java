package com.example.sadi.smartdoorapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Sadia Sami on 7/1/2016.
 */
public class Profile_Settings extends AppCompatActivity
{
    private static final int RESULT_LOAD_IMAGE = 1;
    public static ImageView imageToUpload;
    Button bProfileUploadImage;

    public static EditText firstName;
    public static EditText lastName;
    public static EditText country;
    public static EditText city;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_settings);

        bProfileUploadImage=(Button)findViewById(R.id.bProfileUploadImage);

        firstName = (EditText)findViewById(R.id.editText_pfirstname);
        lastName = (EditText)findViewById(R.id.editText_plastname);
        city = (EditText)findViewById(R.id.editText_pcity);
        country = (EditText)findViewById(R.id.editText_pcountry);

        UploadImage();
    }

    public void UploadImage()
    {
        imageToUpload = (ImageView) findViewById(R.id.imageToUploadprofile);

        imageToUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId())
                {
                    case R.id.imageToUploadprofile:
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                        break;

                    case R.id.bUploadImage:
                        break;
                }
            }
        });
    }

    public void SaveChanges(View view)
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
            //********** NOTE: push changes to pi *************//

            Toast.makeText(this, "Changes saved successfully!" , Toast.LENGTH_SHORT).show();
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