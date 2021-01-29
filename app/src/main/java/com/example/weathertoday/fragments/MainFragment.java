package com.example.weathertoday.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertoday.R;
import com.example.weathertoday.WeatherDays;
import com.example.weathertoday.adapters.DaysAdapter;
import com.example.weathertoday.containers.WeatherDataContainer;
import com.example.weathertoday.network.WeatherGetter;
import com.example.weathertoday.network.model.WeatherRequest;
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
    private NestedScrollView nestedScrollView;
    private ProgressBar progressBar;

    private String currentWeatherPostfix = "\u00B0C"; // В будущем будет выбираться согласно пользовательким настройкам
    private final String WIKI_URL = "https://ru.wikipedia.org/wiki/";
    private static final String TAG = "MainFragment";
    private static final int LOADING_DELAY = 200;

    private String currentLocationValue;
    private DaysAdapter daysAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        requireActivity().setTitle(getString(R.string.app_name));
        setRetainInstance(true);
        findViews(view);
        setupMenu();
        setDayOfWeek();
        setBackground();

        currentLocationValue = "Moscow";

        if (savedInstanceState != null) {
            WeatherDataContainer savedContainer = (WeatherDataContainer) savedInstanceState.getSerializable("Key");
            if (savedContainer != null)
                restoreData(savedContainer.getCurrentLocation(), savedContainer.getTemperature(), savedContainer.getStatus(), savedContainer.getMoistureValue(), savedContainer.getPressureValue(), savedContainer.getWindSpeedValue());
            if (savedInstanceState.getSerializable("WeekWeather") != null)
                initRecyclerView((ArrayList<WeatherRequest>) savedInstanceState.getSerializable("WeekWeather"));
        } else {
            WeatherGetter.getWeather(currentLocationValue, MainFragment.this);
            new Handler().postDelayed(() -> WeatherGetter.getWeatherForecast(currentLocationValue, MainFragment.this), LOADING_DELAY);
        }

        view.findViewById(R.id.locationInfoView).setOnClickListener(v -> openLocationInfo());

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            view.findViewById(R.id.btnDownView).setOnClickListener(v -> nestedScrollView.post(() -> nestedScrollView.smoothScrollTo(0, backgroundImage.getHeight())));
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem searchItem = menu.add(R.string.search);
        searchItem.setIcon(R.drawable.ic_search);
        searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        searchItem.setOnMenuItemClickListener((item -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle(R.string.enter_city);

            final EditText input = new EditText(requireContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("OK", (dialog, which) -> {
                String cityName = input.getText().toString();
                WeatherGetter.getWeather(cityName, this);
                WeatherGetter.getWeatherForecast(cityName, this);
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
            return true;
        }));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Log.d(TAG, "onOptionsItemSelected: " + item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        WeatherDataContainer container = WeatherDataContainer.saveData(currentLocation.getText().toString(),
                temperature.getText().toString(),
                weatherStatus.getText().toString(),
                moisture.getText().toString(),
                pressure.getText().toString(),
                windSpeed.getText().toString());
        outState.putSerializable("WeekWeather", daysAdapter.getItems());
        outState.putSerializable("Key", container);
        super.onSaveInstanceState(outState);
    }

    private void setupMenu() {
        setHasOptionsMenu(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_navigation);
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
        nestedScrollView = v.findViewById(R.id.mainScrollView);
        progressBar = v.findViewById(R.id.progressBarView);
    }

    private void restoreData(String currentLocationValue, String temperatureValue, String status, String moistureValue, String pressureValue, String windSpeedValue) {
        setDayOfWeek();
        if (currentLocation.getText().equals(getResources().getString(R.string.weather_location)))
            currentLocation.setText(currentLocationValue);
        temperature.setText(temperatureValue);
        moisture.setText(moistureValue);
        pressure.setText(pressureValue);
        windSpeed.setText(windSpeedValue);
        weatherStatus.setText(getString(R.string.weather_status_example));
        weatherStatus.setText(status);
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

    private void openLocationInfo() {
        String target = currentLocation.getText().toString();
        if (!target.equals("WeatherLocationText")) {
            Snackbar.make(requireView(), R.string.open_in_browser, BaseTransientBottomBar.LENGTH_LONG).setAction(R.string.open, v -> {
                Uri uri = Uri.parse(WIKI_URL + target);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }).show();
        } else {
            Toast.makeText(requireContext(), R.string.choose_city, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("DefaultLocale")
    public void showWeather(WeatherRequest request) {
        currentLocation.setText(request.getName());

        String weatherStatusValue = String.valueOf(request.getWeather()[0].getDescription());
        weatherStatusValue = weatherStatusValue.substring(0, 1).toUpperCase() + weatherStatusValue.substring(1);
        weatherStatus.setText(weatherStatusValue);

        temperature.setText(String.format("%.1f" + currentWeatherPostfix, request.getMain().getTemp()));
        pressure.setText(String.format("%d", request.getMain().getPressure()));
        windSpeed.setText(String.format("%.1f", request.getWind().getSpeed()));
        moisture.setText(String.format("%d", request.getMain().getHumidity()));
    }

    public void initRecyclerView(ArrayList<WeatherRequest> weatherRequests) {
        daysAdapter = new DaysAdapter();
        daysAdapter.addItems(weatherRequests);

        daysRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        daysRecyclerView.setAdapter(daysAdapter);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void showError() {
        Toast.makeText(requireContext(), "This city does not exist!", Toast.LENGTH_LONG).show();
    }
}