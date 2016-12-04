package com.example.sadi.smartdoorapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Created by Hp-pc on 12/3/2016.
 */

public class Insert_Lock2 extends Main_ScreenActivity
{
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

    public static EditText ip_addr;
    public static EditText mac_addr;

    public static String s_ip_addr;
    public static String s_mac_addr;

    public static String IP_ADDRESS = Main_ScreenActivity.IP_ADDRESS;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_lock2);

        ip_addr = (EditText)findViewById(R.id.editText_ip_id);
        mac_addr = (EditText)findViewById(R.id.editText_mac_id);
    }

    void Insert_Door()
    {
        s_ip_addr = ip_addr.getText().toString().trim();
        s_mac_addr = mac_addr.getText().toString().trim();

        if(s_mac_addr.length()==0 || s_ip_addr.length()==0)
        {
            if(s_ip_addr.length()==0)
            {
                ip_addr.setError("IP Address is required!");
            }

            if(s_mac_addr.length()==0)
            {
                mac_addr.setError("MAC Address is required!");
            }
        }
        else
        {
            //IBSHAR WILL SAVE NAME, IP AND MAC IN FILE
            //ON IP ADDRESS, NAME ALONG WITH PIN CODE AND DESC WILL BE STORED
            //USER DESCRIPTION IS SAVED ON ANOTHER PI FROM THERE IT WILL BE REPLICATE IN NEW PI

        }
    }
}
