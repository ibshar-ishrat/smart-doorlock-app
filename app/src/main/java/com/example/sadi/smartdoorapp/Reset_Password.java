package com.example.sadi.smartdoorapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Reset_Password extends AppCompatActivity
{
    public static EditText password;
    public static EditText confirmPassword;

    public static String IP_ADDRESS = "192.168.0.102";

    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        password = (EditText)findViewById(R.id.editText_newpas_reset);
        confirmPassword = (EditText)findViewById(R.id.editText_confpass_reset);
    }

    public void Submit(View view)
    {
        String sPassword = password.getText().toString().trim();
        String sConfirmPassword = confirmPassword.getText().toString().trim();;

        String passwordPattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

        //********* PASSWORD DEFINITION ***********//
        /*
        At least one upper case english letter
        At least one lower case english letter
        At least one digit
        At least one special character
        Minimum 8 in length
         */

        if( sPassword.length() == 0 || !(sPassword.matches(passwordPattern)) || !(sConfirmPassword.matches(sPassword)) )
        {
            if( sPassword.length() == 0 )
                password.setError( "Password is required!" );

            else if( !(sPassword.matches(passwordPattern)) )
            {
                password.setError("Invalid password! Password must contain At least one upper case,one lower case, 1 digit, 1 special character");
                Toast.makeText(this, "HINT: Password must be 8 to 20 character long", Toast.LENGTH_SHORT).show();
            }

            if ( !(sConfirmPassword.matches(sPassword)) )
            {
                confirmPassword.setError("Password not matched!");
            }
        }
        else
        {
            SharedPreferences set_pw = getSharedPreferences("new_pw", 0);

            SharedPreferences.Editor edit = set_pw.edit();

            edit.putString("New_Password", sPassword);

            edit.commit();

            new Set_New_PW().execute();
        }
    }

    class Set_New_PW extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute()
        {
            /*super.onPreExecute();
            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();*/
        }

        @Override
        protected String doInBackground(String... args)
        {
            SharedPreferences sP = getSharedPreferences("new_pw", 0);

            List<NameValuePair> params = new ArrayList<>();

            params.add(new BasicNameValuePair("New_Password", sP.getString("New_Password", "") ));
            params.add(new BasicNameValuePair("MAC", Utils.getMACAddress("wlan0" ) ));

            //params.add(new BasicNameValuePair("Phone_IP_Public", reg4_Pref.getString("Phone_IP_Public", "") ));
            //params.add(new BasicNameValuePair("Phone_IP_Local", reg4_Pref.getString("Phone_IP_Local", "") ));


            JSONObject json = jsonParser.makeHttpRequest("http://"+IP_ADDRESS+"/db_reset_pw.php", "POST", params);

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
            //pDialog.dismiss();

            Intent next = new Intent("com.example.sadi.smartdoorapp.Acknowledgement_Page");
            startActivity(next);
        }
    }
}