package com.example.leruyn.weatherappmvvm.viewmodel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.leruyn.weatherappmvvm.WeatherApplication;
import com.example.leruyn.weatherappmvvm.data.WeatherFactory;
import com.example.leruyn.weatherappmvvm.data.WeatherResponse;
import com.example.leruyn.weatherappmvvm.data.WeatherService;
import com.example.leruyn.weatherappmvvm.data.dao.WeatherDao;
import com.example.leruyn.weatherappmvvm.utils.DialogUtils;
import com.example.leruyn.weatherappmvvm.utils.InternetUtils;
import com.example.leruyn.weatherappmvvm.utils.listener.MyLocation;
import com.example.leruyn.weatherappmvvm.utils.listener.NetworkStateReceiver;

import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * Created by leruy on 4/26/2018.
 */

public class BaseViewModel extends Observable implements NetworkStateReceiver.NetworkStateReceiverListener {

    Context context;
    public NetworkStateReceiver networkStateReceiver;
    public WeatherApplication weatherApplication;



    public static WeatherDao weatherDao;

    private static LocationManager manager;
    private static WeatherService weatherService;


    public AlertDialog gpsDialog, networkDialog;

    public BaseViewModel(@NonNull Context context) {
        this.context = context;
        weatherApplication = new WeatherApplication();
        if (weatherService == null) {
            weatherService = WeatherFactory.create();
        }
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);

        context.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        weatherDao = new WeatherDao(context);
        manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);


    }


    @Override
    public void networkAvailable() {
        if (networkDialog!=null) {
            if (networkDialog.isShowing()) {
                networkDialog.dismiss();
            }
        }
    }

    @Override
    public void networkUnavailable() {
        if (networkDialog == null) {
            networkDialog = DialogUtils.buildAlertMessageNoInternet(context);
            networkDialog.show();
        } else {
            if (!networkDialog.isShowing()) {
                networkDialog.show();
            }
        }

    }

    public void fetchWeatherList(Context context) {

        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (InternetUtils.isNetworkAvailable(context)) {
                MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
                    @Override
                    public void gotLocation(final Location location) {
                        System.out.println("Latitude: " + location.getLatitude());
                        System.out.println("Longitude: " + location.getLongitude());

                        Call<WeatherResponse> call = weatherService.getWeatherData(location.getLatitude(), location.getLongitude());
                        call.enqueue(new Callback<WeatherResponse>() {
                            @Override
                            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                                Log.d(TAG, "request success");
                                if (response.body().getList() != null) {
                                    weatherDao.deleteTable();
                                    weatherDao.addWeatherTable(response.body());
                                }
                            }

                            @Override
                            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                                Log.e(TAG, t.toString());
                            }
                        });
                    }
                };

                MyLocation myLocation = new MyLocation();
                myLocation.getLocation(context, locationResult);
            } else {
                if (networkDialog == null || !networkDialog.isShowing()) {
                    networkDialog = DialogUtils.buildAlertMessageNoInternet(context);
                    networkDialog.show();
                }

            }
        } else {
            if (gpsDialog == null || !gpsDialog.isShowing()) {
                gpsDialog = DialogUtils.buildAlertMessageNoGps(context);
                gpsDialog.show();
            }

        }
    }

}
