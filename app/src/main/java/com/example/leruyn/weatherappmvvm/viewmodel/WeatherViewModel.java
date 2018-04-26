package com.example.leruyn.weatherappmvvm.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.example.leruyn.weatherappmvvm.R;
import com.example.leruyn.weatherappmvvm.model.City;
import com.example.leruyn.weatherappmvvm.model.WeatherModel;
import com.example.leruyn.weatherappmvvm.utils.DateUtils;
import com.example.leruyn.weatherappmvvm.utils.DialogUtils;
import com.example.leruyn.weatherappmvvm.utils.eventbus.Events;
import com.example.leruyn.weatherappmvvm.utils.eventbus.GlobalBus;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by LeRuyn on 4/21/2018.
 */
public class WeatherViewModel extends BaseViewModel {
    private WeatherModel weatherModel;
    private City city;
    private Context context;
    public ObservableBoolean isLoading = new ObservableBoolean();

    public WeatherViewModel(@NonNull Context context, WeatherModel weatherModel, City city) {
        super(context);
        this.context = context;
        this.weatherModel = weatherModel;
        this.city = city;
        if (!GlobalBus.getBus().isRegistered(this))
            GlobalBus.getBus().register(this);

    }


    public String getAddress() {
        return city.name
                + ", "
                + city.country;
    }

    @SuppressLint("StringFormatMatches")
    public String getTemp() {
        return String.format(context.getResources().getString(R.string.temp_value), weatherModel.getMain().getTemp() - 273.15);
    }

    public String getDt() {
        return DateUtils.formatTimeByIso8601UTC(weatherModel.getDt() * 1000, context);
    }


    public void reset() {
        context = null;
        if (networkStateReceiver != null) {
            context.unregisterReceiver(networkStateReceiver);
        }
        if (GlobalBus.getBus().isRegistered(this))
            GlobalBus.getBus().unregister(this);
    }


    /* Needs to be public for Databinding */
    public void onRefresh() {
        isLoading.set(true);
        fetchWeatherList(context);
    }


    @Override
    public void networkAvailable() {
        super.networkAvailable();
        fetchWeatherList(context);
    }

    @Override
    public void networkUnavailable() {
        super.networkUnavailable();
        isLoading.set(false);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void insertDataBase(Events.insertDataBase insertDataBase) {
        weatherModel = insertDataBase.getWeatherResponse().getList().get(0);
        city = insertDataBase.getWeatherResponse().getCity();
        isLoading.set(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeStatusGPS(Events.changeStatusGPS changeStatusGPS) {
        if (changeStatusGPS.getStatus()) {
            if (gpsDialog != null) {
                if (gpsDialog.isShowing()) {
                    gpsDialog.dismiss();
                }
            }
            fetchWeatherList(context);
        } else {
            if (gpsDialog == null) {
                gpsDialog = DialogUtils.buildAlertMessageNoGps(context);
                gpsDialog.show();
            } else {
                if (gpsDialog.isShowing()) {
                    gpsDialog.show();
                }
            }
        }
    }
}
