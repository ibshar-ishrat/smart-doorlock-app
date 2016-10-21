package com.example.sadi.smartdoorapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ibshar on 29/05/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "db_DOOR_LOCK_PHONE.DB";

    public DatabaseHelper(Context context) {

        super(context,DATABASE_NAME,null,1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE PASSWORDS ( Password_ID INTEGER PRIMARY KEY AUTOINCREMENT, Username TEXT NOT NULL, Email_Address TEXT NOT NULL, Alt_Email_Address TEXT NOT NULL, PW TEXT NOT NULL, UNIQUE(Username, Email_Address, Alt_Email_Address) );");

        db.execSQL("CREATE TABLE SECURITY_QUESTION ( Sec_Ques_ID INTEGER PRIMARY KEY AUTOINCREMENT, Question_1 TEXT NOT NULL, Question_2 TEXT NOT NULL, Answer_1 TEXT NOT NULL, Answer_2 TEXT NOT NULL  );");

        db.execSQL("CREATE TABLE USER ( User_ID INTEGER PRIMARY KEY AUTOINCREMENT, FName TEXT NOT NULL, LName TEXT NOT NULL, Image NONE, City TEXT NOT NULL, Country TEXT NOT NULL, Privilege INTEGER NOT NULL DEFAULT 1, Num_of_Doors INTEGER NOT NULL DEFAULT 1, Password_ID INTEGER NOT NULL, Sec_Ques_ID INTEGER NOT NULL, FOREIGN KEY(Password_ID) REFERENCES PASSWORDS(Password_ID), FOREIGN KEY(Sec_Ques_ID) REFERENCES SECURITY_QUESTION(Sec_Ques_ID), UNIQUE(Password_ID, Sec_Ques_ID), CHECK(Privilege IN (1,2)) );");

        db.execSQL("CREATE TABLE PHONE ( Phone_ID INTEGER PRIMARY KEY AUTOINCREMENT, MAC_Address TEXT NOT NULL UNIQUE, IP_Address_Network TEXT NOT NULL, IP_Address_Device TEXT NOT NULL, User_ID INTEGER NOT NULL, FOREIGN KEY(User_ID) REFERENCES USER(User_ID) );");

        db.execSQL("CREATE TABLE DEVICE_NAME ( Name_ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT NOT NULL, Description TEXT, PIN_Code INTEGER NOT NULL, User_ID INTEGER NOT NULL, FOREIGN KEY(User_ID) REFERENCES USER(User_ID) );");

        db.execSQL("CREATE TABLE DEVICE ( Device_ID INTEGER PRIMARY KEY AUTOINCREMENT, MAC_Address TEXT NOT NULL, IP_Address_Network TEXT NOT NULL, IP_Address_Device TEXT NOT NULL, Device_Status TEXT NOT NULL DEFAULT 'Enable', Door_Status TEXT NOT NULL DEFAULT 'Close', Phone_ID INTEGER NOT NULL, Device_Name_ID INTEGER NOT NULL, FOREIGN KEY(Phone_ID) REFERENCES PHONE(Phone_ID), FOREIGN KEY(Device_Name_ID) REFERENCES DEVICE_NAME(Device_Name_ID), UNIQUE (MAC_Address, Device_Name_ID), CHECK(Door_Status IN('Close', 'Open')), CHECK(Device_Status IN('Enable', 'Disable')) );");

        db.execSQL("CREATE TABLE PORT ( Port_ID INTEGER PRIMARY KEY AUTOINCREMENT, Port_Color INTEGER UNIQUE );");

        db.execSQL("CREATE TABLE FEATURE ( Feature_ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT NOT NULL, Status TEXT NOT NULL DEFAULT 'Absent', Port_ID INTEGER NOT NULL, FOREIGN KEY(Port_ID) REFERENCES PORT(Port_ID), UNIQUE (Name, Port_ID), CHECK(Name IN('Camera', 'Bell Ring', 'Door Knock', 'Lock Tamper', 'Human Detector')), CHECK(Status IN('Present', 'Absent')) );");

        db.execSQL("CREATE TABLE Device_Feature ( DF_ID INTEGER PRIMARY KEY AUTOINCREMENT, Device_ID INTEGER NOT NULL, Feature_ID INTEGER NOT NULL, FOREIGN KEY(Device_ID) REFERENCES DEVICE(Device_ID), FOREIGN KEY(Feature_ID) REFERENCES FEATURE(Feature_ID) );");

        db.execSQL("CREATE TABLE ACTION ( Action_ID INTEGER PRIMARY KEY AUTOINCREMENT, Action_Name TEXT NOT NULL, Time_Marked NUMERIC NOT NULL UNIQUE, Username TEXT NOT NULL, Phone_ID INTEGER NOT NULL, Device_ID INTEGER NOT NULL, FOREIGN KEY(Phone_ID) REFERENCES PHONE(Phone_ID), FOREIGN KEY(Device_ID) REFERENCES DEVICE(Device_ID), CHECK(Action_ID IN('Unlock', 'Lock','Ignore')) );");

        db.execSQL("CREATE TABLE ACTIVITY ( Activity_ID INTEGER PRIMARY KEY AUTOINCREMENT, Activity_Name TEXT NOT NULL, Visitor_Image NONE NOT NULL, Time_Marked NUMERIC NOT NULL UNIQUE, Action_ID INTEGER, Device_ID INTEGER NOT NULL, FOREIGN KEY(Action_ID) REFERENCES ACTION(Action_ID), FOREIGN KEY(Device_ID) REFERENCES DEVICE(Device_ID), CHECK(Activity_Name IN('Door Bell Ringed', 'Door Knock', 'Door Tampered', 'Human Detected')) );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS USER");
        onCreate(db);
    }
}
