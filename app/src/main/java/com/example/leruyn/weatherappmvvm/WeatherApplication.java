package com.example.leruyn.weatherappmvvm;

import android.app.Application;
import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

import com.example.leruyn.weatherappmvvm.data.WeatherFactory;
import com.example.leruyn.weatherappmvvm.data.WeatherResponse;
import com.example.leruyn.weatherappmvvm.data.WeatherService;
import com.example.leruyn.weatherappmvvm.data.dao.WeatherDao;
import com.example.leruyn.weatherappmvvm.model.City;

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

    private WeatherService weatherService;
    private Scheduler scheduler;
    private static Context contextApplication;
    private static WeatherDao weatherDao;

    private static WeatherApplication get(Context context) {

        return (WeatherApplication) context.getApplicationContext();
    }

    public static WeatherApplication create(Context context) {
        contextApplication = context;
        weatherDao = new WeatherDao(context);
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

    public void fetchWeatherList(String lat, String lon) {

        Call<WeatherResponse> call = weatherService.getWeatherData(lat, lon);
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
                Log.e(TAG, "request failed");
            }
        });
    }

}
