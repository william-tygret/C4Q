package com.williamtygret.weatherc4q;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by williamtygret on 7/19/17.
 */
public class ForecastAdapter extends ArrayAdapter<Forecast> {
    ArrayList<Forecast> mForecasts;
    public ForecastAdapter(Context context, ArrayList<Forecast>forecasts){
        super(context, -1);
        mForecasts = forecasts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Forecast currentForecast = mForecasts.get(position);

        Context context = getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View forecastLayoutView = inflater.inflate(R.layout.forecast_list_item, parent, false);

        TextView forecastDayTextView = (TextView)forecastLayoutView.findViewById(R.id.textViewDay);
        TextView forecastHighTextView = (TextView)forecastLayoutView.findViewById(R.id.textViewHigh);
        TextView forecastLowTextView = (TextView)forecastLayoutView.findViewById(R.id.textViewLow);
        ImageView forecastImageView = (ImageView)forecastLayoutView.findViewById(R.id.imageView);

        forecastDayTextView.setText(" "+ currentForecast.getDay());
        forecastHighTextView.setText(" "+currentForecast.getHigh());
        forecastLowTextView.setText(" "+currentForecast.getLow());

        return forecastLayoutView;
    }

    @Override
    public int getCount() {
        return mForecasts.size();
    }
}