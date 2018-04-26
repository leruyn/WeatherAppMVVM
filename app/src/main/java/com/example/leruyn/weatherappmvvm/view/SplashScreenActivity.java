package com.example.leruyn.weatherappmvvm.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import com.example.leruyn.weatherappmvvm.R;
import com.example.leruyn.weatherappmvvm.utils.DialogUtils;
import com.example.leruyn.weatherappmvvm.utils.eventbus.Events;
import com.example.leruyn.weatherappmvvm.utils.eventbus.GlobalBus;
import com.example.leruyn.weatherappmvvm.viewmodel.SplashScreenModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by LeRuyn on 4/22/2018.
 */
public class SplashScreenActivity extends AppCompatActivity implements Observer {
    private SplashScreenModel splashScreenModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initDataBinding();

        DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);
    }

    private void initDataBinding() {
        if (!GlobalBus.getBus().isRegistered(this))
            GlobalBus.getBus().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        splashScreenModel = new SplashScreenModel(this);
        setupObserver(splashScreenModel);
    }

    public void setupObserver(Observable observable) {
        observable.addObserver(this);
        if (observable instanceof SplashScreenModel) {
            SplashScreenModel splashScreenModel = (SplashScreenModel) observable;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashScreenModel.reset();

    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof SplashScreenModel) {
            SplashScreenModel splashScreenModel = (SplashScreenModel) observable;
        }
    }

    @Subscribe()
    public void insertDataBase(Events.insertDataBase insertDataBase) {
        if (GlobalBus.getBus().isRegistered(this))
            GlobalBus.getBus().unregister(this);

        new CountDownTimer(5000, 1000) {
            public void onTick(long ms) {
            }

            public void onFinish() {
                Intent intent = new Intent(SplashScreenActivity.this, WeatherActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }.start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeStatusGPS(Events.changeStatusGPS changeStatusGPS) {
        if (changeStatusGPS.getStatus()) {
            if (splashScreenModel.gpsDialog != null) {
                if (splashScreenModel.gpsDialog.isShowing()) {
                    splashScreenModel.gpsDialog.dismiss();
                }
            }
            if (splashScreenModel != null) {
                splashScreenModel.fetchWeatherList(this);
            }
        } else {
            if (splashScreenModel.gpsDialog == null) {
                splashScreenModel.gpsDialog = DialogUtils.buildAlertMessageNoGps(this);
                splashScreenModel.gpsDialog.show();
            } else {
                if (splashScreenModel.gpsDialog.isShowing()) {
                    splashScreenModel.gpsDialog.show();
                }
            }
        }
    }


}
