package com.example.leruyn.weatherappmvvm;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.example.leruyn.weatherappmvvm.data.WeatherFactory;
import com.example.leruyn.weatherappmvvm.data.WeatherResponse;
import com.example.leruyn.weatherappmvvm.data.WeatherService;
import com.example.leruyn.weatherappmvvm.data.dao.WeatherDao;
import com.example.leruyn.weatherappmvvm.utils.DialogUtils;
import com.example.leruyn.weatherappmvvm.utils.InternetUtils;
import com.example.leruyn.weatherappmvvm.utils.listener.MyLocation;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * Created by LeRuyn on 4/22/2018.
 */
public class WeatherApplication extends Application {

    private Scheduler scheduler;
    public static WeatherDao weatherDao;

    private static WeatherService weatherService;

    private static WeatherApplication get(Context context) {

        return (WeatherApplication) context.getApplicationContext();
    }

    public static WeatherApplication create(Context context) {
        weatherDao = new WeatherDao(context);
        if (weatherService == null) {
            weatherService = WeatherFactory.create();
        }
        return WeatherApplication.get(context);
    }

    public WeatherService getWeatherService() {
        if (weatherService == null) {
            weatherService = WeatherFactory.create();
        }

        return weatherService;
    }

    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }

    public void setWeatherService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }





}
