package com.example.weathertoday.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertoday.fragments.CityPickFragmentDirections;
import com.example.weathertoday.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private static final List<String> history = new ArrayList<>();
    private final View parentView;

    public HistoryAdapter(View parentView) {
        this.parentView = parentView;
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {

        private final TextView cityName;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.cityNameView);
        }

        public void bind(String city) {
            cityName.setText(city);
        }
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.bind(history.get(position));
    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    public static void addHistoryCity(String city) {
        if (history.contains(city)) return;
        history.add(city);
    }
}
