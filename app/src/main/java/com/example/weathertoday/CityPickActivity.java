package com.example.weathertoday;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertoday.adapters.CitiesListAdapter;

import java.util.Arrays;

public class CityPickActivity extends AppCompatActivity {

    private String[] topCities;
    private RecyclerView citiesList;
    private CitiesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_pick);

        initRecyclerView();
    }

    private void initRecyclerView() {
        topCities = getResources().getStringArray(R.array.topCities);
        citiesList = findViewById(R.id.citiesListView);

        citiesList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CitiesListAdapter(CityPickActivity.this, this);
        adapter.addItems(Arrays.asList(topCities));

        citiesList.setAdapter(adapter);
    }
}
