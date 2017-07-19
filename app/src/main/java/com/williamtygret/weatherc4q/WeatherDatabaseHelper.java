package com.williamtygret.weatherc4q;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by williamtygret on 7/19/17.
 */
public class WeatherDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "weatherlibrary.db";
    public static final String TABLE_NAME = "WEATHER_LIBRARY";

    public static final String COL_ID = "ID";
    public static final String COL_DATE = "DATE";
    public static final String COL_MAXTEMP = "MAXTEMP";
    public static final String COL_MINTEMP = "MINTEMP";

    private Context mHelperContext;

    public WeatherDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        mHelperContext = context;
    }

    public static final String [] WEATHER_COLUMNS = {COL_ID,COL_DATE,COL_MAXTEMP,COL_MINTEMP};

    //make db accessible in a singleton
    private static WeatherDatabaseHelper instance;

    public static WeatherDatabaseHelper getInstance(Context context){
        if(instance == null){
            instance = new WeatherDatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, DATE TEXT, MAXTEMP TEXT, MINTEMP TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);

    }
}
