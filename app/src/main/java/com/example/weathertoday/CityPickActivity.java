package com.example.weathertoday;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertoday.adapters.CitiesListAdapter;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;

public class CityPickActivity extends AppCompatActivity {

    private String[] topCities;
    private RecyclerView citiesList;
    private CitiesListAdapter adapter;
    private TextInputLayout cityInputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_pick);

        findViews();
        initRecyclerView();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews() {
        cityInputView = findViewById(R.id.cityInputView);
        citiesList = findViewById(R.id.citiesListView);
    }

    private void initRecyclerView() {
        topCities = getResources().getStringArray(R.array.topCities);

        citiesList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CitiesListAdapter(CityPickActivity.this, this);
        adapter.addItems(Arrays.asList(topCities));

        citiesList.setAdapter(adapter);
    }
}
