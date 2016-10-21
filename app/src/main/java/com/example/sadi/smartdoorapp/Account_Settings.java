package com.example.sadi.smartdoorapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Sami Ullah on 3/16/2016.
 */
public class Account_Settings extends AppCompatActivity
{
    public static EditText email;
    public static EditText userName;
    public static EditText altEmail;
    public static EditText password;
    public static EditText confirmPassword;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings);

        email = (EditText)findViewById(R.id.editText_acc_email);
        altEmail = (EditText)findViewById(R.id.editText_acc_AltEmail);
        userName = (EditText)findViewById(R.id.editText_acc_uname);
        password = (EditText)findViewById(R.id.editText_acc_pass);
        confirmPassword = (EditText)findViewById(R.id.editText_acc_confirmpas);
    }

    public void SaveChanges2(View view)
    {
        String sEmail = email.getText().toString().trim();
        String sUserName = userName.getText().toString().trim();
        String sAltEmail = altEmail.getText().toString().trim();
        String sPassword = password.getText().toString().trim();
        String sConfirmPassword = confirmPassword.getText().toString().trim();;

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"; //for email validation

        String userNamePattern = "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$"; //username validation

        //*********** USERNAME DEFINITION ************//
        /*
        ^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$
        └─────┬────┘└───┬──┘└─────┬─────┘└─────┬─────┘ └───┬───┘
        │         │         │            │           no _ or . at the end
        │         │         │            │
        │         │         │            allowed characters
        │         │         │
        │         │         no __ or _. or ._ or .. inside
        │         │
        │         no _ or . at the beginning
        │
        username is 8-20 characters long
        */

        String passwordPattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

        //********* PASSWORD DEFINITION ***********//
        /*
        At least one upper case english letter
        At least one lower case english letter
        At least one digit
        At least one special character
        Minimum 8 in length
         */

        //********* VALIDATION CODE ***********//
        if(sEmail.length() == 0 || !(sEmail.matches(emailPattern)) || sUserName.length() == 0 || !(sUserName.matches(userNamePattern)) || sPassword.length() == 0 || !(sPassword.matches(passwordPattern)) || sAltEmail.length() == 0 || !(sAltEmail.matches(emailPattern)) || sAltEmail.matches(sEmail) || !(sConfirmPassword.matches(sPassword)) )
        {
            if( sEmail.length() == 0 )
                email.setError( "Email is required!" );

            else if ( !(sEmail.matches(emailPattern)) )
            {
                email.setError( "Invalid Email Address!" );
            }

            if( sUserName.length() == 0 )
                userName.setError( "Username is required!" );

            else if( !(sUserName.matches(userNamePattern)) )
            {
                //**************** NOTE ****************//
                /* Also check if someone has taken this user name already or not for this we have to access Pi*/

                userName.setError("Invalid Username!");
                Toast.makeText(this, "HINT: Username must be 8 to 20 character long", Toast.LENGTH_SHORT).show();
            }

            if( sPassword.length() == 0 )
                password.setError( "Password is required!" );

            else if( !(sPassword.matches(passwordPattern)) )
            {
                password.setError("Invalid password! Password must contain At least one upper case,one lower case, 1 digit, 1 special character");
                Toast.makeText(this, "HINT: Password must be 8 to 20 character long", Toast.LENGTH_SHORT).show();
            }

            if( sAltEmail.length() == 0 )
                altEmail.setError( "Alternate Email is required!" );

            else if ( !(sAltEmail.matches(emailPattern)) )
            {
                altEmail.setError( "Invalid Email Address!" );
            }

            else if( sAltEmail.matches(sEmail) )
            {
                altEmail.setError("Please provide other email address!");
            }

            if ( !(sConfirmPassword.matches(sPassword)) )
            {
                confirmPassword.setError("Password not matched!");
            }
        }
        else
        {
            //********** NOTE: push changes to pi *************//

            Toast.makeText(this, "Changes saved successfully!" , Toast.LENGTH_SHORT).show();
        }
    }
}
