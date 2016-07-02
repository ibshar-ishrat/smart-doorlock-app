package com.example.sadi.smartdoorapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Sadia Sami on 7/1/2016.
 */
public class Profile_Settings extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    public static ImageView imageToUpload;
    Button bProfileUploadImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_settings);
        bProfileUploadImage=(Button)findViewById(R.id.bProfileUploadImage);

        UploadImage();
    }


    public void UploadImage() {
        imageToUpload = (ImageView) findViewById(R.id.imageToUploadprofile);
        imageToUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
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

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK && data!=null){
            Uri selectedImage = data.getData();
            imageToUpload.setImageURI(selectedImage);

        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
