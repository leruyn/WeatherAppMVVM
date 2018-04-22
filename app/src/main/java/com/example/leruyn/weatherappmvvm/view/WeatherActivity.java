package com.example.leruyn.weatherappmvvm.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.leruyn.weatherappmvvm.R;

import java.util.Observable;
import java.util.Observer;

public class WeatherActivity extends AppCompatActivity implements Observer {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
