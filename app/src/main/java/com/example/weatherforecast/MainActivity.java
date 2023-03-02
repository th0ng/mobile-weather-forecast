package com.example.weatherforecast;

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
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create a new web request queue
        queue = Volley.newRequestQueue(this);
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
            String description;
            double temperature;
            double windSpeed;

            description = weatherResponse.getJSONArray("weather").getJSONObject(0).getString("description");
            temperature = weatherResponse.getJSONObject("main").getDouble("temp");
            windSpeed = weatherResponse.getJSONObject("wind").getDouble("speed");


            //write the value on the UI
            TextView descriptionTextView = findViewById(R.id.descriptionTextView);
            descriptionTextView.setText(description);

            TextView temperatureTextView = findViewById(R.id.temperatureTextView);
            temperatureTextView.setText((int) temperature);

            TextView windTextView = findViewById(R.id.windTextView);
            windTextView.setText((int) windSpeed);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}