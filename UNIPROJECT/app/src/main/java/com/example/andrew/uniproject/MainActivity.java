package com.example.andrew.uniproject;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign buttons
        Button btnSeeAll = findViewById(R.id.btnSeeAll);
        Button btnPrefs = findViewById(R.id.btnPrefs);
        Button btnSearch = findViewById(R.id.btnSearch);

        //Get button listeners
        impListeners();

        getLoc(FALSE);
    }

    //Class to implement buttons and listeners
    private void impListeners() {

        //Add button listeners
        findViewById(R.id.btnSeeAll).setOnClickListener(this);
        findViewById(R.id.btnPrefs).setOnClickListener(this);
        findViewById(R.id.btnSearch).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnSeeAll) {
            startActivity(new Intent(MainActivity.this, SeeAllActivity.class));
        } else if (v.getId() == R.id.btnPrefs) {
            startActivity(new Intent(MainActivity.this, PreferencesActivity.class));
        } else {
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
        }

    }

    private void getData(String coords) {

        //Define the textview
        final TextView tv = findViewById(R.id.tvMain);

        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);

        //Construct the URL
        String params = "/?exclude=minutely,daily,alerts,flags,currently";
        String location = "/" + coords;
        final String url = "https://api.darksky.net/forecast/76b1650153d418cc29f93e1772252381" + location + params;

        // Create a JSONObj request for the url with params defined above
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    //Handling response
                    public void onResponse(JSONObject response) {

                        // Process the JSON
                        try{
                            // Get the JSON object containing the hourly data
                            JSONObject obj = response.getJSONObject("hourly");

                            // Loop through the data for every hour provided
                            for(int i=0;i <= 48;i++){
                                // Get nested hour array within the hourly object
                                JSONArray hour = obj.getJSONArray("data");

                                // Get the current weather data object
                                JSONObject data = hour.getJSONObject(i);

                                // Extract the info we need from the object
                                String summary = data.getString("summary");
                                String humidity = data.getString("humidity");

                                // Display the formatted json
                                tv.append("Summary: " + summary + ", Humidity: " + humidity);
                                tv.append("\n\n");

                            }
                        //Report error and display to user
                        }catch (JSONException e){
                            e.printStackTrace();
                            tv.setText("Oops! Something went wrong");
                        }
                    }
                },

                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Display error to user
                        tv.setText("Oops! Something went wrong");
                    }
                }
        );

        // Add JsonObjectRequest to the RequestQueue
        queue.add(jsonObjectRequest);
    }

    private void getLoc(boolean all){

        // TODO Change code so these 2 functions work for showing only default loc on mainactivity and all locations on SeeAllActivity

        final TextView tv = findViewById(R.id.tvMain);

        //Create array for storage
        String[] locations = new String[9];
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //Only get primary location if parameter is false
        if(all == FALSE) {
            String loc1 = sharedPref.getString("PrimaryLoc", null);
            getData(loc1);

        //Else get all locations
        } else {

            //Get primary location and store in array
            String loc1 = sharedPref.getString("PrimaryLoc", null);
            tv.append(loc1 + "\n");

            //Loop through remaining 9 locations and append to array
            for(int i=0;i <= 8;i++){
                //Construct the string on each loop
                String location = "loc" + i;
                String key = "Loc" + i;
                String def = "null";

                //Append each preference to the locations array
                location = sharedPref.getString(key, def);
                locations[i] = location;

                //Get data for each location
                getData(locations[i].toString());
                }
        }

    }
}


