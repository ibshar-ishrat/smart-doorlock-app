package com.example.sadi.smartdoorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Sami Ullah on 3/16/2016.
 */
public class User_Registration2 extends Main_ScreenActivity
{
    private static Button button_next2;
    public static EditText email;
    public static EditText userName;
    public static EditText contactNum;
    //EditText altEmail;
    public static EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);
        email=(EditText)findViewById(R.id.editText_email);
        userName=(EditText)findViewById(R.id.editText_uname);
        contactNum=(EditText)findViewById(R.id.editText_pnum);
        password=(EditText)findViewById(R.id.editText_pass);
     /*   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        ButtonNext2();
    }


    public void ButtonNext2()
    {
        button_next2=(Button)findViewById(R.id.button_next2);
        button_next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next2 = new Intent("com.example.sadi.smartdoorapp.activity_registration3");
                startActivity(next2);

            }



        });
    }

}
