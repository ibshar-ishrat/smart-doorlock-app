package com.example.sadi.smartdoorapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Hp-pc on 12/3/2016.
 */

public class InsertLock extends Main_ScreenActivity
{
    public static EditText pinCode;
    public static EditText confirmPinCode;
    public static EditText doorName;
    public static EditText doorDesc;

    public static String sPinCode;
    public static String sConfirmPinCode;
    public static String sDoorName;
    public static String sDoorDesc;

    public static String IP_ADDRESS = Main_ScreenActivity.IP_ADDRESS;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_lock);

        pinCode = (EditText) findViewById(R.id.editText_pin_id);
        confirmPinCode = (EditText) findViewById(R.id.editText_confirmpin_id);
        doorName = (EditText) findViewById(R.id.editText_nameDoor_id);
        doorDesc = (EditText) findViewById(R.id.editText_desc_Door);
    }

    void Goto_InsertDoor2(View view)
    {
        sPinCode = pinCode.getText().toString().trim();
        sConfirmPinCode = confirmPinCode.getText().toString().trim();
        sDoorName = doorName.getText().toString().trim();
        sDoorDesc = doorDesc.getText().toString().trim();

        if (sPinCode.length() == 0 || sPinCode.length() != 4 || sDoorDesc.length() == 0||  sConfirmPinCode.length() == 0 || !(sConfirmPinCode.matches(sPinCode)) || sConfirmPinCode.length() != 4 || sDoorName.length() == 0 || sDoorName.length() > 30 || sDoorDesc.length() > 255) {
            if (sPinCode.length() == 0)
                pinCode.setError("PIN code is required!");

            else if (sPinCode.length() != 4)
                pinCode.setError("PIN code must be of 4 digits!");

            if (sConfirmPinCode.length() == 0)
                confirmPinCode.setError("PIN code is required!");

            else if (sConfirmPinCode.length() != 4)
                confirmPinCode.setError("PIN code not matched!");

            else if (!(sConfirmPinCode.matches(sPinCode)))
                confirmPinCode.setError("PIN code not matched!");

            if (sDoorName.length() == 0)
                doorName.setError("Door Name is required!");

            else if (sDoorName.length() > 30)
                doorName.setError("Door name is too long!");

            if (sDoorDesc.length() == 0)
                doorDesc.setError("Door description is required!");

            else if (sDoorDesc.length() > 255)
                doorDesc.setError("Door description is too long!");
        }
        else
        {
            SharedPreferences id_Pref = getSharedPreferences("insert_door", 0);

            SharedPreferences.Editor edit = id_Pref.edit();

            edit.putString("Pin_Code", sPinCode);
            edit.putString("Door_Name", sDoorName);
            edit.putString("Door_Desc", sDoorDesc);
            edit.commit();

            Intent next = new Intent("com.example.sadi.smartdoorapp.activity_insert_door2");
            startActivity(next);
        }
    }
}
