package com.example.weathertoday.adapters;

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

import com.example.weathertoday.MySettings;
import com.example.weathertoday.R;
import com.example.weathertoday.WeatherDays;
import com.example.weathertoday.network.model.WeatherRequest;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.DaysViewHolder> implements Serializable {

    private ArrayList<WeatherRequest> days = new ArrayList<>();

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
        private final TextView date;

        private final String ICON_URL_PREFIX = "http://openweathermap.org/img/wn/";
        private final String ICON_URL_POSTFIX = "@2x.png";

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
            date = itemView.findViewById(R.id.dateView);
        }

        public void bind(int position) {
           WeatherRequest day = days.get(position);

           temperature.setText(String.format(Locale.ENGLISH, "%.1f" + MySettings.getTempPostfix(), day.getMain().getTemp()));
           pressureDayValue.setText(String.format(Locale.ENGLISH,"%d", day.getMain().getPressure()));
           windSpeedDayValue.setText(String.format(Locale.ENGLISH,"%.1f", day.getWind().getSpeed()));
           moistureDayValue.setText(String.format(Locale.ENGLISH,"%d", day.getMain().getHumidity()));

            String weatherStatusValue = String.valueOf(day.getWeather()[0].getDescription());
            weatherStatusValue = weatherStatusValue.substring(0, 1).toUpperCase() + weatherStatusValue.substring(1);
            weatherStatus.setText(weatherStatusValue);

            try {
                dayOfWeek.setText(WeatherDays.getDayOfWeek(day.getDt_txt()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            date.setText(day.getDt_txt());

            String icon = day.getWeather()[0].getIcon();
            Picasso.get().load(ICON_URL_PREFIX + icon + ICON_URL_POSTFIX).into(dayStatusIcon);
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

    public void addItems(ArrayList<WeatherRequest> list) {
        days.addAll(list);
    }

    public ArrayList<WeatherRequest> getItems() {
        return days;
    }

    public void clearItems() {
        days.clear();
    }
}
