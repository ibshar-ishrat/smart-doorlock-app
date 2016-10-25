package com.example.sadi.smartdoorapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by Sadia Sami on 10/23/2016.
 */

public class test_db extends ActionBarActivity {

    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_db);

        myDB = new DatabaseHelper(this);

        viewAll();
    }

    public void viewAll() {

        Cursor res = myDB.getAllData();

                        if(res.getCount() == 0) {
                            // show message
                            //showMessage("Error","Nothing found");
                            Toast.makeText(test_db.this, "Error , Nothing found", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        StringBuffer buffer = new StringBuffer();

                        while (res.moveToNext()) {
                            buffer.append("Name :"+ res.getString(0)+"\n");
                            buffer.append("ID :"+ res.getString(1)+"\n");
                            //buffer.append("Surname :"+ res.getString(2)+"\n");
                            //buffer.append("Marks :"+ res.getString(3)+"\n\n");
                        }

                        // Show all data
                        showMessage("DOOR LIST",buffer.toString());
                    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}


