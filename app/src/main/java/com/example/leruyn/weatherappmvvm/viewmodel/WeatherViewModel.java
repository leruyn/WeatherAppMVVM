package com.example.leruyn.weatherappmvvm.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.leruyn.weatherappmvvm.WeatherApplication;

import java.util.Observable;

/**
 * Created by LeRuyn on 4/21/2018.
 */
public class WeatherViewModel extends Observable {

    private Context context;

    public ObservableInt weatherAddress;
    public ObservableInt weatherTemp;

    public WeatherViewModel(@NonNull Context context) {

        this.context = context;

        weatherAddress = new ObservableInt(View.VISIBLE);
        weatherTemp = new ObservableInt(View.VISIBLE);


    }


    public void reset() {
        context = null;
    }
}
