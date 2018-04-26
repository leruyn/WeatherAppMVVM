package com.example.leruyn.weatherappmvvm.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.leruyn.weatherappmvvm.data.WeatherResponse;
import com.example.leruyn.weatherappmvvm.data.helper.DatabaseHandler;
import com.example.leruyn.weatherappmvvm.model.City;
import com.example.leruyn.weatherappmvvm.model.Main;
import com.example.leruyn.weatherappmvvm.model.WeatherModel;
import com.example.leruyn.weatherappmvvm.utils.eventbus.Events;
import com.example.leruyn.weatherappmvvm.utils.eventbus.GlobalBus;

/**
 * Created by leruy on 4/23/2018.
 */

public class WeatherDao extends DatabaseHandler {
    public WeatherDao(Context context) {
        super(context);
    }

    public void addWeatherTable(WeatherResponse weatherResponse) {
        WeatherModel weatherModel = weatherResponse.getList().get(0);
        City city = weatherResponse.getCity();
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_DT, weatherModel.getDt());
            values.put(KEY_TEMP, weatherModel.getMain().getTemp());
            values.put(KEY_TEMP_MIN, weatherModel.getMain().getTempMin());
            values.put(KEY_TEMP_MAX, weatherModel.getMain().getTempMax());
            values.put(KEY_PRESSURE, weatherModel.getMain().getPressure());
            values.put(KEY_SEA_LEVEL, weatherModel.getMain().getSeaLevel());
            values.put(KEY_GRND_LEVEL, weatherModel.getMain().getGrndLevel());
            values.put(KEY_HUMIDITY, weatherModel.getMain().getHumidity());
            values.put(KEY_TEMP_KF, weatherModel.getMain().getTempKf());
            values.put(KEY_DT_TXT, weatherModel.getDtTxt());
            values.put(KEY_NAME, city.getName());
            values.put(KEY_COUNTRY, city.getCountry());
            // Inserting Row
            Log.e("RLVVVVVV ", db.insert(TABLE_WEATHER, null, values) + "");

            Events.insertDataBase insertDataBase =
                    new Events.insertDataBase(weatherResponse);
            GlobalBus.getBus().post(insertDataBase);
        } catch (Exception e) {
            Events.insertDataBase insertDataBase =
                    new Events.insertDataBase(null);
            GlobalBus.getBus().post(insertDataBase);
            e.printStackTrace();
        }
    }


    /**
     * Get info user
     *
     * @return
     */
    public City getInfoLocation() {
        // Select All Query
        String selectQuery = "SELECT  "+KEY_NAME+", "+ KEY_COUNTRY+" FROM " + TABLE_WEATHER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        City cityResponse = new City();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                cityResponse.setName(cursor.getString(0));
                cityResponse.setCountry(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        return cityResponse;

    }


    public WeatherModel getInfoWeather() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WEATHER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        WeatherModel weatherModel = new WeatherModel();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                weatherModel.setDt(Long.valueOf(cursor.getString(1)));

                Main main = new Main();
                main.setTemp(Double.valueOf(cursor.getString(2)));
                main.setTempMin(Double.valueOf(cursor.getString(3)));
                main.setTempMax(Double.valueOf(cursor.getString(4)));
                main.setPressure(Double.valueOf(cursor.getString(5)));
                main.setSeaLevel(Double.valueOf(cursor.getString(6)));
                main.setGrndLevel(Double.valueOf(cursor.getString(7)));
                main.setHumidity(Long.valueOf(cursor.getString(8)));
                main.setTempKf(Double.valueOf(cursor.getString(9)));
                weatherModel.setMain(main);

            } while (cursor.moveToNext());
            cursor.close();
        }
        return weatherModel;

    }

    /**
     * Delete table
     */
    public void deleteTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WEATHER, null, null);
    }
}
