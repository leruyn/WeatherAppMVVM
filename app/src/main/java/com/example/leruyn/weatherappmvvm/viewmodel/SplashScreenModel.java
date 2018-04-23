package com.example.leruyn.weatherappmvvm.viewmodel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ObservableInt;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.example.leruyn.weatherappmvvm.WeatherApplication;
import com.example.leruyn.weatherappmvvm.data.WeatherService;
import com.example.leruyn.weatherappmvvm.model.WeatherModel;
import com.example.leruyn.weatherappmvvm.utils.MyLocation;
import com.example.leruyn.weatherappmvvm.utils.eventbus.Events;
import com.example.leruyn.weatherappmvvm.utils.eventbus.GlobalBus;
import com.example.leruyn.weatherappmvvm.view.WeatherActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by LeRuyn on 4/22/2018.
 */
public class SplashScreenModel extends Observable {

    private static final String TAG = "RLV";
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 0;

    private List<WeatherModel> weatherList;
    private Context context;

    public ObservableInt weatherProgress;

    private WeatherApplication weatherApplication;
    private WeatherService weatherService;


    public SplashScreenModel(@NonNull Context context) {

        this.context = context;
        this.weatherList = new ArrayList<>();
        weatherProgress = new ObservableInt(View.VISIBLE);
        weatherApplication = WeatherApplication.create(context);
        weatherService = weatherApplication.getWeatherService();


        // to Find the Location
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
                @Override
                public void gotLocation(final Location location) {
                    System.out.println("Latitude: " + location.getLatitude());
                    System.out.println("Longitude: " + location.getLongitude());

                    weatherApplication.fetchWeatherList(location.getLatitude() + "", location.getLongitude() + "");
                }
            };

            MyLocation myLocation = new MyLocation();
            myLocation.getLocation(context, locationResult);
        } else {
            buildAlertMessageNoGps();
        }


    }


    public void reset() {
        context = null;
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
