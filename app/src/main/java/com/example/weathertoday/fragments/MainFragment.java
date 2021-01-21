package com.example.weathertoday.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.Objects;

public class MainFragment extends Fragment {

    private TextView currentLocation;
    private TextView temperature;
    private TextView moisture;
    private TextView pressure;
    private TextView windSpeed;
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
            if (savedContainer != null) {
                setData(savedContainer.getCurrentLocation(), savedContainer.getTemperature(), savedContainer.getMoistureValue(), savedContainer.getPressureValue(), savedContainer.getWindSpeedValue());
            } else {
                generateData();
            }
            if (savedInstanceState.getSerializable("WeekWeather") != null) {
                initRecyclerView((ArrayList<WeatherDays>) savedInstanceState.getSerializable("WeekWeather"));
            }
        } else {
            initRecyclerView(WeatherDays.getDays(8, requireActivity()));
        }

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_list);

        view.findViewById(R.id.locationInfoView).setOnClickListener(v -> openLocationInfo());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem menuItem = menu.add(R.string.option_fragment_name);
        menuItem.setIcon(R.drawable.ic_settings);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                Navigation.findNavController(requireView()).navigate(R.id.navigateToOptionsFragment);
                break;
            case android.R.id.home:
                Navigation.findNavController(requireView()).navigate(R.id.navigateToCityPickFragment);
                break;
        }
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
        currentLocation = v.findViewById(R.id.weatherLocationView);
        temperature = v.findViewById(R.id.weatherTemperatureView);
        moisture = v.findViewById(R.id.weatherMoistureValueView);
        pressure = v.findViewById(R.id.weatherPressureValueView);
        windSpeed = v.findViewById(R.id.windSpeedValueView);
        daysRecyclerView = v.findViewById(R.id.daysListView);
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
        if (currentLocation.getText().equals(getResources().getString(R.string.weather_location)))
            currentLocation.setText(currentLocationValue);
        temperature.setText(temperatureValue);
        moisture.setText(moistureValue);
        pressure.setText(pressureValue);
        windSpeed.setText(windSpeedValue);
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
            Uri uri = Uri.parse(WIKI_URL + target);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }
}