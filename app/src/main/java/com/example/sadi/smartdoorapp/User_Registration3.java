package com.example.sadi.smartdoorapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import static com.example.sadi.smartdoorapp.GCMRegistrationIntentService.token;

/**
 * Created by Sami Ullah on 3/16/2016.
 */
public class User_Registration3 extends Main_ScreenActivity
{
    public static EditText secQ1;
    public static EditText secA1;
    public static EditText secQ2;
    public static EditText secA2;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration3);

        secQ1=(EditText)findViewById(R.id.editText_secQ1);
        secA1=(EditText)findViewById(R.id.editText_secAns1);
        secQ2=(EditText)findViewById(R.id.editText_secQ2);
        secA2=(EditText)findViewById(R.id.editText_secAns2);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {

            //When the broadcast received
            //We are sending the broadcast from GCMRegistrationIntentService

            @Override
            public void onReceive(Context context, Intent intent) {
                //If the broadcast has received with success
                //that means device is registered successfully
                if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)) {
                    //Getting the registration token from the intent
                    String token = intent.getStringExtra("token");
                    System.out.println("Here we go");
                    //Displaying the token as toast
                    //Toast.makeText(getApplicationContext(), "Registration token:" + token, Toast.LENGTH_LONG).show();

                    //if the intent is not with success then displaying error messages
                } else if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)) {
                    Toast.makeText(getApplicationContext(), "GCM registration error!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();
                }
            }
        };
                /* REGISTER THE USER TO GOOGLE CLOUD MESSAGING */


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
            Intent itent = new Intent(User_Registration3.this, GCMRegistrationIntentService.class);
            startService(itent);
        }
        /* USER HAS BEEN REGISTERED TO GOOGLE CLOUD MESSAGING */
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
    //Registering receiver on activity resume
    @Override
    protected void onResume()
    {
        super.onResume();
        Log.w("MainActivity", "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }
    //Unregistering receiver on activity paused
    @Override
    protected void onPause()
    {
        super.onPause();
        Log.w("MainActivity", "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    public void ButtonNext3(View view)
    {
        //System.out.println(token);
        String sSecQ1 =  secQ1.getText().toString().trim();
        String sSecQ2 =  secQ2.getText().toString().trim();
        String sSecA1 =  secA1.getText().toString().trim();
        String sSecA2 =  secA2.getText().toString().trim();

        //********* VALIDATION CODE ***********//
        if(  sSecQ1.length() == 0 || sSecQ2.length() == 0 || sSecA1.length() == 0 || sSecA2.length() == 0 )
        {
            if( sSecQ1.length() == 0 )
                secQ1.setError( "Security Question is required!" );

            else if( sSecQ1.length() > 255 )
                secQ1.setError( "Security Question length exceeded!" );

            if( sSecQ2.length() == 0 )
                secQ2.setError( "Security Question is required!" );

            else if( sSecQ2.length() > 255 )
                secQ2.setError( "Security Question length exceeded!" );

            if( sSecA1.length() == 0 )
                secA1.setError( "Please answer your security question!" );

            else if( sSecA1.length() > 25 )
                secA1.setError( "Please answer question in 25 characters!" );

            if( sSecA2.length() == 0 )
                secA2.setError( "Please answer your security question!" );

            else if( sSecA2.length() > 25 )
                secA2.setError( "Please answer question in 25 characters!" );
        }
        else
        {
            SharedPreferences reg3_Pref = getSharedPreferences("reg_pref", 0);

            SharedPreferences.Editor edit = reg3_Pref.edit();

            edit.putString("SecQ1", sSecQ1);
            edit.putString("SecQ2", sSecQ2);
            edit.putString("SecA1", sSecA1);
            edit.putString("SecA2", sSecA2);
            edit.putString("Token",token);

            edit.commit();

            //************** VALIDATION SUCCESSFUL SO GO TO NEXT PAGE *****************//
            Intent next3 = new Intent("com.example.sadi.smartdoorapp.activity_registration4");
            startActivity(next3);
        }
    }
}