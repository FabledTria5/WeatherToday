package com.example.weathertoday.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertoday.R;
import com.example.weathertoday.WeatherDataContainer;
import com.example.weathertoday.WeatherDays;
import com.example.weathertoday.adapters.DaysAdapter;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Objects;

public class MainFragment extends Fragment {

    private ImageView backgroundImage;
    private TextView currentLocation;
    private TextView weatherStatus;
    private TextView temperature;
    private TextView moisture;
    private TextView pressure;
    private TextView windSpeed;
    private TextView dayOfWeek;
    private RecyclerView daysRecyclerView;

    private String currentWeatherPostfix = "\u00B0"; // В будущем будет выбираться согласно пользовательким настройкам
    private final String WIKI_URL = "https://ru.wikipedia.org/wiki/";

    DaysAdapter daysAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        requireActivity().setTitle(getString(R.string.app_name));
        setHasOptionsMenu(true);
        setRetainInstance(true);
        findViews(view);

        if (savedInstanceState != null) {
            WeatherDataContainer savedContainer = (WeatherDataContainer) savedInstanceState.getSerializable("Key");
            if (savedContainer != null)
                setData(savedContainer.getCurrentLocation(), savedContainer.getTemperature(), savedContainer.getMoistureValue(), savedContainer.getPressureValue(), savedContainer.getWindSpeedValue());
            if (savedInstanceState.getSerializable("WeekWeather") != null)
                initRecyclerView((ArrayList<WeatherDays>) savedInstanceState.getSerializable("WeekWeather"));
        } else {
            generateData();
            initRecyclerView(WeatherDays.getDays(8, requireActivity()));
        }

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_list);

        view.findViewById(R.id.locationInfoView).setOnClickListener(v -> openLocationInfo());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem optionsItem = menu.add(R.string.option_fragment_name);
        MenuItem authorsItem = menu.add(R.string.developers);

        optionsItem.setIcon(R.drawable.ic_settings);
        authorsItem.setIcon(R.drawable.ic_writer);

        optionsItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        authorsItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        optionsItem.setOnMenuItemClickListener(item -> {
            Navigation.findNavController(requireView()).navigate(R.id.navigateToOptionsFragment);
            return true;
        });

        authorsItem.setOnMenuItemClickListener(item -> {
            Toast.makeText(requireContext(), "Not ready yet", Toast.LENGTH_SHORT).show();
            return true;
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            Navigation.findNavController(requireView()).navigate(R.id.navigateToCityPickFragment);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        WeatherDataContainer container = WeatherDataContainer.saveData(currentLocation.getText().toString(),
                temperature.getText().toString(),
                moisture.getText().toString(),
                pressure.getText().toString(),
                windSpeed.getText().toString());
        outState.putSerializable("WeekWeather", daysAdapter.getItems());
        outState.putSerializable("Key", container);
        super.onSaveInstanceState(outState);
    }

    private void findViews(View v) {
        backgroundImage = v.findViewById(R.id.backgroundView);
        currentLocation = v.findViewById(R.id.weatherLocationView);
        weatherStatus = v.findViewById(R.id.weatherStatusView);
        temperature = v.findViewById(R.id.weatherTemperatureView);
        moisture = v.findViewById(R.id.weatherMoistureValueView);
        pressure = v.findViewById(R.id.weatherPressureValueView);
        windSpeed = v.findViewById(R.id.windSpeedValueView);
        daysRecyclerView = v.findViewById(R.id.daysListView);
        dayOfWeek = v.findViewById(R.id.dayOfWeekView);
    }

    private void generateData() {
        MainFragmentArgs args = MainFragmentArgs.fromBundle(requireArguments());

        String currentLocationValue = args.getCityName();
        String temperatureValue = (int) (Math.random() * 25) + currentWeatherPostfix;
        String moistureValue = String.valueOf((int) (Math.random() * 100));
        String pressureValue = String.valueOf((int) (Math.random() * 100));
        String windSpeedValue = String.valueOf((int) (Math.random() * 10));

        setData(currentLocationValue, temperatureValue, moistureValue, pressureValue, windSpeedValue);
    }

    private void setData(String currentLocationValue, String temperatureValue, String moistureValue, String pressureValue, String windSpeedValue) {
        setBackground();
        setDayOfWeek();
        if (currentLocation.getText().equals(getResources().getString(R.string.weather_location)))
            currentLocation.setText(currentLocationValue);
        temperature.setText(temperatureValue);
        moisture.setText(moistureValue);
        pressure.setText(pressureValue);
        windSpeed.setText(windSpeedValue);
        weatherStatus.setText(getString(R.string.weather_status_example));
    }

    private void setDayOfWeek() {
        dayOfWeek.setText(WeatherDays.getCurrentDayName());
    }

    private void setBackground() {
        int nightModeFlag = requireContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        switch (nightModeFlag) {
            case Configuration.UI_MODE_NIGHT_NO:
                backgroundImage.setImageResource(R.drawable.default_image_day);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                backgroundImage.setImageResource(R.drawable.default_image_night);
                break;
        }
    }

    private void initRecyclerView(ArrayList<WeatherDays> data) {
        daysAdapter = new DaysAdapter();

        daysAdapter.addItems(data);

        daysRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        daysRecyclerView.setAdapter(daysAdapter);
    }

    private void openLocationInfo() {
        String target = currentLocation.getText().toString();
        if (!target.equals(getResources().getString(R.string.weather_location))) {
            Snackbar.make(requireView(), R.string.open_in_browser, BaseTransientBottomBar.LENGTH_LONG).setAction(R.string.open, v -> {
                Uri uri = Uri.parse(WIKI_URL + target);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }).show();
        } else {
            Toast.makeText(requireContext(), R.string.choose_city, Toast.LENGTH_SHORT).show();
        }
    }
}