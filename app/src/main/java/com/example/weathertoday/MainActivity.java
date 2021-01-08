package com.example.weathertoday;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView currentWeather;
    private TextView moisture;
    private TextView pressure;
    private TextView windSpeed;
    private String currentWeatherPostfix = "\u00B0"; // В будущем будет выбираться согласно пользовательким настройкам

    private final String methodsLog = "Method info";
    private final String saveInstance = "Saved instance";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(methodsLog, "onCreate Method");
        Toast.makeText(this, "onCreate method", Toast.LENGTH_SHORT).show();

        findViews();
        generateData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(methodsLog, "onStart Method");
        Toast.makeText(this, "onStart method", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(methodsLog, "onResume Method");
        Toast.makeText(this, "onResume method", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(methodsLog, "onPause Method");
        Toast.makeText(this, "onPause method", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(methodsLog, "onStop Method");
        Toast.makeText(this, "onStop method", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(methodsLog, "onDestroy Method");
        Toast.makeText(this, "onDestroy method", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        DataContainer container = DataContainer.getInstance(currentWeather.getText().toString(), moisture.getText().toString(), pressure.getText().toString(), windSpeed.getText().toString());
        outState.putSerializable(saveInstance, container);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        DataContainer savedContainer = (DataContainer) savedInstanceState.getSerializable(saveInstance);
        if (savedContainer != null) setData(savedContainer.getCurrentWeatherValue(), savedContainer.getMoistureValue(), savedContainer.getPressureValue(), savedContainer.getWindSpeedValue());
    }

    private void findViews() {
        currentWeather = findViewById(R.id.currentWeatherView);
        moisture = findViewById(R.id.weatherMoistureValueView);
        pressure = findViewById(R.id.weatherPressureValueView);
        windSpeed = findViewById(R.id.windSpeedValueView);
    }

    private void generateData() {

        String currentWeatherValue = (int) (Math.random() * 20) + currentWeatherPostfix;
        String moistureValue = String.valueOf((int) (Math.random() * 100));
        String pressureValue = String.valueOf((int) (Math.random() * 100));
        String windSpeedValue = String.valueOf((int) (Math.random() * 6));

        setData(currentWeatherValue, moistureValue, pressureValue, windSpeedValue);
    }

    @SuppressLint("SetTextI18n")
    private void setData(String currentWeatherValue, String moistureValue, String pressureValue, String windSpeedValue) {
        currentWeather.setText(currentWeatherValue);
        moisture.setText(moistureValue);
        pressure.setText(pressureValue);
        windSpeed.setText(windSpeedValue);
    }
}