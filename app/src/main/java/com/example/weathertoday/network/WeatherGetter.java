package com.example.weathertoday.network;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.weathertoday.fragments.MainFragment;
import com.example.weathertoday.network.model.WeatherRequest;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class WeatherGetter {

    private static final String REQUEST_FIRST_PART = "https://api.openweathermap.org/data/2.5/weather?q=";
    private static final String UNITS = "&units=metric";
    private static final String LOCALE = "&lang=ru";
    private static final String API_KEY = "&appid=f5f6ea48efb1914ff6e0b2857c54af1d";

    public static void getWeather(String city, MainFragment parent) {
        try {
            URL url = new URL(REQUEST_FIRST_PART + city + UNITS + LOCALE + API_KEY);
            Handler handler = new Handler(Looper.getMainLooper());
            new Thread(() -> {
                HttpsURLConnection urlConnection;
                try {
                    urlConnection = (HttpsURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setReadTimeout(1000);
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String result = getLines(in);

                    Log.d("123", result);
                    Gson gson = new Gson();
                    WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
                    handler.post(() -> parent.showWeather(weatherRequest));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static String getLines(BufferedReader in) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return in.lines().collect(Collectors.joining("\n"));
        }
        return null;
    }
}

