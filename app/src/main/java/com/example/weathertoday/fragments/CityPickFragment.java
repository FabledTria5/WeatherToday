package com.example.weathertoday.fragments;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertoday.R;
import com.example.weathertoday.adapters.CitiesListAdapter;
import com.example.weathertoday.adapters.HistoryAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CityPickFragment extends Fragment {

    private RecyclerView citiesList;
    private RecyclerView historyList;
    private ConstraintLayout topCitiesLayout;
    private ConstraintLayout historyLayout;
    private CitiesListAdapter citiesListAdapter;
    private HistoryAdapter historyAdapter;
    private ImageView topTenArrowIcon;
    private ImageView historyArrowIcon;

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

        topCitiesLayout.setOnClickListener(v -> openCloseList((RecyclerView) topCitiesLayout.getChildAt(2)));
        historyLayout.setOnClickListener(v -> openCloseList((RecyclerView) historyLayout.getChildAt(2)));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Navigation.findNavController(requireView()).popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openCloseList(RecyclerView list) {
        if (list.getVisibility() == View.GONE) {
            closeViews();
            TransitionManager.beginDelayedTransition((ViewGroup) list.getParent(), new AutoTransition());
            list.setVisibility(View.VISIBLE);
            ConstraintLayout parent = (ConstraintLayout) list.getParent();
            ImageView arrow = (ImageView) parent.getChildAt(0);
            arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
        } else {
            list.setVisibility(View.GONE);
            ConstraintLayout parent = (ConstraintLayout) list.getParent();
            ImageView arrow = (ImageView) parent.getChildAt(0);
            arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
        }
    }

    private void findViews(View v) {
        citiesList = v.findViewById(R.id.citiesListView);
        topTenArrowIcon = v.findViewById(R.id.topCitiesArrowIconView);
        historyList = v.findViewById(R.id.historyListView);
        historyArrowIcon = v.findViewById(R.id.historyArrowIconView);
        topCitiesLayout = v.findViewById(R.id.topCitiesLayout);
        historyLayout = v.findViewById(R.id.historyLayout);
    }

    private void initRecyclerView() {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(requireContext(), R.drawable.separator)));
        topCities = Arrays.asList(getResources().getStringArray(R.array.topCities));

        citiesList.setLayoutManager(new LinearLayoutManager(requireContext()));
        historyList.setLayoutManager(new LinearLayoutManager(requireContext()));

        citiesListAdapter = new CitiesListAdapter(getView());
        historyAdapter = new HistoryAdapter(getView());

        citiesListAdapter.addItems(topCities);
        citiesList.setAdapter(citiesListAdapter);
        citiesList.addItemDecoration(itemDecoration);
        historyList.setAdapter(historyAdapter);
        historyList.addItemDecoration(itemDecoration);
    }

    private void closeViews() {
        citiesList.setVisibility(View.GONE);
        historyList.setVisibility(View.GONE);

        topTenArrowIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
        historyArrowIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
    }
}