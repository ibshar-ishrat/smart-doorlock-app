package com.example.sadi.smartdoorapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Sadia Sami on 6/21/2016.
 */
public class Fragment_Tab_Door_Status extends Fragment {
    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.door_status,container,false);
        return rootview;
    }


}
