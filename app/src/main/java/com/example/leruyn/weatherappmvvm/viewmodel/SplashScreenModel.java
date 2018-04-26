package com.example.leruyn.weatherappmvvm.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.leruyn.weatherappmvvm.WeatherApplication;
import com.example.leruyn.weatherappmvvm.model.WeatherModel;
import com.example.leruyn.weatherappmvvm.utils.eventbus.Events;
import com.example.leruyn.weatherappmvvm.utils.eventbus.GlobalBus;
import com.example.leruyn.weatherappmvvm.view.WeatherActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LeRuyn on 4/22/2018.
 */
public class SplashScreenModel extends BaseViewModel {

    private static final String TAG = "RLV";
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 0;

    private List<WeatherModel> weatherList;
    private Context context;

    public ObservableInt weatherProgress;

    private WeatherApplication weatherApplication;


    public SplashScreenModel(@NonNull Context context) {
        super(context);
        this.context = context;
        this.weatherList = new ArrayList<>();
        weatherProgress = new ObservableInt(View.VISIBLE);
        weatherApplication = WeatherApplication.create(context);

    }

    @Override
    public void networkAvailable() {
        super.networkAvailable();
        fetchWeatherList(context);
    }

    @Override
    public void networkUnavailable() {
        super.networkUnavailable();
    }


    public void reset() {
        if (!GlobalBus.getBus().isRegistered(this))
            GlobalBus.getBus().unregister(this);
        context = null;
    }






}
