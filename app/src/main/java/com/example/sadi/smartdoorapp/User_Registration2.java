package com.example.sadi.smartdoorapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import android.content.SharedPreferences;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Sami Ullah on 3/16/2016.
 */
public class User_Registration2 extends Main_ScreenActivity {
    private ProgressDialog pDialog;

    public static EditText email;
    public static EditText userName;
    public static EditText altEmail;
    public static EditText password;
    public static EditText confirmPassword;

    public static String email_out;
    public static String alt_email_out;
    public static String username_out;

    public static String sEmail;
    public static String sUserName;
    public static String sAltEmail;
    public static String sPassword;
    public static String sConfirmPassword;

    public static String passwordPattern;
    public static String userNamePattern;
    public static String emailPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);

        email = (EditText) findViewById(R.id.editText_email);
        altEmail = (EditText) findViewById(R.id.editText_AltEmail);
        userName = (EditText) findViewById(R.id.editText_uname);
        password = (EditText) findViewById(R.id.editText_pass);
        confirmPassword = (EditText) findViewById(R.id.editText_confirmpas);

        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
    }

    public void ButtonNext2(View view)
    {
        sEmail = email.getText().toString().trim();
        sUserName = userName.getText().toString().trim();
        sAltEmail = altEmail.getText().toString().trim();
        sPassword = password.getText().toString().trim();
        sConfirmPassword = confirmPassword.getText().toString().trim();


        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"; //for email validation

        userNamePattern = "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$"; //username validation

        //*********** USERNAME DEFINITION ************//
        /*
        ^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$
        └─────┬────┘└───┬──┘└─────┬─────┘└─────┬─────┘ └───┬───┘
        │         │         │            │           no _ or . at the end
        │         │         │            │
        │         │         │            allowed characters
        │         │         │
        │         │         no __ or _. or ._ or .. inside
        │         │
        │         no _ or . at the beginning
        │
        username is 8-20 characters long
        */

        passwordPattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

        //********* PASSWORD DEFINITION ***********//
        /*
        At least one upper case english letter
        At least one lower case english letter
        At least one digit
        At least one special character
        Minimum 8 in length
         */

        //********* identical email, username, altEmail from pi and do validation of app level ***********//
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    class GetDataJSON extends AsyncTask<String, String, String>  {

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
            String url = "http://192.168.0.103/db_val_reg2.php?Email="+sEmail+"&Username="+sUserName+"&AltEmail="+sAltEmail;

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

            /*SharedPreferences query_result = getSharedPreferences("query", 0);
            SharedPreferences.Editor edit = query_result.edit();

            edit.putString(result_tag, result);

            edit.commit();*/

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

               // for(int i=0;i<retrievedArray.length();i++)
                {
                    JSONObject c = retrievedArray.getJSONObject(0);
                    email_out= c.getString("Email_Address");

                    c = retrievedArray.getJSONObject(0);
                    username_out = c.getString("Username");

                    c = retrievedArray.getJSONObject(0);
                    alt_email_out = c.getString("Alt_Email_Address");
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            //********* VALIDATION CODE ***********//
            if( sEmail.matches(email_out) || sUserName.matches(username_out) || sAltEmail.matches(alt_email_out) || sEmail.length() == 0 || !(sEmail.matches(emailPattern)) || sUserName.length() == 0 || !(sUserName.matches(userNamePattern)) || sPassword.length() == 0 || !(sPassword.matches(passwordPattern)) || sAltEmail.length() == 0 || !(sAltEmail.matches(emailPattern)) || sAltEmail.matches(sEmail) || !(sConfirmPassword.matches(sPassword)) )
            {
                if (sEmail.length() == 0)
                    email.setError("Email is required!");

                else if (!(sEmail.matches(emailPattern))) {
                    email.setError("Invalid Email Address!");
                }

                else if (sEmail.matches(email_out)) {
                    email.setError("This Email Address is already registered!");
                }

                if (sUserName.length() == 0)
                    userName.setError("Username is required!");

                else if (!(sUserName.matches(userNamePattern))) {
                    userName.setError("Invalid Username!");
                }

                else if (sUserName.length() < 8) {
                    userName.setError("Username must be 8 to 20 character long!");
                }

                else if (sUserName.length() > 20) {
                    userName.setError("Username must be 8 to 20 character long!");
                }

                else if (sUserName.matches(username_out)) {
                    userName.setError("This Username is already registered!");
                }

                if (sPassword.length() == 0)
                    password.setError("Password is required!");

                else if (!(sPassword.matches(passwordPattern))) {
                    password.setError("Invalid password! Password must contain At least one upper case,one lower case, 1 digit, 1 special character");
                    //Toast.makeText(this, "HINT: Password must be 8 to 20 character long", Toast.LENGTH_SHORT).show();
                }

                else if (sPassword.length() < 8) {
                    userName.setError("Password must be 8 to 20 character long!");
                }

                else if (sPassword.length() > 20) {
                    userName.setError("Password must be 8 to 20 character long!");
                }

                if (sAltEmail.length() == 0)
                    altEmail.setError("Alternate Email is required!");

                else if (!(sAltEmail.matches(emailPattern))) {
                    altEmail.setError("Invalid Email Address!");
                }

                else if (sAltEmail.matches(sEmail)) {
                    altEmail.setError("Please provide other email address!");
                }

                else if (sAltEmail.matches(alt_email_out)) {
                    altEmail.setError("This Email is already registered!");
                }

                if (!(sConfirmPassword.matches(sPassword))) {
                    confirmPassword.setError("Password not matched!");
                }
            }
            else
            {
                SharedPreferences reg2_Pref = getSharedPreferences("reg_pref", 0);

                SharedPreferences.Editor edit = reg2_Pref.edit();

                edit.putString("Email", sEmail);
                edit.putString("Alt_Email", sAltEmail);
                edit.putString("Username", sUserName);
                edit.putString("Password", sPassword);

                edit.commit();

                //************** VALIDATION SUCCESSFUL SO GO TO NEXT PAGE *****************//
                Intent next2 = new Intent("com.example.sadi.smartdoorapp.activity_registration3");
                startActivity(next2);
            }
           //pDialog.dismiss();
        }
    }
}