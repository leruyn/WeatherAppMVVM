package com.example.leruyn.weatherappmvvm.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import com.example.leruyn.weatherappmvvm.utils.eventbus.Events;
import com.example.leruyn.weatherappmvvm.utils.eventbus.GlobalBus;

/**
 * Created by leruy on 4/23/2018.
 */

public class GPSCheck extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {


        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Events.changeStatusGPS changeStatusGPS =
                    new Events.changeStatusGPS(true);
            GlobalBus.getBus().post(changeStatusGPS);

        } else {
            Events.changeStatusGPS changeStatusGPS =
                    new Events.changeStatusGPS(false);
            GlobalBus.getBus().post(changeStatusGPS);

        }


    }
}
