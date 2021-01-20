package com.example.weathertoday.fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertoday.R;
import com.example.weathertoday.adapters.CitiesListAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CityPickFragment extends Fragment {

    private RecyclerView citiesList;
    private CitiesListAdapter adapter;
    private TextInputEditText citySearch;
    private TextView topTen;

    private List<String> topCities;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_pick, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        requireActivity().setTitle(R.string.city_pick_fragment_name);
        setHasOptionsMenu(true);
        findViews(view);
        initRecyclerView();

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        citySearch.setOnFocusChangeListener((v, hasFocus) -> {
            Toast toast = Toast.makeText(getContext(), "Search not yet available :(", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0,0);
            toast.show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Navigation.findNavController(requireView()).popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View v) {
        citySearch = v.findViewById(R.id.citySearchFieldView);
        citiesList = v.findViewById(R.id.citiesListView);
        topTen = v.findViewById(R.id.topCitiesView);
    }

    private void initRecyclerView() {
        topCities = Arrays.asList(getResources().getStringArray(R.array.topCities));

        citiesList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CitiesListAdapter(getView());
        adapter.addItems(topCities);

        citiesList.setAdapter(adapter);
    }
}