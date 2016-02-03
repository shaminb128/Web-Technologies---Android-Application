package com.example.shamin.weatherforecast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.net.Uri;
import android.view.View;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;
import android.widget.RadioGroup;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private static String urlString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        addListenerOnButton();
        street = (EditText) findViewById(R.id.street);
        city = (EditText) findViewById(R.id.city);
        submit = (Button) findViewById(R.id.submit);
        state = (Spinner) findViewById(R.id.state);
        error = (TextView) findViewById(R.id.error);
        degreeGroup = (RadioGroup) findViewById(R.id.degree);
        us = (RadioButton) findViewById(R.id.us);
        si = (RadioButton) findViewById(R.id.si);


        //degree = (RadioGroup) findViewById(R.id.degree);
    }

    public void onButtonClick(View v) {

            Intent i = new Intent(MainActivity.this, Display.class);
            startActivity(i);

    }

    public void onButtonClick1(View v){
        if (v.getId() == R.id.submit) {
            if (!validate()) {

                if(si.isChecked())
                    degree="si";
                else
                    degree = "us";


                Log.d("pppp", degree);
                // Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
                // sendPostRequest(street, city, state, degree);
                //submit.setOnClickListener(new View.OnClickListener() {
                    //public void onClick(View v) {
                        lstreet=street.getText().toString().trim().replaceAll(" ","%20");
                        lcity=city.getText().toString().trim().replaceAll(" ","%20");
                        lstate=state.getSelectedItem().toString().replaceAll(" ","%20");
                        urlString = "http://cs-server.usc.edu:13083/HW8/sendjson.php?street="+lstreet+"&city="+lcity+"&state="+lstate+"&degree="+degree;
                        Log.d("check",urlString);
                        new ProcessJSON().execute(urlString);
                    //}
                //});
            }

        }
    }

    ImageButton imageButton;

    public void addListenerOnButton() {
        imageButton = (ImageButton) findViewById(R.id.logo);
        imageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this, "http://forecast.io/", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://forecast.io/"));
                startActivity(intent);
            }
        });
    }

    EditText street, city;
    Button submit;
    TextView error;
    Spinner state;

    RadioGroup degreeGroup;
    RadioButton us;
    RadioButton si;
    String degree;
    String lstreet;
    String lcity;
    String lstate;


    public boolean validate() {


        int x = 1;
        if (street.getText().toString().trim().length() <= 0) {
            // Toast t1 = Toast.makeText(MainActivity.this, "Please enter a street address", Toast.LENGTH_LONG);
            // t1.setGravity(Gravity.LEFT | Gravity.BOTTOM, 250, 350);
            // t1.show();
            error.setText("Please enter a street address");
            //validate();
            x = 0;
        } else if (city.getText().toString().trim().length() <= 0) {
            // Toast t2 = Toast.makeText(MainActivity.this, "Please enter a city", Toast.LENGTH_LONG);
            // t2.setGravity(Gravity.LEFT | Gravity.BOTTOM, 250, 350);
            // t2.show();
            error.setText("Please enter a city");
            //validate();
            x = 0;
        } else if (state.getSelectedItem().toString().equals("Select")) {
            // Toast t3 = Toast.makeText(MainActivity.this, "Please select a state", Toast.LENGTH_LONG);
            // t3.setGravity(Gravity.LEFT | Gravity.BOTTOM, 250, 350);
            // t3.show();
            error.setText("Please select a state");
            //validate();
            x = 0;
        }
        if(x==0)
        {
            return true;
        }
        else {
            return false;
        }
    }


    private class ProcessJSON extends AsyncTask<String, Void, String>{
        protected String doInBackground(String... strings){
            String stream = null;
            String urlString = strings[0];

            HTTPDataHandler hh = new HTTPDataHandler();
            stream = hh.GetHTTPData(urlString);


            return stream;
        }

        protected void onPostExecute(String stream){
           // TextView tv = (TextView) findViewById(R.id.tv);
            Log.d("Debug", "Success");
            if(stream != null){
                try{
                    Log.d("str",stream);
                    //JSONObject reader = new JSONObject(stream);
                    Intent new_Intent = new Intent(MainActivity.this, ResultActivity.class);
                    new_Intent.putExtra("result", stream);
                   // new_Intent.putExtra("street", street.getText().toString().trim());
                    new_Intent.putExtra("city", city.getText().toString().trim());
                    new_Intent.putExtra("state", state.getSelectedItem().toString());
                    new_Intent.putExtra("degree", degree);

                    startActivity(new_Intent);


                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            //else if(stream.equalsIgnoreCase(null)||stream.equalsIgnoreCase(""))
             // Toast.makeText(MainActivity.this, "Bogus Values Entered", Toast.LENGTH_LONG).show();

        }
    }


    public void onClickClear(View v)
    {
        street.setText("");
        city.setText("");
        state.setSelection(0);
        us.setChecked(true);
        si.setChecked(false);
        error.setText("");
    }
}
