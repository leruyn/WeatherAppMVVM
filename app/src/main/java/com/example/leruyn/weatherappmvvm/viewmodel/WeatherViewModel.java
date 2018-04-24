package com.example.leruyn.weatherappmvvm.viewmodel;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.leruyn.weatherappmvvm.model.City;
import com.example.leruyn.weatherappmvvm.model.WeatherModel;

import java.util.Observable;

/**
 * Created by LeRuyn on 4/21/2018.
 */
public class WeatherViewModel extends Observable {
    private WeatherModel weatherModel;
    private City city;
    private Context context;

    public WeatherViewModel(@NonNull Context context, WeatherModel weatherModel, City city) {

        this.context = context;
        this.weatherModel = weatherModel;
        this.city = city;

    }


    public String getAddress() {
        return city.name
                + ", "
                + city.country;
    }

    public String getTemp() {
        return weatherModel.getMain().getTemp() / 10 + "\\u00B0C";
    }


    public void reset() {
        context = null;
    }
}
