package com.example.leruyn.weatherappmvvm.data;

import com.example.leruyn.weatherappmvvm.utils.Constant;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by LeRuyn on 4/21/2018.
 */
public interface WeatherService {

    @GET
    Observable<WeatherResponse> fetchWeather(@Url String url);

    @GET(WeatherFactory.BASE_URL + "data/2.5/forecast?cnt=1&APPID="+ Constant.API_KEY)
    Call<WeatherResponse> getWeatherData(@Query("lat") String lat,
                                         @Query("lon") String lon);

}
