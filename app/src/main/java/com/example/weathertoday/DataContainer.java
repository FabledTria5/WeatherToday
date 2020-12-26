package com.example.weathertoday;

import java.io.Serializable;

public class DataContainer implements Serializable {

    private static DataContainer instance;
    private final String currentWeatherValue;
    private final String moistureValue;
    private final String pressureValue;
    private final String windSpeedValue;

    private DataContainer(String currentWeatherValue, String moistureValue, String pressureValue, String windSpeedValue) {
        this.currentWeatherValue = currentWeatherValue;
        this.moistureValue = moistureValue;
        this.pressureValue = pressureValue;
        this.windSpeedValue = windSpeedValue;
    }

    public static DataContainer getInstance(String currentWeatherValue, String moistureValue, String pressureValue, String windSpeedValue) {
        return (instance == null) ? instance = new DataContainer(currentWeatherValue, moistureValue, pressureValue, windSpeedValue) : instance;
    }

    public String getCurrentWeatherValue() {
        return currentWeatherValue;
    }

    public String getMoistureValue() {
        return moistureValue;
    }

    public String getPressureValue() {
        return pressureValue;
    }

    public String getWindSpeedValue() {
        return windSpeedValue;
    }
}
