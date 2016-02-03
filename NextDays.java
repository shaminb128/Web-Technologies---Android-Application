package com.example.shamin.weatherforecast;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class NextDays extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_next_days);

        TableLayout tab2 = (TableLayout)findViewById(R.id.days7);

        try {
            String result = getIntent().getStringExtra("result");
            String city = getIntent().getStringExtra("city");
            String state = getIntent().getStringExtra("state");
            String temperature_unit = getIntent().getStringExtra("degree");
            Log.d("json", city);
            JSONObject reader = new JSONObject(result);
            JSONObject current = reader.getJSONObject("currently");
            JSONObject hourly = reader.getJSONObject("hourly");
            JSONObject daily = reader.getJSONObject("daily");

            JSONArray dataarray = daily.getJSONArray("data");
            String timezone = reader.getString("timezone");


            Log.d("det", city);
            String tempunit = get_tempunit(temperature_unit);
            Log.d("detter", tempunit);
            int dayvalue=1;
            for(int row = 1; row<=14 && dayvalue<=7; row++)
            {

                TableRow tr = new TableRow(this);

                if(row%2==0)
                    tr.setPadding(0, 0, 0, 20);

                if(row==1||row==2)
                    tr.setBackgroundColor((Color.parseColor("#357CB4")));
                if(row==3||row==4)
                    tr.setBackgroundColor((Color.parseColor("#EB4343")));
                if(row==5||row==6)
                    tr.setBackgroundColor((Color.parseColor("#E68E4F")));
                if(row==7||row==8)
                    tr.setBackgroundColor((Color.parseColor("#A6A338")));
                if(row==9||row==10)
                    tr.setBackgroundColor((Color.parseColor("#9770A7")));
                if(row==11||row==12)
                    tr.setBackgroundColor((Color.parseColor("#F27B7D")));
                if(row==13||row==14)
                    tr.setBackgroundColor((Color.parseColor("#CE4571")));

                JSONObject data = dataarray.getJSONObject(dayvalue);
                for(int column = 0; column<2; column++)
                {


                    if(row%2==1&&column==0) {


                        long gettime = data.getLong("time");
                        String day = get_day(gettime, timezone);
                        String date = get_date(gettime, timezone);
                        String coldisp =day+", "+date;
                        //Log.d("day",day);
                        TextView cell = new TextView(this);
                        cell.setWidth(500);
                        cell.setHeight(50);
                        cell.setGravity(Gravity.CENTER);
                        //cell.setLeft(5);
                        cell.setText(coldisp);
                        cell.setTextColor((Color.parseColor("#000000")));

                        cell.setVisibility(View.VISIBLE);
                        tr.addView(cell);
                    }
                    if(row%2==1&&column==1) {
                        ImageView cell = new ImageView(this);
                        String icon = data.getString("icon");
                        String finalIcon = get_icon(icon);
                        int x = getFlagResource(NextDays.this, finalIcon);
                        cell.setImageResource(x);
                        //cell.setPadding(0,0,20,0);
                        //cell.setMaxWidth(30);
                        //cell.setMaxHeight(30);
                        cell.setVisibility(View.VISIBLE);

                        tr.addView(cell);
                        cell.requestLayout();
                        cell.getLayoutParams().height=80;
                        cell.getLayoutParams().width=80;
                        cell.setPadding(0,20,0,0);


                    }
                    if((row%2==0&&column==0)) {
                        dayvalue++;
                        Integer min = data.getInt("temperatureMin");
                        Integer max = data.getInt("temperatureMax");
                        String disp = "Min: "+min+tempunit+" | Max: "+max+tempunit;
                        TextView cell = new TextView(this);
                        cell.setWidth(350);
                        cell.setHeight(50);
                        cell.setText(disp);
                        //cell.setLeft(10);
                        //cell.setPadding(0,0,0,10);
                        cell.setGravity(Gravity.CENTER);
                        cell.setTextColor((Color.parseColor("#000000")));
                        cell.setVisibility(View.VISIBLE);
                        Log.d("check1", row+"");
                        tr.addView(cell);
                    }
                    if(row==14&&column==0) {
                        Log.d("check","sup");
                    }


                }
                tab2.addView(tr);
                if(row%2==0){
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, 0, 0, 20);
                    tr.setLayoutParams(params);}

            }






        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get_tempunit(String temperature_unit) {
        if (temperature_unit.equalsIgnoreCase("si"))
            return "" + (char) 0x00B0 + "C";
        else
            return "" + (char) 0x00B0 + "F";
    }

    public int getFlagResource(Context context, String name) {
        int resId = context.getResources().getIdentifier(name, "drawable", "com.example.shamin.weatherforecast");
        return resId;
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

    public String get_day(long time, String timezone) {
        Date date = new Date(time * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
    public String get_date(long time, String timezone) {
        Date date = new Date(time * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d");
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
}


