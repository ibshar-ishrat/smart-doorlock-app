package com.example.sadi.smartdoorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Forget_Password extends AppCompatActivity
{
    public static TextView secQ1;
    public static EditText secA1;
    public static TextView secQ2;
    public static EditText secA2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_verification);

        //************* NOTE: bring questions from pi and show them on text view of questions *****************//

        secQ1=(TextView) findViewById(R.id.textView_ques_ver);
        secQ2=(TextView) findViewById(R.id.textView_ques_ver2);

        secA1=(EditText)findViewById(R.id.editText_Answer_ver1);
        secA2=(EditText)findViewById(R.id.editText_answer_ver2);
    }

    public void Button_verify (View view)
    {

        String sSecA1 =  secA1.getText().toString().trim();
        String sSecA2 =  secA2.getText().toString().trim();

        if( sSecA1.length() == 0 )
            secA1.setError( "Please answer your security question!" );

        if( sSecA2.length() == 0 )
            secA2.setError( "Please answer your security question!" );

        //************** NOTE: brings answers of questions from pi and verify them with these 2 edit text boxes ***************//

        Intent next1 = new Intent("com.example.sadi.smartdoorapp.Reset_Password");
        startActivity(next1);
    }


}
