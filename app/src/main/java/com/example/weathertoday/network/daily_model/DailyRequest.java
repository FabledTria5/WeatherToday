package com.example.weathertoday.network.daily_model;

public class DailyRequest {
    private Humidity humidity;
    private Pressure pressure;
    private Temp temp;
    private WindSpeed wind_speed;
    private Weather[] weather;

    public Humidity getHumidity() {
        return humidity;
    }

    public void setHumidity(Humidity humidity) {
        this.humidity = humidity;
    }

    public Pressure getPressure() {
        return pressure;
    }

    public void setPressure(Pressure pressure) {
        this.pressure = pressure;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public WindSpeed getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(WindSpeed wind_speed) {
        this.wind_speed = wind_speed;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }
}
