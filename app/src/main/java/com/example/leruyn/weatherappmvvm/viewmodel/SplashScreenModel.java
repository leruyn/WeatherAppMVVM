package com.example.leruyn.weatherappmvvm.viewmodel;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.ObservableInt;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import com.example.leruyn.weatherappmvvm.WeatherApplication;
import com.example.leruyn.weatherappmvvm.data.WeatherResponse;
import com.example.leruyn.weatherappmvvm.data.WeatherService;
import com.example.leruyn.weatherappmvvm.model.City;
import com.example.leruyn.weatherappmvvm.model.WeatherModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LeRuyn on 4/22/2018.
 */
public class SplashScreenModel extends Observable {

    private static final String TAG = "RLV";
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 60;

    private List<WeatherModel> weatherList;
    private Context context;

    public ObservableInt weatherProgress;

    private Call<WeatherResponse> call;

    private LocationManager lm;
    private LocationListener ll;

    String lat, lon;


    public SplashScreenModel(@NonNull Context context) {

        this.context = context;
        this.weatherList = new ArrayList<>();
        weatherProgress = new ObservableInt(View.VISIBLE);

        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
        ll = new myLocationListener();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, 0, ll);
    }

    public void fetchWeatherList() {

        WeatherApplication weatherApplication = WeatherApplication.create(context);
        WeatherService weatherService = weatherApplication.getWeatherService();


        call = weatherService.getWeatherData(lat, lon);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                Log.d(TAG, "request success");
                if (response.body().getList() != null) {
                    weatherList = response.body().getList();
                    City city = response.body().getCity();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e(TAG, "request failed");
            }
        });
    }


    public void reset() {
        context = null;
        call.cancel();
    }

    class myLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub
            if (location != null) {
                double pLat = location.getLatitude();
                double pLong = location.getLongitude();
                lat = (Double.toString(pLat));
                lon = (Double.toString(pLong));
                fetchWeatherList();
            }
        }


        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }


        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }

    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        turnGPSOn();
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

    private void turnGPSOn() {
        String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (!provider.contains("gps")) { //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            context.sendBroadcast(poke);
        }
    }

}
