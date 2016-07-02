package com.example.sadi.smartdoorapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Sadia Sami on 7/2/2016.
 */
public class DatePickerFragment_Signup extends DialogFragment
        implements DatePickerDialog.OnDateSetListener  {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = (c.get(Calendar.MONTH)+1);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year,month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        //Setting and Displaying date set by user in textView
                TextView tv2 = (TextView) getActivity().findViewById(R.id.textView_PickDate_registration);
                tv2.setText("Year: " + view.getYear() + " Month: " + view.getMonth() + " Day: " + view.getDayOfMonth());


    }
}
