package com.example.weathertoday.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.weathertoday.R;

import java.util.Objects;

public class OptionsFragment extends Fragment {

    private SwitchCompat themeSwitcher;
    private Spinner languageSelector;
    private Spinner temperatureUnitsSelector;
    private Spinner pressureUnitsSelector;
    private Spinner windSpeedUnitsSelector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        requireActivity().setTitle(R.string.option_fragment_name);
        setHasOptionsMenu(true);
        findViews(view);
        setupSpinners();
        themeSwitcher.setChecked(true);

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Navigation.findNavController(requireView()).popBackStack();
        }
        return true;
    }

    private void findViews(View v) {
        themeSwitcher = v.findViewById(R.id.nightModeSwitchView);
        languageSelector = v.findViewById(R.id.selectLanguageView);
        temperatureUnitsSelector = v.findViewById(R.id.selectTemperatureUnitsView);
        pressureUnitsSelector = v.findViewById(R.id.selectPressureUnitsView);
        windSpeedUnitsSelector = v.findViewById(R.id.selectWindSpeedView);
    }

    private void setupSpinners() {
        if (getContext() != null) {
            ArrayAdapter<String> languageAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, getResources().getStringArray(R.array.languages));
            ArrayAdapter<String> temperatureAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, getResources().getStringArray(R.array.temperature_units));
            ArrayAdapter<String> pressureAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, getResources().getStringArray(R.array.pressure_units));
            ArrayAdapter<String> windSpeedAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, getResources().getStringArray(R.array.wind_speed_units));

            languageAdapter.setDropDownViewResource(R.layout.spinner_item);
            temperatureAdapter.setDropDownViewResource(R.layout.spinner_item);
            pressureAdapter.setDropDownViewResource(R.layout.spinner_item);
            windSpeedAdapter.setDropDownViewResource(R.layout.spinner_item);

            languageSelector.setAdapter(languageAdapter);
            temperatureUnitsSelector.setAdapter(temperatureAdapter);
            pressureUnitsSelector.setAdapter(pressureAdapter);
            windSpeedUnitsSelector.setAdapter(windSpeedAdapter);
        }
    }
}