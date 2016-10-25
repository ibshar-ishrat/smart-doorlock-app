package com.example.sadi.smartdoorapp;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sami Ullah on 3/16/2016.
 */
public class User_Registration4 extends Main_ScreenActivity
{
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private BroadcastReceiver mRegistrationBroadcastReceiver;

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

        else
        {
            SharedPreferences reg4_Pref = getSharedPreferences("reg_pref", 0);

            SharedPreferences.Editor edit = reg4_Pref.edit();

            edit.putString("Pin_Code", sPinCode);
            edit.putString("Door_Name", sDoorName);
            if(sDoorDesc != "") edit.putString("Door_Desc", sDoorDesc);

            edit.commit();


            /*CHECK FOR SUCCESSFUL INTERNET CONNECTIVITY ON PHONE THEN GO FOR REGISTRATION*/

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo == null || !networkInfo.isConnected())
            {
                Toast.makeText(this,"No active internet connection. Please try again later!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                      /* REGISTER THE USER TO GOOGLE CLOUD MESSAGING */
                mRegistrationBroadcastReceiver = new BroadcastReceiver()
                {

                    //When the broadcast received
                    //We are sending the broadcast from GCMRegistrationIntentService

                    @Override
                    public void onReceive(Context context, Intent intent)
                    {
                        //If the broadcast has received with success
                        //that means device is registered successfully
                        if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS))
                        {
                            //Getting the registration token from the intent
                            String token = intent.getStringExtra("token");
                            //Displaying the token as toast
                            Toast.makeText(getApplicationContext(), "Registration token:" + token, Toast.LENGTH_LONG).show();

                            //if the intent is not with success then displaying error messages
                        }
                        else if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR))
                        {
                            Toast.makeText(getApplicationContext(), "GCM registration error!", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();
                        }
                    }
                };

                //Checking play service is available or not
                int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

                //if play service is not available
                if(ConnectionResult.SUCCESS != resultCode)
                {
                    //If play service is supported but not installed
                    if(GooglePlayServicesUtil.isUserRecoverableError(resultCode))
                    {
                        //Displaying message that play service is not installed
                        Toast.makeText(getApplicationContext(), "Google Play Service is not install/enabled in this device!", Toast.LENGTH_LONG).show();
                        GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());

                        //If play service is not supported
                        //Displaying an error message
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "This device does not support for Google Play Service!", Toast.LENGTH_LONG).show();
                    }

                    //If play service is available
                }
                else
                {
                    //Starting intent to register device
                    Intent itent = new Intent(this, GCMRegistrationIntentService.class);
                    startService(itent);
                }
        /* USER HAS BEEN REGISTERED TO GOOGLE CLOUD MESSAGING */

                new Create_User().execute();
            }
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

            params.add(new BasicNameValuePair("F_Name", reg4_Pref.getString("F_Name", "") ));
            params.add(new BasicNameValuePair("L_Name", reg4_Pref.getString("L_Name", "")  ));
            params.add(new BasicNameValuePair("Country", reg4_Pref.getString("Country", "") ));
            params.add(new BasicNameValuePair("City", reg4_Pref.getString("City", "") ));

            params.add(new BasicNameValuePair("Email", reg4_Pref.getString("Email", "") ));
            params.add(new BasicNameValuePair("Alt_Email", reg4_Pref.getString("Alt_Email", "")  ));
            params.add(new BasicNameValuePair("Username", reg4_Pref.getString("Username", "") ));
            params.add(new BasicNameValuePair("PW", reg4_Pref.getString("Password", "") ));

            params.add(new BasicNameValuePair("SecQ1", reg4_Pref.getString("SecQ1", "") ));
            params.add(new BasicNameValuePair("SecQ2", reg4_Pref.getString("SecQ2", "")  ));
            params.add(new BasicNameValuePair("SecA1", reg4_Pref.getString("SecA1", "") ));
            params.add(new BasicNameValuePair("SecA2", reg4_Pref.getString("SecA2", "") ));

            params.add(new BasicNameValuePair("Pin_Code", reg4_Pref.getString("Pin_Code", "") ));
            params.add(new BasicNameValuePair("Door_Name", reg4_Pref.getString("Door_Name", "")  ));
            params.add(new BasicNameValuePair("Door_Desc", reg4_Pref.getString("Door_Desc", "") ));



            // Find MAC and IP Address of Device
            String mac_addr = Utils.getMACAddress("wlan0");
            //String mac_addr_eth = Utils.getMACAddress("eth0");

            String ip_addr = Utils.getIPAddress(true); // IPv4
            //String ip_addr_v6 = Utils.getIPAddress(false); // IPv6


            //Find Public IP Address
            String ip_public = null; //you get the IP as a String

            URL whatismyip = null;
            try
            {
                whatismyip = new URL("http://checkip.amazonaws.com");
            } catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            BufferedReader in = null;
            try
            {
                in = new BufferedReader(new InputStreamReader(
                        whatismyip.openStream()));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            try
            {
                ip_public = in.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            params.add(new BasicNameValuePair("Phone_MAC_Addr", mac_addr ));
            params.add(new BasicNameValuePair("Phone_IP_Public", ip_public ));
            params.add(new BasicNameValuePair("Phone_IP_Local", ip_addr ));


            JSONObject json = jsonParser.makeHttpRequest("http://192.168.0.101/db_register_user.php", "POST", params);

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

            Intent next3 = new Intent("com.example.sadi.smartdoorapp.Acknowledgement_Page");
            startActivity(next3);
        }
    }
}