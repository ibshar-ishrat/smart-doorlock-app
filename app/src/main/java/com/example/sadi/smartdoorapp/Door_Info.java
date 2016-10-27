package com.example.sadi.smartdoorapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ibshar on 26/10/2016.
 */

public class Door_Info extends Activity {
     private ArrayList<Integer> id = new ArrayList<Integer>();


    DatabaseHelper myDB;
    private ArrayList<String> results = new ArrayList<String>();
    private String tableName = myDB.tableName;
    private SQLiteDatabase newDB;

    //private ArrayList<Integer> result_array = new ArrayList<Integer>();
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
     setContentView(R.layout.door_info);
     //Intent i = getIntent();

        String name_door = getIntent().getExtras().getString("key");


        //getting array of ids of Doors
       // id = (ArrayList<Integer>) getIntent().getSerializableExtra("key");
        //getting id of Door1
        //int door = id.get(0);

       // Toast.makeText(this,name_door,Toast.LENGTH_SHORT).show();

        //myDB = new DatabaseHelper(this);
        openAndQueryDatabase(name_door);

         }





    private void openAndQueryDatabase(String myDoor1) {
        try {
            myDB= new DatabaseHelper(this.getApplicationContext());
            newDB = myDB.getWritableDatabase();

            SharedPreferences door_list = getSharedPreferences("door_list", 0);
            int i = door_list.getInt(myDoor1,5);

            Cursor c = newDB.rawQuery("select NAME, Description from DEVICE_NAME where Name_ID="+i+"", null);
            Toast.makeText(this,Integer.toString(door_list.getInt(myDoor1,5)),Toast.LENGTH_SHORT).show();


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


        TextView tView = (TextView) findViewById(R.id.Door_info);
        //TextView tView = new TextView(this);
        tView.setText("Information of All Doors....");
        ListView list = (ListView)findViewById(R.id.listView);


        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results);

        list.setAdapter(adapter);

    }

       /* getListView().addHeaderView(tView);


        getListView().setTextFilterEnabled(true);*/


    }




  /*  private void list(final ArrayList<Integer> result_array )
    {
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //when user click on item new activity open and id pass to next activity

                switch(position){

                    case 1: Intent newActivity = new Intent(view.getContext(), Door_Info.class);
                        newActivity.putExtra("key", result_array);
                        startActivity(newActivity);
                        break;

                    case 2:
                        Intent newActivity1 = new Intent(view.getContext(), Door_Info2.class);
                        newActivity1.putExtra("key", result_array);
                        startActivity(newActivity1);
                        break;

                    case 3:
                        Intent newActivity2 = new Intent(view.getContext(), DoorInfo3.class);
                        newActivity2.putExtra("key", result_array);
                        startActivity(newActivity2);
                        break;

                }



            }

        });
    }
*/

       //id = getIntent().getExtras().getIntegerArrayList("id");
        //idhar chaiye????????// yes //give me two min ok

        /*SharedPreferences get_id = getSharedPreferences("pass", 0)         ;
        id = get_id.getLong("user_id",0);*/





        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //String value = prefs.getString("key", null);

       // TextView tv = (TextView) findViewById(R.id.Door_info);
       // tv.setText( String.valueOf(id.get(0)));

                //actually we need to cast it in string first see conversion ok :)run now ok


