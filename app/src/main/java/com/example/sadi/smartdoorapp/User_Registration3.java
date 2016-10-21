package com.example.sadi.smartdoorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import android.content.SharedPreferences;

/**
 * Created by Sami Ullah on 3/16/2016.
 */
public class User_Registration3 extends Main_ScreenActivity
{
    public static EditText secQ1;
    public static EditText secA1;
    public static EditText secQ2;
    public static EditText secA2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration3);

        secQ1=(EditText)findViewById(R.id.editText_secQ1);
        secA1=(EditText)findViewById(R.id.editText_secAns1);
        secQ2=(EditText)findViewById(R.id.editText_secQ2);
        secA2=(EditText)findViewById(R.id.editText_secAns2);

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

    public void ButtonNext3(View view)
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
            SharedPreferences reg3_Pref = getSharedPreferences("reg_pref", 0);

            SharedPreferences.Editor edit = reg3_Pref.edit();

            edit.putString("SecQ1", sSecQ1);
            edit.putString("SecQ2", sSecQ2);
            edit.putString("SecA1", sSecA1);
            edit.putString("SecA2", sSecA2);

            edit.commit();

            //************** VALIDATION SUCCESSFUL SO GO TO NEXT PAGE *****************//
            Intent next3 = new Intent("com.example.sadi.smartdoorapp.activity_registration4");
            startActivity(next3);
        }
    }
}