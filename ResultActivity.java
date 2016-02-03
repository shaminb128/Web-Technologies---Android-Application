package com.example.shamin.weatherforecast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class ResultActivity extends AppCompatActivity {

    //TextView info;
    ImageButton fbbutton;
    //LoginButton fb;
    ShareDialog shareDialog;
    JSONObject reader;
    JSONObject current;
    JSONObject daily;
    String result;
    String summary;
    Integer temperature;
    String city;
    String icon;
    String finalIcon;
    String temperature_unit;
    String tempunit;
    Context c;
    private CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //info = (TextView)findViewById(R.id.fbdata);
        //fb = (LoginButton) findViewById(R.id.fb);
        fbbutton = (ImageButton)findViewById(R.id.fb_button);
        shareDialog = new ShareDialog(this);
        c=this;
        fbbutton.setOnClickListener(new View.OnClickListener(){
            String description = null;
            String json_icon = null;
            String img_name = "http://cs-server.usc.edu:45678/hw/hw8/images/";

            @Override
            public void onClick(View v){
                if(ShareDialog.canShow(ShareLinkContent.class)) {
                    Intent intent1 = getIntent();
                    Bundle bd1 = intent1.getExtras();

                    try {
                        reader = new JSONObject(result);
                        current = reader.getJSONObject("currently");
                        daily = reader.getJSONObject("daily");
                        summary = current.getString("summary");
                        temperature = current.getInt("temperature");
                        city = getIntent().getStringExtra("city");
                        icon = current.getString("icon");
                        finalIcon = get_icon(icon);
                        temperature_unit = getIntent().getStringExtra("degree");
                        tempunit = get_tempunit(temperature_unit);
                        description = summary + "," + temperature;
                        img_name = img_name + finalIcon + ".png";
                        Log.d("link", img_name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Current Weather in " + city)
                            .setContentDescription(description)
                            .setImageUrl(Uri.parse(img_name))
                            .setContentUrl(Uri.parse("http://forecast.io"))
                            .build();
                        Log.d("hhh","gghh");
                    shareDialog = new ShareDialog((Activity) c);
                    shareDialog.show(linkContent);

                }

                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(c, "Success", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(c, "Post Unsuccessful", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        error.printStackTrace();
                    }
                    });


                }
            });


        TextView tvsummary = (TextView) findViewById(R.id.summary);
        ImageView ivicon = (ImageView) findViewById(R.id.icon);
        TextView tvtemperature = (TextView) findViewById(R.id.temperature);
        TextView tvlh = (TextView) findViewById(R.id.lh);
        TextView tvprecipitation = (TextView) findViewById(R.id.precipitation);
        TextView tvrain = (TextView) findViewById(R.id.rain);
        TextView tvwindspeed = (TextView) findViewById(R.id.windspeed);
        TextView tvdewpoint = (TextView) findViewById(R.id.dewpoint);
        TextView tvhumidity = (TextView) findViewById(R.id.humidity);
        TextView tvvisibility = (TextView) findViewById(R.id.visibility);
        TextView tvsunrise = (TextView) findViewById(R.id.sunrise);
        TextView tvsunset = (TextView) findViewById(R.id.sunset);
        TextView tvtemperatureunit = (TextView) findViewById(R.id.temperatureunit);

        try {

            result = getIntent().getStringExtra("result");
            if(result.equalsIgnoreCase(null)||result.equalsIgnoreCase(""))
                Toast.makeText(ResultActivity.this, "Bogus Values Entered", Toast.LENGTH_LONG).show();
            city = getIntent().getStringExtra("city");
            String state = getIntent().getStringExtra("state");
            temperature_unit = getIntent().getStringExtra("degree");
            reader = new JSONObject(result);
            current = reader.getJSONObject("currently");
            daily = reader.getJSONObject("daily");
            String timezone = reader.getString("timezone");
            Log.d("zone", timezone);
            JSONArray dataarray = daily.getJSONArray("data");
            JSONObject data = dataarray.getJSONObject(0);
            Log.d("temp", temperature_unit);
            icon = current.getString("icon");
            summary = current.getString("summary");
            temperature = current.getInt("temperature");
            Integer lowtemperature = data.getInt("temperatureMin");
            Integer hightemperature = data.getInt("temperatureMax");
            //Log.d("temo", lowtemperature);
            Double precipitation = current.getDouble("precipIntensity");
            Integer rain = current.getInt("precipProbability");
            Double windspeed = current.getDouble("windSpeed");
            Integer dewpoint = current.getInt("dewPoint");
            Integer humidity = current.getInt("humidity");
            int visibility = current.getInt("visibility");
            String vischeck = String.valueOf(visibility)+"";
            long sunrise = data.getLong("sunriseTime");
            long sunset = data.getLong("sunsetTime");


            //Log.d("heyy", sunrise);

            tvsummary.setText(summary + " in " + city + ", " + state);

            finalIcon = get_icon(icon);
            Log.d("hello", finalIcon);

            int x = getFlagResource(ResultActivity.this, finalIcon);

            ivicon.setImageResource(x);
            tempunit = get_tempunit(temperature_unit);
            Log.d("yello", tempunit);
            tvtemperature.setText("" + temperature);
            tvtemperatureunit.setText(tempunit);
            tvlh.setText("L:" + lowtemperature + (char) 0x00B0 + "|H:" + hightemperature + (char) 0x00B0);
            String precipvalue = get_precipitation(precipitation, temperature_unit);
            tvprecipitation.setText(precipvalue);
            tvrain.setText(rain + "%");
            String speedunit = get_speedunit(temperature_unit);
            tvwindspeed.setText(windspeed + speedunit);
            Log.d("sup", speedunit);
            tvdewpoint.setText(dewpoint + tempunit);
            tvhumidity.setText(humidity + "%");
            String visibilityunit = get_visibilityunit(temperature_unit);
            tvvisibility.setText(visibility + visibilityunit);

            String sunrisetime = get_time(sunrise,timezone);


            Log.d("time", sunrisetime);
            tvsunrise.setText(sunrisetime);
            final String sunsettime = get_time(sunset,timezone);

            tvsunset.setText(sunsettime);




        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public int getFlagResource(Context context, String name) {
        int resId = context.getResources().getIdentifier(name, "drawable", "com.example.shamin.weatherforecast");
        return resId;
    }

    public String get_time(long time, String timezone) {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        calendar.setTimeInMillis(time * 1000);
        String formattedDate = sdf.format(calendar.getTime());
        Log.d("a","b");
        return formattedDate.toUpperCase();
    }

    public String get_visibilityunit(String formdegree) {
        if(formdegree.equalsIgnoreCase("us"))
            return "mi";
        else
            return "km";
    }
    public String get_precipitation(Double precipitation, String formdegree) {
        if (formdegree.equalsIgnoreCase("us")) {
            if (precipitation >= 0 && precipitation < 0.002)
                return "None";
            else if (precipitation >= 0.002 && precipitation < 0.017)
                return "Very Light";
            else if (precipitation >= 0.017 && precipitation < 0.1)
                return "Light";
            else if (precipitation >= 0.1 && precipitation < 0.4)
                return "Moderate";
            else if (precipitation >= 0.4)
                return "Heavy";
            else return null;
        } else {
            if (precipitation >= 0 && precipitation < 0.0508)
                return "None";
            else if (precipitation >= 0.0508 && precipitation < 0.4318)
                return "Very Light";
            else if (precipitation >= 0.4318 && precipitation < 2.54)
                return "Light";
            else if (precipitation >= 2.54 && precipitation < 10.16)
                return "Moderate";
            else if (precipitation >= 10.16)
                return "Heavy";
            else return null;
        }

    }

    public String get_speedunit(String formdegree) {
        if(formdegree.equalsIgnoreCase("us"))
            return "mph";
        else
            return "m/s";
    }
    public String get_tempunit(String temperature_unit) {
        if(temperature_unit.equalsIgnoreCase("si"))
            return ""+(char) 0x00B0+"C";
        else
            return ""+(char) 0x00B0+"F";
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



    public void onClickDetails(View v) {
        if (v.getId() == R.id.details) {
            Intent i = new Intent(ResultActivity.this, DetailsActivity.class);

            try {
                String result = getIntent().getStringExtra("result");
                String city = getIntent().getStringExtra("city");
                String state = getIntent().getStringExtra("state");
                String temperature_unit = getIntent().getStringExtra("degree");

                JSONObject reader = new JSONObject(result);
                i.putExtra("city",city);
                i.putExtra("state",state);
                i.putExtra("result",result);
                i.putExtra("degree",temperature_unit);


                startActivity(i);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onClickMap(View v) {
        if(v.getId() == R.id.map) {
            Intent i = new Intent(ResultActivity.this, MapActivity.class);

            try {
                String result = getIntent().getStringExtra("result");
                String city = getIntent().getStringExtra("city");
                String state = getIntent().getStringExtra("state");
                String temperature_unit = getIntent().getStringExtra("degree");

                JSONObject reader = new JSONObject(result);
                i.putExtra("city",city);
                i.putExtra("state",state);
                i.putExtra("result",result);
                i.putExtra("degree",temperature_unit);


                startActivity(i);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
