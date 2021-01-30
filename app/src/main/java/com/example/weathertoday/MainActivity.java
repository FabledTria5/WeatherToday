package com.example.weathertoday;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.weathertoday.network.WeatherGetter;
import com.example.weathertoday.network.model.WeatherRequest;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private NavigationView navigationView;
    private NavController navController;
    private DrawerLayout drawerLayout;
    private AppBarConfiguration appBarConfiguration;

    private final String HOME_LOCATION = "Moscow";
    private final String ICON_URL_PREFIX = "http://openweathermap.org/img/wn/";
    private final String ICON_URL_POSTFIX = "@2x.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayoutView);

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.main_nav, R.id.options_nav, R.id.developers_nav)
                .setOpenableLayout(drawerLayout)
                .build();

        navController = Navigation.findNavController(this, R.id.fragmentContainer);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        WeatherGetter.getWeather(HOME_LOCATION, this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainer);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: " + getResources().getResourceEntryName(navController.getCurrentDestination().getId()));
        if (navController.getCurrentDestination().getId() == R.id.home_nav) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.exit))
                    .setPositiveButton("Yes", (dialog, which) -> this.finishAffinity())
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel()).show();
        } else {
            Navigation.findNavController(this, R.id.fragmentContainer).popBackStack();
        }
    }

    @SuppressLint("DefaultLocale")
    public void setupNavHeader(WeatherRequest weatherRequest) {
        View headerView = navigationView.getHeaderView(0);
        ((TextView) headerView.findViewById(R.id.navHeaderLocationView)).setText(weatherRequest.getName());

        String weatherStatusValue = String.valueOf(weatherRequest.getWeather()[0].getDescription());
        weatherStatusValue = weatherStatusValue.substring(0, 1).toUpperCase() + weatherStatusValue.substring(1);
        ((TextView) headerView.findViewById(R.id.navWeatherStatus)).setText(weatherStatusValue);

        ((TextView) headerView.findViewById(R.id.navMoistureValue)).setText(String.format("%d", weatherRequest.getMain().getHumidity()));
        ((TextView) headerView.findViewById(R.id.navPressureValue)).setText(String.format("%d", weatherRequest.getMain().getPressure()));
        ((TextView) headerView.findViewById(R.id.navWindValue)).setText(String.format("%.1f", weatherRequest.getWind().getSpeed()));

        String icon = weatherRequest.getWeather()[0].getIcon();
        Picasso.get().load(ICON_URL_PREFIX + icon + ICON_URL_POSTFIX).into(((ImageView) headerView.findViewById(R.id.navHeaderIconView)));

    }
}