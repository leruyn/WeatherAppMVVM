package com.example.leruyn.weatherappmvvm.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.leruyn.weatherappmvvm.R;
import com.example.leruyn.weatherappmvvm.WeatherApplication;
import com.example.leruyn.weatherappmvvm.databinding.ActivityWeatherBinding;
import com.example.leruyn.weatherappmvvm.model.City;
import com.example.leruyn.weatherappmvvm.viewmodel.SplashScreenModel;
import com.example.leruyn.weatherappmvvm.viewmodel.WeatherViewModel;

import java.util.Observable;
import java.util.Observer;

public class WeatherActivity extends AppCompatActivity implements Observer {
    private WeatherViewModel weatherViewModel;

    private WeatherApplication weatherApplication;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataBinding();
        setupObserver(weatherViewModel);

        DataBindingUtil.setContentView(this, R.layout.weather_activity);
    }

    private void initDataBinding() {
        weatherViewModel = new WeatherViewModel(this);
    }


    public void setupObserver(Observable observable) {
        observable.addObserver(this);
        if (observable instanceof SplashScreenModel) {
            WeatherViewModel weatherViewModel = (WeatherViewModel) observable;
            City city = weatherApplication.weatherDao.getInfoLocation();
//            weatherViewModel.weatherTemp.set(city.getCountry()+", "+city.getName());
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof WeatherViewModel) {
            WeatherViewModel weatherViewModel = (WeatherViewModel) observable;
        }
    }
}
