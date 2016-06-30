package com.example.sadi.smartdoorapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


/**
 * Created by Sami Ullah on 3/16/2016.
 */
public class User_Registration extends Main_ScreenActivity
{
    private static Button button_next1;
    private static final int RESULT_LOAD_IMAGE = 1;
    public static ImageView imageToUpload;
    Button bUploadImage;
    public static EditText firstName;
    public static EditText lastName;
    public static EditText DOB;
    public static EditText city;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        bUploadImage=(Button)findViewById(R.id.bUploadImage);
        firstName = (EditText)findViewById(R.id.editText_firstname);
        lastName = (EditText)findViewById(R.id.editText_lastname);
        DOB = (EditText)findViewById(R.id.editText_dob);
        city = (EditText)findViewById(R.id.editText_city);
     /*   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        UploadImage();
        ButtonNext1();
    }

    public void UploadImage()
    {
        imageToUpload=(ImageView)findViewById(R.id.imageToUpload);
        imageToUpload.setOnClickListener(new View.OnClickListener() {
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
    public void ButtonNext1()
    {
        button_next1=(Button)findViewById(R.id.button_next1);
        button_next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent next1 = new Intent("com.example.sadi.smartdoorapp.activity_registration2");
                        startActivity(next1);

                }



    });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK && data!=null){
            Uri selectedImage = data.getData();
            imageToUpload.setImageURI(selectedImage);

        }
    }

}
