package com.example.sadi.smartdoorapp;


import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ibshar on 25/10/2016.
 */

public class test_db extends ListActivity implements Serializable {

    DatabaseHelper myDB;
    private ArrayList<String> results = new ArrayList<String>();
    private String tableName = myDB.tableName;
    private SQLiteDatabase newDB;

    private ArrayList<Integer> result_array = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.test_db);




        //calling method to export databse on phone



        openAndQueryDatabase();

        //viewAll();

    }





    private void openAndQueryDatabase() {
        try {
            myDB= new DatabaseHelper(this.getApplicationContext());
            newDB = myDB.getWritableDatabase();

            Cursor c = newDB.rawQuery("select NAME, NAME_ID from DEVICE_NAME",null);


            if (c != null ) {

                if (c.moveToFirst()) {
                    do {
                        String Name = c.getString(c.getColumnIndex("Name"));
                        int id = c.getInt(c.getColumnIndex("Name_ID"));


                        /*SharedPreferences pass_id = getSharedPreferences("pass", 0);
                        SharedPreferences.Editor edit = pass_id.edit();

                        edit.putLong("user_id",id);
                        edit.commit();*/


                    results.add(Name);
                        result_array.add( id);
                    } while (c.moveToNext());


                    SharedPreferences door_list = getSharedPreferences("door_list", 0);
                    SharedPreferences.Editor edit = door_list.edit();
                    for(int i=0; i < result_array.size(); i++)
                    {
                        int temp_id = result_array.get(i);
                        String temp_name = results.get(i);

                        edit.putInt(temp_name ,temp_id);
                    }

                    edit.commit();


                }

                displayResultList();
                list(results);   //passing id
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
        TextView tView = new TextView(this);
        tView.setText("List of All Doors....");
        //ListView list = new ListView(this);
        getListView().addHeaderView(tView);

        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, results));
        getListView().setTextFilterEnabled(true);


    }




        private void list(final  ArrayList<String> results )
    {
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               //when user click on item new activity open and id pass to next activity

                switch(position){

                    case 1: Intent newActivity = new Intent(view.getContext(), Door_Info.class);
                        int pos= position - 1;
                        newActivity.putExtra("key", results.get(pos));
                        startActivity(newActivity);
                        break;

                    case 2:
                        Intent newActivity1 = new Intent(view.getContext(), Door_Info2.class);
                        int pos_item2 = position - 1;
                        newActivity1.putExtra("key", results.get(pos_item2));
                        startActivity(newActivity1);
                        break;

                    case 3:
                    Intent newActivity2 = new Intent(view.getContext(), DoorInfo3.class);
                        int pos_item3= position - 1;
                    newActivity2.putExtra("key", results.get(pos_item3));
                    startActivity(newActivity2);
                        break;

                }



            }

        });
    }




    //Implementing On click on List Items to open new activity





   /* public void viewAll() {

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
    }*/


}



