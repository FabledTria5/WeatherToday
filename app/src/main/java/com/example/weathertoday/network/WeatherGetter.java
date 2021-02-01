package com.example.weathertoday.network;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import com.example.weathertoday.BuildConfig;
import com.example.weathertoday.MainActivity;
import com.example.weathertoday.MySettings;
import com.example.weathertoday.fragments.MainFragment;
import com.example.weathertoday.network.model.WeatherList;
import com.example.weathertoday.network.model.WeatherRequest;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class WeatherGetter {

    private static final String REQUEST_FIRST_PART = "https://api.openweathermap.org/data/2.5/weather?q=";
    private static final String MULTIPLE_REQUEST_FIRST_PART = "https://api.openweathermap.org/data/2.5/forecast?q=";
    private static final String UNITS = "&units=";
    private static final String LOCALE = "&lang=";
    private static final String API_KEY_PREFIX = "&appid=";
    private static final String FORECAST_DAYS_COUNT = "&cnt=12";

    private static final Gson gson = new Gson();

    public static void getWeather(String city, MainActivity parent, String unitsName) {
        try {
            String language = (Locale.getDefault().getLanguage().equals("en")) ? "en" : "ru";
            city = formatCityName(city);
            URL url = new URL((REQUEST_FIRST_PART + city + UNITS + unitsName + LOCALE + language + API_KEY_PREFIX + BuildConfig.WEATHER_API_KEY).replaceAll(" ", ""));
            Handler handler = new Handler(Looper.getMainLooper());
            new Thread(() -> {
                HttpsURLConnection urlConnection;
                try {
                    urlConnection = (HttpsURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setReadTimeout(1000);
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String result = getLines(in);

                    WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);

                    handler.post(() -> parent.setupNavHeader(weatherRequest));
                    urlConnection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void getWeather(String city, MainFragment parent, String unitsName) {
        try {
            String language = (Locale.getDefault().getLanguage().equals("en")) ? "en" : "ru";
            city = formatCityName(city);
            URL url = new URL((REQUEST_FIRST_PART + city + UNITS + unitsName + LOCALE + language + API_KEY_PREFIX + BuildConfig.WEATHER_API_KEY));
            Handler handler = new Handler(Looper.getMainLooper());
            new Thread(() -> {
                HttpsURLConnection urlConnection;
                try {
                    urlConnection = (HttpsURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setReadTimeout(1000);
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String result = getLines(in);

                    WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
                    handler.post(() -> parent.showWeather(weatherRequest));
                    urlConnection.disconnect();
                } catch (IOException e) {
                    handler.post(parent::showError);
                }
            }).start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void getWeatherForecast(String city, MainFragment parent, String unitsName) {
        try {
            String language = (Locale.getDefault().getLanguage().equals("en")) ? "en" : "ru";
            city = formatCityName(city);
            URL url = new URL(MULTIPLE_REQUEST_FIRST_PART + city + UNITS + unitsName + LOCALE + language + FORECAST_DAYS_COUNT + API_KEY_PREFIX + BuildConfig.WEATHER_API_KEY);
            Handler handler = new Handler(Looper.getMainLooper());
            new Thread(() -> {
                HttpsURLConnection urlConnection;
                try {
                    urlConnection = (HttpsURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setReadTimeout(10000);
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String result = getLines(in);

                    WeatherList weatherList = gson.fromJson(result, WeatherList.class);
                    handler.post(() -> parent.initRecyclerView(new ArrayList<>(Arrays.asList(weatherList.getList()))));
                    urlConnection.disconnect();
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

    private static String formatCityName(String cityName) {
        if (cityName.endsWith(" ")) {
            cityName = cityName.substring(0, cityName.length() - 1);
        }
        return cityName.replace(" ", "-");
    }
}

