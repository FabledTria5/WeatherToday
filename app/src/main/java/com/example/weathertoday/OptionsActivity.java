package com.example.weathertoday;

import android.os.Bundle;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class OptionsActivity extends AppCompatActivity {

    private SwitchCompat themeSwitcher;
    private Spinner languageSelector;
    private Spinner temperatureUnitsSelector;
    private Spinner pressureUnitsSelector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_layout);

        findViews();
    }

    private void findViews() {
        themeSwitcher = findViewById(R.id.nightModeSwitchView);
        languageSelector = findViewById(R.id.selectLanguageView);
        temperatureUnitsSelector = findViewById(R.id.selectTemperatureUnitsView);
        pressureUnitsSelector = findViewById(R.id.selectPressureUnitsView);
    }
}
