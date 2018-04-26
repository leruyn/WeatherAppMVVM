package com.example.leruyn.weatherappmvvm.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.leruyn.weatherappmvvm.R;
import com.example.leruyn.weatherappmvvm.WeatherApplication;
import com.example.leruyn.weatherappmvvm.databinding.WeatherActivityBinding;
import com.example.leruyn.weatherappmvvm.model.City;
import com.example.leruyn.weatherappmvvm.model.WeatherModel;
import com.example.leruyn.weatherappmvvm.utils.eventbus.Events;
import com.example.leruyn.weatherappmvvm.viewmodel.WeatherViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Observable;
import java.util.Observer;

public class WeatherActivity extends AppCompatActivity implements Observer {
    private WeatherViewModel weatherViewModel;
    private WeatherActivityBinding weatherActivityBinding;
    private WeatherApplication weatherApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        weatherActivityBinding = DataBindingUtil.setContentView(this, R.layout.weather_activity);
        weatherApplication = new WeatherApplication();
        initDataBinding();

    }

    private void initDataBinding() {
        WeatherModel weatherModel = weatherApplication.weatherDao.getInfoWeather();
        City city = weatherApplication.weatherDao.getInfoLocation();
        weatherViewModel = new WeatherViewModel(this, weatherModel, city);
        weatherActivityBinding.setWeatherViewModel(weatherViewModel);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        weatherViewModel.reset();
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof WeatherViewModel) {
            WeatherViewModel weatherViewModel = (WeatherViewModel) observable;
        }
    }



}
