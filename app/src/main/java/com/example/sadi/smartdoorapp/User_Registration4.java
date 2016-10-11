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
public class User_Registration4 extends Main_ScreenActivity {
    private static Button button_done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration4);
        ButtonDone();
    }


    public void ButtonDone()
    {
        button_done=(Button)findViewById(R.id.button_done);
        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               EditText txtItem = (EditText) findViewById(R.id.editText_nameDoor);
                String text = txtItem.getText().toString();
                //Intent i = new Intent(User_Registration4.this, sidePanel.class);
                //i.putExtra("text_label", text);



                //Here define all your sharedpreferences code with key and value
                //create object of SharedPreferences
                SharedPreferences prefs = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                //Now get editor
                SharedPreferences.Editor edit = prefs.edit();
                //takes value from edit text
                edit.putString("MID", text );
                //commits your edit
                edit.commit();

                Intent next3 = new Intent("com.example.sadi.smartdoorapp.Acknowledgement_Page");
                startActivity(next3);

            }



        });
    }

}
