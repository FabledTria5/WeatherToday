package com.example.weathertoday;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertoday.adapters.CitiesListAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.List;

public class CityPickActivity extends AppCompatActivity {

    private RecyclerView citiesList;
    private CitiesListAdapter adapter;
    private TextInputEditText citySearch;
    private TextView topTen;

    private List<String> topCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_pick);

        findViews();
        initRecyclerView();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        citySearch.setOnFocusChangeListener((v, hasFocus) -> {
            Toast toast = Toast.makeText(getApplicationContext(), "Search not yet available :(", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0,0);
            toast.show();
        });
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
        citySearch = findViewById(R.id.citySearchFieldView);
        citiesList = findViewById(R.id.citiesListView);
        topTen = findViewById(R.id.topCitiesView);
    }

    private void initRecyclerView() {
        topCities = Arrays.asList(getResources().getStringArray(R.array.topCities));
        citiesList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CitiesListAdapter(CityPickActivity.this, this);
        adapter.addItems(topCities);

        citiesList.setAdapter(adapter);
    }
}
