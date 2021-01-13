package com.example.weathertoday;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextView currentLocation;
    private TextView temperature;
    private TextView moisture;
    private TextView pressure;
    private TextView windSpeed;

    private String currentWeatherPostfix = "\u00B0"; // В будущем будет выбираться согласно пользовательким настройкам
    private final String wikiUrl = "https://ru.wikipedia.org/wiki/";

    private static final int REQUEST_ACCESS_TYPE = 1;
    public static final String ACCESS_MESSAGE = "Location";
    private final String saveInstance = "Saved instance";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        generateData();

        findViewById(R.id.menuButton).setOnClickListener(v -> openMenu());
        findViewById(R.id.optionsButton).setOnClickListener(v -> openOptions());
        findViewById(R.id.locationInfoView).setOnClickListener(v -> openLocationInfo());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        WeatherDataContainer container = WeatherDataContainer.saveData(currentLocation.getText().toString(),
                temperature.getText().toString(),
                moisture.getText().toString(),
                pressure.getText().toString(),
                windSpeed.getText().toString());
        outState.putSerializable(saveInstance, container);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        WeatherDataContainer savedContainer = (WeatherDataContainer) savedInstanceState.getSerializable(saveInstance);
        if (savedContainer != null)
            setData(savedContainer.getCurrentLocation(), savedContainer.getTemperature(), savedContainer.getMoistureValue(), savedContainer.getPressureValue(), savedContainer.getWindSpeedValue());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_ACCESS_TYPE) {
            if (resultCode == RESULT_OK) {
                String location = Objects.requireNonNull(data).getStringExtra(ACCESS_MESSAGE);
                currentLocation.setText(location);
                generateData();
            } else {
                currentLocation.setText(getResources().getString(R.string.error_message));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void findViews() {
        currentLocation = findViewById(R.id.weatherLocationView);
        temperature = findViewById(R.id.weatherTemperatureView);
        moisture = findViewById(R.id.weatherMoistureValueView);
        pressure = findViewById(R.id.weatherPressureValueView);
        windSpeed = findViewById(R.id.windSpeedValueView);
    }

    private void generateData() {
        String currentLocationValue = getResources().getString(R.string.weather_location);
        String temperatureValue = (int) (Math.random() * 25) + currentWeatherPostfix;
        String moistureValue = String.valueOf((int) (Math.random() * 100));
        String pressureValue = String.valueOf((int) (Math.random() * 100));
        String windSpeedValue = String.valueOf((int) (Math.random() * 10));

        setData(currentLocationValue, temperatureValue, moistureValue, pressureValue, windSpeedValue);
    }

    private void setData(String currentLocationValue, String temperatureValue, String moistureValue, String pressureValue, String windSpeedValue) {
        if (currentLocation.getText().equals(getResources().getString(R.string.weather_location)))
            currentLocation.setText(currentLocationValue);
        temperature.setText(temperatureValue);
        moisture.setText(moistureValue);
        pressure.setText(pressureValue);
        windSpeed.setText(windSpeedValue);
    }

    private void openMenu() {
        Intent intent = new Intent(MainActivity.this, CityPickActivity.class);
        startActivityForResult(intent, REQUEST_ACCESS_TYPE);
    }

    private void openOptions() {
        Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
        startActivity(intent);
    }

    private void openLocationInfo() {
        String target = currentLocation.getText().toString();
        if (!target.equals(getResources().getString(R.string.weather_location))) {
            Uri uri = Uri.parse(wikiUrl + target);
            Intent browser = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(browser);
        }
    }
}