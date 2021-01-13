package com.example.weathertoday;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertoday.adapters.CitiesListAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;

public class CityPickActivity extends AppCompatActivity {

    private String[] topCities;
    private RecyclerView citiesList;
    private CitiesListAdapter adapter;
    private MaterialToolbar toolbar;
    private TextInputLayout cityInputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_pick);

        findViews();
        setToolbar();
        initRecyclerView();
    }

    private void findViews() {
        toolbar = findViewById(R.id.toolbarView);
        cityInputView = findViewById(R.id.cityInputView);
        citiesList = findViewById(R.id.citiesListView);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(v -> startActivity(new Intent(CityPickActivity.this, MainActivity.class)));
    }

    private void initRecyclerView() {
        topCities = getResources().getStringArray(R.array.topCities);

        citiesList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CitiesListAdapter(CityPickActivity.this, this);
        adapter.addItems(Arrays.asList(topCities));

        citiesList.setAdapter(adapter);
    }
}
