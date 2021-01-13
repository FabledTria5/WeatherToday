package com.example.weathertoday;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.appbar.MaterialToolbar;

public class OptionsActivity extends AppCompatActivity {

    private SwitchCompat themeSwitcher;
    private Spinner languageSelector;
    private Spinner temperatureUnitsSelector;
    private Spinner pressureUnitsSelector;
    private MaterialToolbar toolbar;
    private Button saveSettings; // Будет сохранять пользовательские настройки

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_layout);

        findViews();
        setToolbar();
        themeSwitcher.setChecked(true);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(v -> startActivity(new Intent(OptionsActivity.this, MainActivity.class)));
    }

    private void findViews() {
        toolbar = findViewById(R.id.toolbarView);
        themeSwitcher = findViewById(R.id.nightModeSwitchView);
        languageSelector = findViewById(R.id.selectLanguageView);
        temperatureUnitsSelector = findViewById(R.id.selectTemperatureUnitsView);
        pressureUnitsSelector = findViewById(R.id.selectPressureUnitsView);
        saveSettings = findViewById(R.id.saveSettingsView);
    }
}
