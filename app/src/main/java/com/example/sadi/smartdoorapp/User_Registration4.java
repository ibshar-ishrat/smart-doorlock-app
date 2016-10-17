package com.example.sadi.smartdoorapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Sami Ullah on 3/16/2016.
 */
public class User_Registration4 extends Main_ScreenActivity
{
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
            //Here define all your sharedpreferences code with key and value
            //create object of SharedPreferences
            SharedPreferences prefs = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
            //Now get editor
            SharedPreferences.Editor edit = prefs.edit();
            //takes value from edit text
            edit.putString("MID", sDoorName);
            //commits your edit
            edit.commit();

            Intent next3 = new Intent("com.example.sadi.smartdoorapp.Acknowledgement_Page");
            startActivity(next3);
        }
    }
}