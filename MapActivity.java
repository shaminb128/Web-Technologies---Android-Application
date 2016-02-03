package com.example.shamin.weatherforecast;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;



import org.json.JSONObject;

/**
 * Created by Shamin on 12/9/2015.
 */
public class MapActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        try {
                String result = getIntent().getStringExtra("result");
                JSONObject reader = new JSONObject(result);
            String lat = reader.getString("latitude");
            String lng = reader.getString("longitude");
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            MapFragment fragment = new MapFragment();

            Bundle bundle = new Bundle();
            bundle.putString("lat", lat);
            bundle.putString("lng", lng);

            fragment.setArguments(bundle);

            fragmentTransaction.add(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }






    }}
