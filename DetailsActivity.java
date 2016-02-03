package com.example.shamin.weatherforecast;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DetailsActivity extends TabActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_details);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        TextView tvheading = (TextView) findViewById(R.id.heading);
        //TextView tvtempheader = (TextView) findViewById(R.id.tempheader);

        String result = getIntent().getStringExtra("result");
        String city = getIntent().getStringExtra("city");
        String state = getIntent().getStringExtra("state");
        String temperature_unit = getIntent().getStringExtra("degree");
        String tempunit = get_tempunit(temperature_unit);

        tvheading.setText("More Details for " + city + ", " + state);
        //tvtempheader.setText("Temp(" + tempunit + ")");






        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

        TabHost.TabSpec addNextHoursTabSpec = tabHost.newTabSpec("addNextHours");
        TabHost.TabSpec addNextDaysTabSpec = tabHost.newTabSpec("addNextDays");

        Intent Next24Intent = new Intent(DetailsActivity.this, NextHours.class);
        try {

            //JSONObject reader = new JSONObject(result);
            Next24Intent.putExtra("city", city);
            Next24Intent.putExtra("state",state);
            Next24Intent.putExtra("result",result);
            Next24Intent.putExtra("degree",temperature_unit);


            // startActivity(i);
        }catch(Exception e) {
            e.printStackTrace();
        }
        addNextHoursTabSpec.setIndicator("Next 24 Hours");
        addNextHoursTabSpec.setContent(Next24Intent);

        Intent Next7Intent = new Intent(DetailsActivity.this, NextDays.class);
        try {

            //JSONObject reader = new JSONObject(result);
            Next7Intent.putExtra("city", city);
            Next7Intent.putExtra("state",state);
            Next7Intent.putExtra("result",result);
            Next7Intent.putExtra("degree",temperature_unit);


            // startActivity(i);
        }catch(Exception e) {
            e.printStackTrace();
        }
        addNextDaysTabSpec.setIndicator("Next 7 Days");
        addNextDaysTabSpec.setContent(Next7Intent);



        tabHost.addTab(addNextHoursTabSpec);
        tabHost.addTab(addNextDaysTabSpec);

    }

   public String get_tempunit(String temperature_unit) {
        if (temperature_unit.equalsIgnoreCase("si"))
            return "" + (char) 0x00B0 + "C";
        else
            return "" + (char) 0x00B0 + "F";
    }

}
