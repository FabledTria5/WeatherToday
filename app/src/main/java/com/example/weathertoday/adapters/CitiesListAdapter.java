package com.example.weathertoday.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertoday.R;
import com.example.weathertoday.fragments.CityPickFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CitiesListAdapter extends RecyclerView.Adapter<CitiesListAdapter.CitiesViewHolder> {

    private final List<String> citiesList = new ArrayList<>();
    private final CityPickFragment parentContext;

    public CitiesListAdapter(CityPickFragment context) {
        this.parentContext = context;
    }

    static class CitiesViewHolder extends RecyclerView.ViewHolder {

        private final TextView cityName;

        public CitiesViewHolder(View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.cityNameView);
        }

        public void bind(String city) {
            cityName.setText(city);
        }
    }

    @NonNull
    @Override
    public CitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CitiesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesViewHolder holder, int position) {
        holder.bind(citiesList.get(position));

        holder.itemView.setOnClickListener(v -> openCity(position));
    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }

    public void addItems(Collection<String> cities) {
        citiesList.addAll(cities);
        notifyDataSetChanged();
    }

    public void clearItems() {
        citiesList.clear();
        notifyDataSetChanged();
    }

    private void openCity(int position) {
        parentContext.openCity(citiesList.get(position));
    }
}
