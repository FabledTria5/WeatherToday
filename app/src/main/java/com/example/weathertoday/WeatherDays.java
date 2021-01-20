package com.example.weathertoday;

import android.app.Activity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class WeatherDays implements Serializable {

    private String weatherStatus;
    private String temperature;
    private String moisture;
    private String pressure;
    private String windSpeed;
    private String dayOfWeek;

    private static ArrayList<String> days;

    public static ArrayList<WeatherDays> getDays(int value, Activity parent) {

        ArrayList<WeatherDays> arrayList = new ArrayList<>();
        days = new ArrayList<>(Arrays.asList(parent.getResources().getStringArray(R.array.days_of_week)));
        new SimpleDateFormat("EEEE", Locale.ENGLISH).format(Calendar.getInstance().getTime());

        int shift = 0;

        for (int i = 0; i < value; i++) {
            WeatherDays day = new WeatherDays();
            day.generateData(shift, parent);
            arrayList.add(day);
            if (day.dayOfWeek.equals(days.get(days.size() - 1))) {
                String rawString = new SimpleDateFormat("EEEE", Locale.forLanguageTag(Locale.getDefault().getLanguage())).format(Calendar.getInstance().getTime());
                String currentDay = rawString.substring(0, 1).toUpperCase() + rawString.substring(1);
                shift = -days.indexOf(currentDay);
                continue;
            }
            shift++;
        }
        return arrayList;
    }

    private void generateData(int shift, Activity parent) {
        dayOfWeek = getDayName(shift);
        weatherStatus = parent.getResources().getString(R.string.weather_status_text);
        temperature = (int) (Math.random() * 25) + parent.getResources().getString(R.string.weather_postfix);
        moisture = String.valueOf((int) (Math.random() * 100));
        pressure = String.valueOf((int) (Math.random() * 100));
        windSpeed = String.valueOf((int) (Math.random() * 10));
    }

    private String getDayName(int shift) {
        String rawString = new SimpleDateFormat("EEEE", Locale.forLanguageTag(Locale.getDefault().getLanguage())).format(Calendar.getInstance().getTime());
        String currentDay = rawString.substring(0, 1).toUpperCase() + rawString.substring(1);
        return days.get(days.indexOf(currentDay) + shift);
    }

    public String getWeatherStatus() {
        return weatherStatus;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getMoisture() {
        return moisture;
    }

    public String getPressure() {
        return pressure;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }
}
