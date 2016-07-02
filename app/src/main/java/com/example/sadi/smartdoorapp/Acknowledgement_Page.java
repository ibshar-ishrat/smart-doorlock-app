package com.example.sadi.smartdoorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Sadia Sami on 7/2/2016.
 */
public class Acknowledgement_Page extends Main_ScreenActivity {
    private static Button button_login_ack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acknowledge_page);
        ButtonLogin();

    }
    public void ButtonLogin()
    {
        button_login_ack=(Button)findViewById(R.id.button_Login_ack);
        button_login_ack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Acknowledgement_Page.this, Main_ScreenActivity.class);
                startActivity(i);
            }





        });
    }
}
