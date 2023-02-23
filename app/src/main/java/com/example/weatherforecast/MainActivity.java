package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
        //ADd the request to queue
        queue.add(stringRequest);
    }

    private void parseJsonAndUpdateUI(String response) {
        try {
            JSONObject weatherResponse = new JSONObject(response)
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}