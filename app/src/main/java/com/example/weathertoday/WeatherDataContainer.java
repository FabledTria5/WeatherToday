package com.example.weathertoday;

import java.io.Serializable;

public class WeatherDataContainer implements Serializable {

    private final String currentLocation;
    private final String temperature;
    private final String moistureValue;
    private final String pressureValue;
    private final String windSpeedValue;

    private WeatherDataContainer(String currentLocation, String temperature, String moistureValue, String pressureValue, String windSpeedValue) {
        this.currentLocation = currentLocation;
        this.temperature = temperature;
        this.moistureValue = moistureValue;
        this.pressureValue = pressureValue;
        this.windSpeedValue = windSpeedValue;
    }

    public static WeatherDataContainer saveData(String currentLocation, String temperatureValue, String moistureValue, String pressureValue, String windSpeedValue) {
        return new WeatherDataContainer(currentLocation, temperatureValue, moistureValue, pressureValue, windSpeedValue);
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public String getTemperature() {
        return temperature;
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
