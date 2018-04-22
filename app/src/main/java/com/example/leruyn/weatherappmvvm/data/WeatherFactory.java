package com.example.leruyn.weatherappmvvm.data;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LeRuyn on 4/21/2018.
 */
public class WeatherFactory {
    public final static String BASE_URL = "http://api.openweathermap.org/";
    public final static String LOAD_DATA_URL = "http://api.openweathermap.org/data/2.5/forecast?id=1580578&APPID=1681c86b85f0f0875dd29c31488e6069";

    public static WeatherService create() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(WeatherService.class);
    }
}
