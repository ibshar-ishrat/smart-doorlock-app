package com.example.sadi.smartdoorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Forget_Password extends AppCompatActivity {
    private static Button Button_verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_verification);

        Button_verify();

    }

    public void Button_verify ()
    {
        Button_verify=(Button)findViewById(R.id.button_verify);
        Button_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next1 = new Intent("com.example.sadi.smartdoorapp.Reset_Password");
                startActivity(next1);

            }



        });
    }

}
