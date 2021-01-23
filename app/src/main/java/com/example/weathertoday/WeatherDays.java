package com.example.weathertoday;

import android.app.Activity;
import android.os.Build;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    private String date;

    private static ArrayList<String> days;

    public static ArrayList<WeatherDays> getDays(int value, Activity parent) {

        ArrayList<WeatherDays> arrayList = new ArrayList<>();
        days = new ArrayList<>(Arrays.asList(parent.getResources().getStringArray(R.array.days_of_week)));

        int daysShift = 1;
        int dateShift = 1;

        for (int i = 0; i < value; i++) {
            WeatherDays day = new WeatherDays();
            try {
                day.getDayName(daysShift);
            } catch (IndexOutOfBoundsException e) {
                daysShift = getDaysShift();
                continue;
            }
            day.generateData(dateShift, parent);
            arrayList.add(day);
            dateShift++;
            if (day.dayOfWeek.equals(days.get(days.size() - 1))) {
                daysShift = getDaysShift();
                continue;
            }
            daysShift++;
        }
        return arrayList;
    }

    private static int getDaysShift() {
        int daysShift;
        String rawString = new SimpleDateFormat("EEEE", Locale.forLanguageTag(Locale.getDefault().getLanguage())).format(Calendar.getInstance().getTime());
        String currentDay = rawString.substring(0, 1).toUpperCase() + rawString.substring(1);
        daysShift = -days.indexOf(currentDay);
        return daysShift;
    }

    private void generateData(int dateShift, Activity parent) {
        weatherStatus = parent.getResources().getString(R.string.weather_status_example);
        temperature = (int) (Math.random() * 25) + parent.getResources().getString(R.string.weather_postfix);
        moisture = String.valueOf((int) (Math.random() * 100));
        pressure = String.valueOf((int) (Math.random() * 100));
        windSpeed = String.valueOf((int) (Math.random() * 10));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = LocalDate.now().plusDays(dateShift).toString();
        }
    }

    private void getDayName(int shift) throws IndexOutOfBoundsException {
        String rawString = new SimpleDateFormat("EEEE", Locale.forLanguageTag(Locale.getDefault().getLanguage())).format(Calendar.getInstance().getTime());
        String currentDay = rawString.substring(0, 1).toUpperCase() + rawString.substring(1);
        dayOfWeek = days.get(days.indexOf(currentDay) + shift);
    }

    public static String getCurrentDayName() {
        String rawString = new SimpleDateFormat("EEEE", Locale.forLanguageTag(Locale.getDefault().getLanguage())).format(Calendar.getInstance().getTime());
        return rawString.substring(0, 1).toUpperCase() + rawString.substring(1);
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

    public String getDate() {
        return date;
    }
}
