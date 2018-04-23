package com.example.leruyn.weatherappmvvm.viewmodel;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Observable;

/**
 * Created by LeRuyn on 4/21/2018.
 */
public class WeatherViewModel extends Observable {

    private Context context;

    public WeatherViewModel(@NonNull Context context) {

        this.context = context;


    }


    public void reset() {
        context = null;
    }
}
