package com.example.sadi.smartdoorapp;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ibshar on 26/10/2016.
 */

public class Door_Info2 extends Activity {



    DatabaseHelper myDB;
    private ArrayList<Integer> id = new ArrayList<Integer>();
    private ArrayList<String> results = new ArrayList<String>();
    private String tableName = myDB.tableName;
    private SQLiteDatabase newDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.door_info);
        //Intent i = getIntent();

       id = (ArrayList<Integer>) getIntent().getSerializableExtra("key");

        int mydoor = id.get(1);

        myDB = new DatabaseHelper(this);
        openAndQueryDatabase(mydoor);
       //id = getIntent().getExtras().getIntegerArrayList("id");
        //idhar chaiye????????// yes //give me two min ok

        /*SharedPreferences get_id = getSharedPreferences("pass", 0);
        id = get_id.getLong("user_id",0);*/





        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //String value = prefs.getString("key", null);

       //TextView tv = (TextView) findViewById(R.id.Door_info);
       //tv.setText( String.valueOf(mydoor));

                //actually we need to cast it in string first see conversion ok :)run now ok
    }

    private void openAndQueryDatabase(int myDoor) {
        try {
            myDB= new DatabaseHelper(this.getApplicationContext());
            newDB = myDB.getWritableDatabase();



            Cursor c = newDB.rawQuery("select NAME, Description from DEVICE_NAME where Name_ID =" + myDoor + " ",null);


            if (c != null ) {

                if (c.moveToFirst()) {
                    do {
                        String Name = c.getString(c.getColumnIndex("Name"));
                        String Description = c.getString(c.getColumnIndex("Description"));
                        results.add("Name: " + Name + "  Description: " + Description);

                    } while (c.moveToNext());
                }

                displayResultList();

            }
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        } finally {
            if (newDB != null)
                newDB.execSQL("DELETE FROM " + tableName);
            newDB.close();
        }


    }

    private void displayResultList() {


        TextView tView = (TextView) findViewById(R.id.tag_Door_info);
        //TextView tView = new TextView(this);
        tView.setText("Information of All Doors....");
        ListView list = (ListView)findViewById(R.id.listView);


        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results);

        list.setAdapter(adapter);

    }

}
