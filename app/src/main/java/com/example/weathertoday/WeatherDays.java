package com.example.weathertoday;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WeatherDays implements Serializable {

    public static String getDayOfWeek(String dt_txt) throws ParseException {
        dt_txt = dt_txt.substring(0, 10);
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date date = inFormat.parse(dt_txt);
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE", Locale.US);
        return outFormat.format(date);
    }

    public static String getCurrentDayName() {
        String rawString = new SimpleDateFormat("EEEE", Locale.forLanguageTag(Locale.getDefault().getLanguage())).format(Calendar.getInstance().getTime());
        return rawString.substring(0, 1).toUpperCase() + rawString.substring(1);
    }
}
