package com.example.sadi.smartdoorapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    Button bUploadImage;
    EditText firstName;
    EditText lastName;

    EditText city;
    //DatePicker datePicker ;
    //SimpleDateFormat dateFormatter ;
   // Date d ;
   // String entered_dob ;



    //int day = datePicker.getDayOfMonth();
    //int month = datePicker.getMonth() + 1;
    //int year = datePicker.getYear();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        bUploadImage=(Button)findViewById(R.id.bUploadImage);
        firstName = (EditText)findViewById(R.id.editText_firstname);
        lastName = (EditText)findViewById(R.id.editText_lastname);
        //datePicker = (DatePicker) findViewById(R.id.textView_PickDate_registration);
        //DOB = (EditText)findViewById(R.id.editText_dob);
        city = (EditText)findViewById(R.id.editText_city);
      //  d = new Date(year, month, day);
       // dateFormatter = new SimpleDateFormat("MM-dd-yyyy");
        //entered_dob = dateFormatter.format(d);
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
     //   ButtonNext1();
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
    public void ButtonNext1(View view)
    {
        String first_name = firstName.getText().toString();
        String last_name = lastName.getText().toString();
        //String DOfB = DOB.getText().toString();
        String uCity = city.getText().toString();

        new Create_User().execute(first_name,last_name,uCity);
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

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment_Signup();
        newFragment.show(getSupportFragmentManager(), "datePicker");
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
            // String String_name = editText.getText().toString();
            // String Int_Part = editText2.getText().toString();
            String first_name = args[0];
            String last_name = args[1];
           // String DofB = args[2];
            String uCity = args[2];

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("editText_firstname", first_name));
            params.add(new BasicNameValuePair("editText_lastname", last_name));
           // params.add(new BasicNameValuePair("editText_DOB", DofB));
            params.add(new BasicNameValuePair("editText_city", uCity));


            JSONObject json = jsonParser.makeHttpRequest("http://192.168.1.101/user_create.php", "POST", params);

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
            Intent next1 = new Intent("com.example.sadi.smartdoorapp.activity_registration2");
            startActivity(next1);

        }
    }



}
