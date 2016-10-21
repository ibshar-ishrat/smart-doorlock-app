package com.example.sadi.smartdoorapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sami Ullah on 3/16/2016.
 */
public class User_Registration4 extends Main_ScreenActivity
{
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

    public static EditText pinCode;
    public static EditText confirmPinCode;
    public static EditText doorName;
    public static EditText doorDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration4);

        pinCode = (EditText)findViewById(R.id.editText_pin);
        confirmPinCode = (EditText)findViewById(R.id.editText_confirmpin);
        doorName = (EditText)findViewById(R.id.editText_nameDoor);
        doorDesc = (EditText)findViewById(R.id.editText_desc_Door);
    }

    public void ButtonDone(View view)
    {
        String sPinCode = pinCode.getText().toString().trim();
        String sConfirmPinCode = confirmPinCode.getText().toString().trim();
        String sDoorName = doorName.getText().toString().trim();
        String sDoorDesc = doorDesc.getText().toString().trim();

        //********* VALIDATION CODE ***********//
        if ( sPinCode.length() == 0 ||sPinCode.length() != 4 || sConfirmPinCode.length() == 0 || !(sConfirmPinCode.matches(sPinCode)) || sConfirmPinCode.length() != 4 || sDoorName.length() == 0 || sDoorName.length() > 30 || sDoorDesc.length() > 255 )
        {
            if (sPinCode.length() == 0)
                pinCode.setError("PIN code is required!");

            else if (sPinCode.length() != 4 )
                pinCode.setError("PIN code must be of 4 digits!");

            if (sConfirmPinCode.length() == 0 )
                confirmPinCode.setError("PIN code is required!");

            else if (sConfirmPinCode.length() != 4 )
                confirmPinCode.setError("PIN code not matched!");

            else if ( !(sConfirmPinCode.matches(sPinCode)) )
                confirmPinCode.setError("PIN code not matched!");

            if (sDoorName.length() == 0)
                doorName.setError("Door Name is required!");

            else if (sDoorName.length() > 30)
                doorName.setError("Door name is too long!");

            if (sDoorDesc.length() > 255)
                doorDesc.setError("Door description is too long!");
        }

        else {
            SharedPreferences reg4_Pref = getSharedPreferences("reg_pref", 0);

            SharedPreferences.Editor edit = reg4_Pref.edit();

            edit.putString("Pin_Code", sPinCode);
            edit.putString("Door_Name", sDoorName);
            if(sDoorDesc != "") edit.putString("Door_Desc", sDoorDesc);

            edit.commit();

            Toast.makeText(this, reg4_Pref.getString("L_Name", "") , Toast.LENGTH_SHORT).show();
            new Create_User().execute();
        }
    }

    class Create_User extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(User_Registration4.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args)
        {
            SharedPreferences reg4_Pref = getSharedPreferences("reg_pref", 0);

            List<NameValuePair> params = new ArrayList<>();

            params.add(new BasicNameValuePair("db_col_name", reg4_Pref.getString("F_Name", "") ));
            params.add(new BasicNameValuePair("db_col_name", reg4_Pref.getString("L_Name", "")  ));
            params.add(new BasicNameValuePair("db_col_name", reg4_Pref.getString("Country", "") ));
            params.add(new BasicNameValuePair("db_col_name", reg4_Pref.getString("City", "") ));

            params.add(new BasicNameValuePair("db_col_name", reg4_Pref.getString("Email", "") ));
            params.add(new BasicNameValuePair("db_col_name", reg4_Pref.getString("Alt_Email", "")  ));
            params.add(new BasicNameValuePair("db_col_name", reg4_Pref.getString("Username", "") ));
            params.add(new BasicNameValuePair("db_col_name", reg4_Pref.getString("Password", "") ));

            params.add(new BasicNameValuePair("db_col_name", reg4_Pref.getString("SecQ1", "") ));
            params.add(new BasicNameValuePair("db_col_name", reg4_Pref.getString("SecQ2", "")  ));
            params.add(new BasicNameValuePair("db_col_name", reg4_Pref.getString("SecA1", "") ));
            params.add(new BasicNameValuePair("db_col_name", reg4_Pref.getString("SecA2", "") ));

            params.add(new BasicNameValuePair("db_col_name", reg4_Pref.getString("Pin_Code", "") ));
            params.add(new BasicNameValuePair("db_col_name", reg4_Pref.getString("Door_Name", "")  ));
            params.add(new BasicNameValuePair("db_col_name", reg4_Pref.getString("Door_Desc", "") ));

            /*JSONObject json = jsonParser.makeHttpRequest("http://192.168.1.102/user_create.php", "POST", params);

            try
            {
                int success = json.getInt("success");

                if (success == 1)
                {
                    finish();
                }
            }
            catch (JSONException e
            {
                e.printStackTrace();
            }*/

            return null;
        }

        protected void onPostExecute(String file_url)
        {
            pDialog.dismiss();

            Intent next3 = new Intent("com.example.sadi.smartdoorapp.Acknowledgement_Page");
            startActivity(next3);
        }
    }

}