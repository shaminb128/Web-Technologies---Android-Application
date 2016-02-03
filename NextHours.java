package com.example.shamin.weatherforecast;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import java.text.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class NextHours extends AppCompatActivity {

    private static Context context;
    TableLayout tab1;
    String result;
    String city;
    String state;
    String temperature_unit;
    JSONObject reader;
    JSONObject current;
    JSONObject hourly;
    JSONArray dataarray;
    JSONObject data;
    String timezone;
    String tempunit;
    TableRow plus;



    public static Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_next_hours);



        /*TextView  tv=new TextView(this);
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER_VERTICAL);

        tv.setText("This Is Tab1 Activity");



        setContentView(tv);*/



        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        /*TableRow tr = new TableRow(this);
        TextView tr1 = new TextView(this);
        tr1.setText("Time");
        tr1.*/
        //TableLayout tab1 = new TableLayout(this);
                tab1 = (TableLayout) findViewById(R.id.hours24);

        try {
            result = getIntent().getStringExtra("result");
            city = getIntent().getStringExtra("city");
            state = getIntent().getStringExtra("state");
            temperature_unit = getIntent().getStringExtra("degree");
            Log.d("json", city);
            reader = new JSONObject(result);
            current = reader.getJSONObject("currently");
            hourly = reader.getJSONObject("hourly");
            dataarray = hourly.getJSONArray("data");

            timezone = reader.getString("timezone");


            Log.d("det", city);
            tempunit = get_tempunit(temperature_unit);
            //tempunit.replace("am","AM").replace("pm","PM");
            Log.d("detter", tempunit);

            for (int row = 1; row <= 24; row++) {
                TableRow rowgroup = new TableRow(this);
                rowgroup.setBottom(10);
                if(row%2==1)
                {
                    rowgroup.setBackgroundColor(Color.LTGRAY);
                }
                data = dataarray.getJSONObject(row);
                for (int column = 0; column < 3; column++) {
                    if (column == 0) {
                        long gettime = data.getLong("time");
                        String time = get_time(gettime, timezone);
                        TextView cell = new TextView(this);
                        cell.setWidth(100);
                        cell.setHeight(100);
                        cell.setGravity(Gravity.CENTER);
                        //cell.setLeft(5);
                        cell.setText(time);
                        cell.setTextColor((Color.parseColor("#000000")));
                        cell.setVisibility(View.VISIBLE);
                        rowgroup.addView(cell);
                    } else if (column == 1) {
                        ImageView cell = new ImageView(this);
                        String icon = data.getString("icon");
                        String finalIcon = get_icon(icon);
                        int x = getFlagResource(NextHours.this, finalIcon);
                        cell.setImageResource(x);
                        //cell.setMaxWidth(30);
                        //cell.setMaxHeight(30);
                        cell.setVisibility(View.VISIBLE);
                        rowgroup.addView(cell);
                        cell.requestLayout();
                        cell.getLayoutParams().height=150;
                        cell.getLayoutParams().width=150;


                    } else {
                        TextView cell = new TextView(this);
                        Integer temperature = data.getInt("temperature");
                        cell.setWidth(100);
                        cell.setHeight(100);
                        cell.setGravity(Gravity.CENTER);
                        cell.setText(temperature + tempunit);
                        cell.setTextColor((Color.parseColor("#000000")));
                        cell.setVisibility(View.VISIBLE);
                        rowgroup.addView(cell);
                    }

                }
                tab1.addView(rowgroup);

            }

            plus = new TableRow(this);

            TextView tv1 = new TextView(this);
            tv1.setWidth(100);
            tv1.setHeight(100);
            tv1.setText("");
            plus.addView(tv1);

            final Button plusbutton = new Button(this);
            plusbutton.setBackgroundColor(Color.parseColor("#357CB4"));
            plusbutton.setTextSize(40);
            plusbutton.setText("+");
            plusbutton.setTextColor(Color.parseColor("#FFFFFF"));
            plusbutton.setWidth(150);
            plusbutton.setHeight(150);

            plus.addView(plusbutton);

            TextView tv2 = new TextView(this);
            tv2.setHeight(100);
            tv2.setWidth(100);
            tv2.setText("");
            plus.addView(tv2);

            tab1.addView(plus);

            setContext(this);

            plusbutton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    tab1.removeView(plus);
                    for(int row=25; row<=48; row++) {
                        TableRow tr = new TableRow(getContext());
                        if(row%2==1)
                            tr.setBackgroundColor(Color.LTGRAY);
                        try {
                            data = dataarray.getJSONObject(row);
                            for (int column = 0; column < 3; column++) {
                                if (column == 0) {
                                    long gettime = data.getLong("time");
                                    String time = get_time(gettime, timezone);
                                    TextView cell = new TextView(getContext());
                                    cell.setWidth(100);
                                    cell.setHeight(100);
                                    cell.setGravity(Gravity.CENTER);
                                    //cell.setLeft(5);
                                    cell.setText(time);
                                    cell.setTextColor((Color.parseColor("#000000")));
                                    cell.setVisibility(View.VISIBLE);
                                    tr.addView(cell);
                                } else if (column == 1) {
                                    ImageView cell = new ImageView(getContext());
                                    String icon = data.getString("icon");
                                    String finalIcon = get_icon(icon);
                                    int x = getFlagResource(NextHours.this, finalIcon);
                                    cell.setImageResource(x);
                                    //cell.setMaxWidth(30);
                                    //cell.setMaxHeight(30);
                                    cell.setVisibility(View.VISIBLE);
                                    tr.addView(cell);
                                    cell.requestLayout();
                                    cell.getLayoutParams().height = 150;
                                    cell.getLayoutParams().width = 150;


                                } else {
                                    TextView cell = new TextView(getContext());
                                    Integer temperature = data.getInt("temperature");
                                    cell.setWidth(100);
                                    cell.setHeight(100);
                                    cell.setGravity(Gravity.CENTER);
                                    cell.setText(temperature + tempunit);
                                    cell.setTextColor((Color.parseColor("#000000")));
                                    cell.setVisibility(View.VISIBLE);
                                    tr.addView(cell);
                                }


                            }

                            tab1.addView(tr);

                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                }
            });




        } catch (Exception e) {
            e.printStackTrace();


    }}




    public int getFlagResource(Context context, String name) {
        int resId = context.getResources().getIdentifier(name, "drawable", "com.example.shamin.weatherforecast");
        return resId;
    }

    public String get_time(long time, String timezone) {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        calendar.setTimeInMillis(time*1000);
        String formattedDate = sdf.format(calendar.getTime());
        //Log.d("a","b");
        return formattedDate.toUpperCase();
    }

    public String get_tempunit(String temperature_unit) {
        if (temperature_unit.equalsIgnoreCase("si"))
            return "" + (char) 0x00B0 + "C";
        else
            return "" + (char) 0x00B0 + "F";
    }

    public String get_icon(String icon) {
        if (icon.equalsIgnoreCase("clear-day"))
            return "clear";
        else if (icon.equalsIgnoreCase("clear-night"))
            return "clear_night";
        else if (icon.equalsIgnoreCase("rain"))
            return "rain";
        else if (icon.equalsIgnoreCase("snow"))
            return "snow";
        else if (icon.equalsIgnoreCase("sleet"))
            return "sleet";
        else if (icon.equalsIgnoreCase("wind"))
            return "wind";
        else if (icon.equalsIgnoreCase("fog"))
            return "fog";
        else if (icon.equalsIgnoreCase("cloudy"))
            return "cloudy";
        else if (icon.equalsIgnoreCase("partly-cloudy-day"))
            return "cloud_day";
        else if (icon.equalsIgnoreCase("partly-cloudy-night"))
            return "cloud_night";
        else return null;

    }
    }





