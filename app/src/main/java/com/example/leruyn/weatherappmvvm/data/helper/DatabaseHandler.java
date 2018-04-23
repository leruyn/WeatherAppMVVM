package com.example.leruyn.weatherappmvvm.data.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LeRuyn on 4/21/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "weatherData";

    // Contacts table name
    public static final String TABLE_WEATHER = "weather_table";

    /**
     * Product style table
     */
    public static final String KEY_ID = "id";
    public static final String KEY_DT = "weather_dt";
    public static final String KEY_TEMP = "weather_temp";
    public static final String KEY_TEMP_MIN = "weather_temp_min";
    public static final String KEY_TEMP_MAX = "weather_temp_max";
    public static final String KEY_PRESSURE = "weather_pressure";
    public static final String KEY_SEA_LEVEL = "weather_sea_level";
    public static final String KEY_GRND_LEVEL = "weather_grnd_level";
    public static final String KEY_HUMIDITY = "weather_humidity";
    public static final String KEY_TEMP_KF = "weather_temp_kf";
    public static final String KEY_DT_TXT = "weather_dt_txt";
    public static final String KEY_NAME = "weather_name";
    public static final String KEY_COUNTRY = "weather_country";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        /**
         * Create table product style
         */
        String CREATE_WEATHER_TABLE = "CREATE TABLE " + TABLE_WEATHER + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_DT + " TEXT," +
                KEY_TEMP + " TEXT," +
                KEY_TEMP_MIN + " TEXT," +
                KEY_TEMP_MAX + " TEXT," +
                KEY_PRESSURE + " TEXT," +
                KEY_SEA_LEVEL + " TEXT," +
                KEY_GRND_LEVEL + " TEXT," +
                KEY_HUMIDITY + " TEXT," +
                KEY_TEMP_KF + " TEXT," +
                KEY_NAME + " TEXT," +
                KEY_COUNTRY + " TEXT," +
                KEY_DT_TXT + " TEXT" + ")";


        db.execSQL(CREATE_WEATHER_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER);

        // Create tables again
        onCreate(db);
    }


}