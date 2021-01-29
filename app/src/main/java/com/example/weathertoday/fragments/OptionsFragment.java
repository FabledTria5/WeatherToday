package com.example.weathertoday.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.weathertoday.R;
import com.example.weathertoday.containers.OptionsContainer;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class OptionsFragment extends Fragment {

    private SwitchCompat themeSwitcher;
    private Spinner languageSelector;
    private Spinner unitsSelector;
    private ConstraintLayout appThemeLayout;
    private ConstraintLayout languageLayout;
    private ConstraintLayout unitsLayout;
    private MaterialButton doneBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        requireActivity().setTitle(R.string.option_fragment_name);
        findViews(view);
        setupLayouts(view);
        setupSpinners();
        setupThemeSwitcher();

        appThemeLayout.setOnClickListener(v -> openCloseExpandableLayout((ConstraintLayout) appThemeLayout.getChildAt(2)));
        languageLayout.setOnClickListener(v -> openCloseExpandableLayout((ConstraintLayout) languageLayout.getChildAt(2)));
        unitsLayout.setOnClickListener(v -> openCloseExpandableLayout((ConstraintLayout) unitsLayout.getChildAt(2)));

        doneBtn.setOnClickListener(v -> getBack());

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
            getBack();
        }
        return true;
    }

    private void findViews(View v) {
        themeSwitcher = v.findViewById(R.id.nightModeSwitchView);
        languageSelector = v.findViewById(R.id.selectLanguageView);
        unitsSelector = v.findViewById(R.id.selectTemperatureUnitsView);

        appThemeLayout = v.findViewById(R.id.openAppTheme);
        languageLayout = v.findViewById(R.id.openSelectLanguage);
        unitsLayout = v.findViewById(R.id.openSelectTemperature);

        doneBtn = v.findViewById(R.id.doneButtonView);
    }

    private void setupLayouts(View v) {
        ArrayList<ConstraintLayout> list = OptionsContainer.getVisibleLayouts();
        for (ConstraintLayout layout : list) {
            v.findViewWithTag(layout.getTag()).setVisibility(View.VISIBLE);
        }
    }

    private void openCloseExpandableLayout(ConstraintLayout layout) {
        if (layout.getVisibility() == View.GONE) {
            OptionsContainer.addVisible(layout, layout.getTag());
            TransitionManager.beginDelayedTransition((ViewGroup) layout.getParent().getParent(), new AutoTransition());
            layout.setVisibility(View.VISIBLE);
            ConstraintLayout parent = (ConstraintLayout) layout.getParent();
            ImageView arrow = (ImageView) parent.getChildAt(1);
            arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
        } else {
            OptionsContainer.removeVisible(layout.getTag());
            ConstraintLayout parent = (ConstraintLayout) layout.getParent();
            ImageView arrow = (ImageView) parent.getChildAt(1);
            arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            layout.setVisibility(View.GONE);
        }
    }

    private void setupSpinners() {
        if (getContext() != null) {
            ArrayAdapter<String> languageAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, getResources().getStringArray(R.array.languages));
            ArrayAdapter<String> unitsAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, getResources().getStringArray(R.array.units_names));

            languageAdapter.setDropDownViewResource(R.layout.spinner_item);
            unitsAdapter.setDropDownViewResource(R.layout.spinner_item);

            languageSelector.setAdapter(languageAdapter);
            unitsSelector.setAdapter(unitsAdapter);
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

    private void getBack() {
        OptionsContainer.clearVisibilitiesList();
        Navigation.findNavController(requireView()).popBackStack();
    }
}