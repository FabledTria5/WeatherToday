package com.example.weathertoday;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

import static com.example.weathertoday.MainActivity.APP_PREFERENCES;
import static com.example.weathertoday.MainActivity.APP_PREFERENCES_THEME;
import static com.example.weathertoday.MainActivity.APP_PREFERENCES_UNITS;

public class MySettings {

    private final static SharedPreferences preferences = MyApp.getContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

    public static String getTempPostfix() {

        String[] weatherPostfixes = MyApp.getContext().getResources().getStringArray(R.array.temperature_units);
        String unitsName = preferences.getString(APP_PREFERENCES_UNITS, "metric");

        switch (unitsName) {
            case "imperial":
                return weatherPostfixes[1];
            case "standard":
                return weatherPostfixes[2];
            default:
                return weatherPostfixes[0];
        }
    }

    public static void setLanguage() {

    }

    public static void setTheme() {

    }
}

