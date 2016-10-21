package com.example.sadi.smartdoorapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Sami Ullah on 3/16/2016.
 */
public class Security_Settings extends AppCompatActivity
{
    public static EditText secQ1;
    public static EditText secA1;
    public static EditText secQ2;
    public static EditText secA2;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_settings);

        secQ1=(EditText)findViewById(R.id.editText_secQ1_sec);
        secA1=(EditText)findViewById(R.id.editText_secAns1_sec);
        secQ2=(EditText)findViewById(R.id.editText_secQ2_sec);
        secA2=(EditText)findViewById(R.id.editText_secAns2_sec);
    }

    public void SaveChanges3(View view)
    {
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
            //********** NOTE: push changes to pi *************//

            Toast.makeText(this, "Changes saved successfully!" , Toast.LENGTH_SHORT).show();
        }
    }
}
