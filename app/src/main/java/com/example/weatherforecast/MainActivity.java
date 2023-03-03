package com.example.weatherforecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private RequestQueue queue;
    private int mCounter = 0;
    private String description = "Click refresh button to get the weather data";
    private double temperature = 0;
    private double windSpeed = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create a new web request queue
        queue = Volley.newRequestQueue(this);

        //write the value on the UI
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    //This is an automatic callback from the system
    //You can save your UI state here: description, temperature and wind
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("WEATHER_DESCRIPTION", description);
        outState.putDouble("TEMPERATURE", temperature);
        outState.putDouble("WIND_SPEED", windSpeed);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //check if there's a bundle and activity ui saved
        //if so read the value from there

        if (savedInstanceState != null) {
            description = savedInstanceState.getString("WEATHER_DESCRIPTION");
            if (description == null) {
                description = "Click refresh button to get the weather data";
            }
            temperature = savedInstanceState.getDouble("TEMPERATURE", 0);
            windSpeed = savedInstanceState.getDouble("WIND_SPEED", 0 );
        }

        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(description);

        TextView temperatureTextView = findViewById(R.id.temperatureTextView);
        temperatureTextView.setText("" + temperature +" C");

        TextView windTextView = findViewById(R.id.windTextView);
        windTextView.setText("" + windSpeed + " m/s");
    }

    public void fetchWeatherData(View view) {
        // Fetch weather data from url:
        String url = "https://api.openweathermap.org/data/2.5/weather?q=tampere&units=metric&appid=6c433438776b5be4ac86001dc88de74d";
        // Make the request and put it in the queue to get the response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    //The response is here as a string
                    Log.d("WEATHER_APP", response);
                    parseJsonAndUpdateUI(response);
                }, error -> {
                    //Error (timeout, other errors)
                    Log.d("WEATHER_APP", error.toString());
                });
        //Add the request to queue
        queue.add(stringRequest);
    }

    private void parseJsonAndUpdateUI(String response) {
        try {
            JSONObject weatherResponse = new JSONObject(response);

            description = weatherResponse.getJSONArray("weather").getJSONObject(0).getString("description");
            temperature = weatherResponse.getJSONObject("main").getDouble("temp");
            windSpeed = weatherResponse.getJSONObject("wind").getDouble("speed");

            TextView descriptionTextView = findViewById(R.id.descriptionTextView);
            descriptionTextView.setText(description);

            TextView temperatureTextView = findViewById(R.id.temperatureTextView);
            temperatureTextView.setText("" + temperature +" C");

            TextView windTextView = findViewById(R.id.windTextView);
            windTextView.setText("" + windSpeed + " m/s");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}