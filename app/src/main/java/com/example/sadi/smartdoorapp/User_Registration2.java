package com.example.sadi.smartdoorapp;

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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Sami Ullah on 3/16/2016.
 */
public class User_Registration2 extends Main_ScreenActivity {
    public static EditText email;
    public static EditText userName;
    public static EditText altEmail;
    public static EditText password;
    public static EditText confirmPassword;

    public static String result_tag;

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

    public void ButtonNext2(View view) {
        String sEmail = email.getText().toString().trim();
        String sUserName = userName.getText().toString().trim();
        String sAltEmail = altEmail.getText().toString().trim();
        String sPassword = password.getText().toString().trim();
        String sConfirmPassword = confirmPassword.getText().toString().trim();
        ;

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"; //for email validation

        String userNamePattern = "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$"; //username validation

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

        String passwordPattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

        //********* PASSWORD DEFINITION ***********//
        /*
        At least one upper case english letter
        At least one lower case english letter
        At least one digit
        At least one special character
        Minimum 8 in length
         */

        //********* VALIDATION CODE ***********//

        //********* identical email from pi ***********//
        result_tag = "email";
        new GetDataJSON().execute("db_validate_email.php?Email=abc@g.com", result_tag );

        SharedPreferences res = getSharedPreferences("query", 0);
        String email_out  = res.getString(result_tag, "");

        //Toast.makeText(this, email_out, Toast.LENGTH_LONG).show();


        //********* identical username from pi ***********//
        result_tag = "username";
        new GetDataJSON().execute("db_validate_username.php?Username=JiyaAliCIS", result_tag );

        SharedPreferences res2 = getSharedPreferences("query", 0);
        String username_out  = res2.getString(result_tag, "");

        //Toast.makeText(this, username_out, Toast.LENGTH_LONG).show();

        //********* identical alt-email from pi ***********//
        result_tag = "alt-email";
        new GetDataJSON().execute("db_validate_alt_email.php?AltEmail=xyz@g.com", result_tag );

        SharedPreferences res3 = getSharedPreferences("query", 0);
        String alt_email_out  = res3.getString(result_tag, "");

        //Toast.makeText(this, alt_email_out, Toast.LENGTH_LONG).show();


        if(sEmail.length() == 0 || !(sEmail.matches(emailPattern)) || sEmail.matches(email_out) || sUserName.length() == 0 || !(sUserName.matches(userNamePattern)) || sUserName.matches(username_out) || sPassword.length() == 0 || !(sPassword.matches(passwordPattern)) || sAltEmail.length() == 0 || !(sAltEmail.matches(emailPattern)) || sAltEmail.matches(alt_email_out) || sAltEmail.matches(sEmail) || !(sConfirmPassword.matches(sPassword)) )
        {
            if (sEmail.length() == 0)
                email.setError("Email is required!");

            else if (!(sEmail.matches(emailPattern))) {
                email.setError("Invalid Email Address!");
            }

            else if (sEmail.matches(email_out))
            {
                email.setError("This Email Address is already registered!");
            }

            if (sUserName.length() == 0)
                userName.setError("Username is required!");

            else if (!(sUserName.matches(userNamePattern))) {
                //**************** NOTE ****************//

                /* Also check if someone has taken this user name already or not for this we have to access Pi*/

                userName.setError("Invalid Username!");
                Toast.makeText(this, "HINT: Username must be 8 to 20 character long", Toast.LENGTH_SHORT).show();
            }

            else if (sUserName.matches(username_out))
            {
                userName.setError("This Username is already registered!");
            }

            if (sPassword.length() == 0)
                password.setError("Password is required!");

            else if (!(sPassword.matches(passwordPattern))) {
                password.setError("Invalid password! Password must contain At least one upper case,one lower case, 1 digit, 1 special character");
                Toast.makeText(this, "HINT: Password must be 8 to 20 character long", Toast.LENGTH_SHORT).show();
            }

            if (sAltEmail.length() == 0)
                altEmail.setError("Alternate Email is required!");

            else if (!(sAltEmail.matches(emailPattern))) {
                altEmail.setError("Invalid Email Address!");
            } else if (sAltEmail.matches(sEmail)) {
                altEmail.setError("Please provide other email address!");
            }

            else if (sAltEmail.matches(alt_email_out))
            {
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
    }

    class GetDataJSON extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args)
        {

            String in_php_script = args[0];
            String result_tag = args[1];

            String url = "http://192.168.0.101/" + in_php_script;

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

            SharedPreferences query_result = getSharedPreferences("query", 0);
            SharedPreferences.Editor edit = query_result.edit();

            edit.putString(result_tag, result);

            edit.commit();

            return result;
        }
    }
}