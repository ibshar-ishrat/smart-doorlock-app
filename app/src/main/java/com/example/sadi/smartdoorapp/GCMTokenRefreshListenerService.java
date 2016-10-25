package com.example.sadi.smartdoorapp;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Ibshar on 23/10/2016.
 */

public class GCMTokenRefreshListenerService extends InstanceIDListenerService
{
    //If the token is changed registering the device again
    @Override
    public void onTokenRefresh()
    {
        Intent intent = new Intent(this, GCMRegistrationIntentService.class);
        startService(intent);
    }
}
