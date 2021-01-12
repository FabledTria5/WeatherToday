package com.example.weathertoday.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.DaysViewHolder> {

    static class DaysViewHolder extends RecyclerView.ViewHolder {

        public DaysViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }

    @NonNull
    @Override
    public DaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DaysViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
