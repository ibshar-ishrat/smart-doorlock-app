package com.example.sadi.smartdoorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Reset_Password extends AppCompatActivity
{
    public static EditText password;
    public static EditText confirmPassword;

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

        //****************** NOTE: got to some other page? *******************//
        Toast.makeText(this, "Changes saved successfully!" , Toast.LENGTH_SHORT).show();

        Intent next = new Intent("com.example.sadi.smartdoorapp.Acknowledgement_Page");
        startActivity(next);

    }
}