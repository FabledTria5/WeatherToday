package com.example.weathertoday.containers;

import java.io.Serializable;

public class WeatherDataContainer implements Serializable {

    private final String currentLocation;
    private final String temperature;
    private final String moistureValue;
    private final String pressureValue;
    private final String windSpeedValue;
    private final String status;

    private WeatherDataContainer(String currentLocation, String temperature, String status, String moistureValue, String pressureValue, String windSpeedValue) {
        this.currentLocation = currentLocation;
        this.temperature = temperature;
        this.moistureValue = moistureValue;
        this.pressureValue = pressureValue;
        this.windSpeedValue = windSpeedValue;
        this.status = status;
    }

    public static WeatherDataContainer saveData(String currentLocation, String temperatureValue, String status, String moistureValue, String pressureValue, String windSpeedValue) {
        return new WeatherDataContainer(currentLocation, temperatureValue, status, moistureValue, pressureValue, windSpeedValue);
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

    public String getStatus() {
        return status;
    }
}
