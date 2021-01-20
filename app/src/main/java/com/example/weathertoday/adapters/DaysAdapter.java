package com.example.weathertoday.adapters;

import android.app.Activity;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertoday.R;
import com.example.weathertoday.WeatherDays;

import java.io.Serializable;
import java.util.ArrayList;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.DaysViewHolder> implements Serializable {

    private ArrayList<WeatherDays> days = new ArrayList<>();

    class DaysViewHolder extends RecyclerView.ViewHolder {

        private final ConstraintLayout hiddenLayout;

        private final ImageView arrowIcon;
        private final ImageView dayStatusIcon;

        private final TextView dayOfWeek;
        private final TextView temperature;
        private final TextView weatherStatus;
        private final TextView moistureDayValue;
        private final TextView pressureDayValue;
        private final TextView windSpeedDayValue;

        public DaysViewHolder(@NonNull View itemView) {
            super(itemView);
            hiddenLayout = itemView.findViewById(R.id.expandableDayView);

            arrowIcon = itemView.findViewById(R.id.dayArrowView);
            dayStatusIcon = itemView.findViewById(R.id.dayStatusIconView);

            dayOfWeek = itemView.findViewById(R.id.dayNameView);
            temperature = itemView.findViewById(R.id.dayTemperatureValueView);
            weatherStatus = itemView.findViewById(R.id.dayWeatherStatusView);
            moistureDayValue = itemView.findViewById(R.id.moistureDayValue);
            pressureDayValue = itemView.findViewById(R.id.pressureDayView);
            windSpeedDayValue = itemView.findViewById(R.id.windSpeedDayView);
        }

        public void bind(int position) {
            WeatherDays day = days.get(position);

            dayOfWeek.setText(day.getDayOfWeek());
            temperature.setText(day.getTemperature());
            weatherStatus.setText(day.getWeatherStatus());
            moistureDayValue.setText(day.getMoisture());
            pressureDayValue.setText(day.getPressure());
            windSpeedDayValue.setText(day.getWindSpeed());
        }

        public void showHideDay() {
            if (hiddenLayout.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition((ViewGroup) hiddenLayout.getParent().getParent(), new AutoTransition());
                hiddenLayout.setVisibility(View.VISIBLE);
                arrowIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            } else {
                hiddenLayout.setVisibility(View.GONE);
                arrowIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            }
        }
    }

    @NonNull
    @Override
    public DaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_day_item, parent, false);
        return new DaysViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DaysViewHolder holder, int position) {
        holder.bind(position);
        holder.itemView.setOnClickListener(v -> holder.showHideDay());
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public void addItems(ArrayList<WeatherDays> arrayList) {
        days.addAll(arrayList);
    }

    public ArrayList<WeatherDays> getItems() {
        return days;
    }

    public void clearItems() {
        days.clear();
    }
}
