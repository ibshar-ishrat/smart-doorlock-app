package com.example.sadi.smartdoorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Sadia Sami on 7/2/2016.
 */
public class Acknowledgement_Page extends Main_ScreenActivity {
    private static Button button_login_ack;
    private static TextView tips;
    private PopupWindow pw;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acknowledge_page);
        ButtonLogin();
        Tip();

        }

    private void Tip() {
        tips = (TextView) findViewById(R.id.textView_Tips);
        tips.setOnClickListener(new View.OnClickListener()
        {


            @Override
            public void onClick(View v) {
                Intent i = new Intent(Acknowledgement_Page.this, UserTips_Popup_Window.class);
                startActivity(i);

            }
    });
    }


    public void ButtonLogin() {
                button_login_ack = (Button) findViewById(R.id.button_Login_ack);
                button_login_ack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Acknowledgement_Page.this, Main_ScreenActivity.class);
                        startActivity(i);
                    }


                });
            }

}

