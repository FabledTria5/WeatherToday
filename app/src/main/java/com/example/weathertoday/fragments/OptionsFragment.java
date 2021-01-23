package com.example.weathertoday.fragments;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.weathertoday.R;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class OptionsFragment extends Fragment {

    private SwitchCompat themeSwitcher;
    private Spinner languageSelector;
    private Spinner temperatureUnitsSelector;
    private Spinner pressureUnitsSelector;
    private Spinner windSpeedUnitsSelector;
    private ConstraintLayout appThemeLayout;
    private ConstraintLayout languageLayout;
    private ConstraintLayout temperatureLayout;
    private ConstraintLayout pressureLayout;
    private ConstraintLayout windSpeedLayout;
    private MaterialButton doneBtn;

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
        setupThemeSwitcher();

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        appThemeLayout.setOnClickListener(v -> openCloseExpandableLayout((ConstraintLayout) appThemeLayout.getChildAt(2)));
        languageLayout.setOnClickListener(v -> openCloseExpandableLayout((ConstraintLayout) languageLayout.getChildAt(2)));
        temperatureLayout.setOnClickListener(v -> openCloseExpandableLayout((ConstraintLayout) temperatureLayout.getChildAt(2)));
        pressureLayout.setOnClickListener(v -> openCloseExpandableLayout((ConstraintLayout) pressureLayout.getChildAt(2)));
        windSpeedLayout.setOnClickListener(v -> openCloseExpandableLayout((ConstraintLayout) windSpeedLayout.getChildAt(2)));

        doneBtn.setOnClickListener(v -> Navigation.findNavController(requireView()).popBackStack());

        themeSwitcher.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
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

        appThemeLayout = v.findViewById(R.id.openAppTheme);
        languageLayout = v.findViewById(R.id.openSelectLanguage);
        temperatureLayout = v.findViewById(R.id.openSelectTemperature);
        pressureLayout = v.findViewById(R.id.openSelectPressure);
        windSpeedLayout = v.findViewById(R.id.openSelectWindSpeed);

        doneBtn = v.findViewById(R.id.doneButtonView);
    }

    private void openCloseExpandableLayout(ConstraintLayout layout) {
        if (layout.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition((ViewGroup) layout.getParent().getParent(), new AutoTransition());
            layout.setVisibility(View.VISIBLE);
            ConstraintLayout parent = (ConstraintLayout) layout.getParent();
            ImageView arrow = (ImageView) parent.getChildAt(1);
            arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
        } else {
            ConstraintLayout parent = (ConstraintLayout) layout.getParent();
            ImageView arrow = (ImageView) parent.getChildAt(1);
            arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            layout.setVisibility(View.GONE);
        }
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

    private void setupThemeSwitcher() {
        int nightModeFlag = requireContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        switch (nightModeFlag) {
            case Configuration.UI_MODE_NIGHT_NO:
                themeSwitcher.setChecked(true);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                themeSwitcher.setChecked(false);
                break;
        }
    }
}