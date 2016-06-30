package com.example.sadi.smartdoorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Sami Ullah on 3/16/2016.
 */
public class User_Registration3 extends Main_ScreenActivity
{
    private static Button button_next3;
    public static EditText secQ1;
    public static EditText secA1;
    public static EditText secQ2;
    public static EditText secA2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration3);
        secQ1=(EditText)findViewById(R.id.editText_secQ1);
        secA1=(EditText)findViewById(R.id.editText_secAns1);
        secQ2=(EditText)findViewById(R.id.editText_secQ2);
        secA2=(EditText)findViewById(R.id.editText_secAns2);
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
        ButtonNext3();

    }
    public void ButtonNext3()
    {
        button_next3=(Button)findViewById(R.id.button_next3);
        button_next3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next3 = new Intent("com.example.sadi.smartdoorapp.activity_registration4");
                startActivity(next3);

            }



        });
    }
}
