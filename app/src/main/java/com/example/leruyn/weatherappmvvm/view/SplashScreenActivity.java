package com.example.leruyn.weatherappmvvm.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.leruyn.weatherappmvvm.R;
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
        setupObserver(splashScreenModel);

        DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);
    }

    private void initDataBinding() {
        if (!GlobalBus.getBus().isRegistered(this))
            GlobalBus.getBus().register(this);
        splashScreenModel = new SplashScreenModel(this);
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
        if (!GlobalBus.getBus().isRegistered(this))
            GlobalBus.getBus().unregister(this);
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof SplashScreenModel) {
            SplashScreenModel splashScreenModel = (SplashScreenModel) observable;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void insertDataBase(Events.insertDataBase insertDataBase) {
        if (insertDataBase.getStatus()) {
            startActivity(new Intent(this, WeatherActivity.class));
            finish();
        }
    }
}
