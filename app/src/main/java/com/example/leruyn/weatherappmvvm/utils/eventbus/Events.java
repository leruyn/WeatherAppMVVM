package com.example.leruyn.weatherappmvvm.utils.eventbus;


import com.example.leruyn.weatherappmvvm.data.WeatherResponse;

/**
 * Created by HoanVu on 10/10/2017.
 */

public class Events {


    public static class insertDataBase {
        WeatherResponse weatherResponse;

        public insertDataBase(WeatherResponse weatherResponse) {

            this.weatherResponse = weatherResponse;
        }

        public WeatherResponse getWeatherResponse() {
            return weatherResponse;
        }

    }

    public static class changeStatusGPS{
        boolean status;
        public changeStatusGPS(boolean status){
            this.status = status;
        }
        public boolean getStatus(){
            return status;
        }
    }


}
