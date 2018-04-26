package com.example.leruyn.weatherappmvvm.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by leruy on 4/26/2018.
 */

public class BaseActivity extends AppCompatActivity implements Observer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public void update(Observable observable, Object o) {

    }
}
