package com.williamtygret.weatherc4q;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Ref;
import java.util.ArrayList;

/**
 * Created by williamtygret on 7/19/17.
 */
public class ForecastAdapter extends ArrayAdapter<Forecast> {

    private final Context context;
    private ArrayList<Forecast> mForecasts;

    public ForecastAdapter(Context context, ArrayList<Forecast>forecasts){
        super(context, -1);
        mForecasts = forecasts;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Forecast currentForecast = mForecasts.get(position);
        Log.d("currentforecast","current forecast is: "+currentForecast.getWeatherIcon());



        Context context = getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View forecastLayoutView = inflater.inflate(R.layout.forecast_list_item, parent, false);

        TextView forecastDayTextView = (TextView)forecastLayoutView.findViewById(R.id.textViewDay);
        TextView forecastHighTextView = (TextView)forecastLayoutView.findViewById(R.id.textViewHigh);
        TextView forecastLowTextView = (TextView)forecastLayoutView.findViewById(R.id.textViewLow);
        ImageView forecastImageView = (ImageView)forecastLayoutView.findViewById(R.id.imageView);

        forecastDayTextView.setText(" "+ currentForecast.getDay());
        forecastHighTextView.setText(" " + currentForecast.getHigh());
        forecastLowTextView.setText(" " + currentForecast.getLow());
        String iconstring = String.valueOf(currentForecast.getWeatherIcon());
        forecastImageView.setImageResource(getImageId(iconstring));

        if(currentForecast.getWeatherIcon().equals("am_pcloudy.png")){
                forecastImageView.setImageResource(R.drawable.am_pcloudy);
            }else if(currentForecast.getWeatherIcon().equals("blizzard.png")) {
            forecastImageView.setImageResource(R.drawable.blizzard);
            }else if(currentForecast.getWeatherIcon().equals("blowingsnow.png")){
            forecastImageView.setImageResource(R.drawable.blowingsnow);
            }else if(currentForecast.getWeatherIcon().equals("chancetstorm.png")){
            forecastImageView.setImageResource(R.drawable.chancetstorm);
            }else if(currentForecast.getWeatherIcon().equals("clear.png")) {
            forecastImageView.setImageResource(R.drawable.clear);
            }else if(currentForecast.getWeatherIcon().equals("cloudy.png")){
            forecastImageView.setImageResource(R.drawable.cloudy);
            }else if(currentForecast.getWeatherIcon().equals("drizzle.png")){
            forecastImageView.setImageResource(R.drawable.drizzle);
            }else if(currentForecast.getWeatherIcon().equals("dust.png")) {
            forecastImageView.setImageResource(R.drawable.dust);
            }else if(currentForecast.getWeatherIcon().equals("fair.png")){
            forecastImageView.setImageResource(R.drawable.fair);
            }else if(currentForecast.getWeatherIcon().equals("flurries.png")){
            forecastImageView.setImageResource(R.drawable.flurries);
            }else if(currentForecast.getWeatherIcon().equals("fog.png")){
            forecastImageView.setImageResource(R.drawable.fog);
            }else if(currentForecast.getWeatherIcon().equals("freezingrain.png")) {
            forecastImageView.setImageResource(R.drawable.freezingrain);
            }else if(currentForecast.getWeatherIcon().equals("hazy.png")){
            forecastImageView.setImageResource(R.drawable.hazy);
            }else if(currentForecast.getWeatherIcon().equals("mcloudy.png")){
            forecastImageView.setImageResource(R.drawable.mcloudy);
            }else if(currentForecast.getWeatherIcon().equals("mcloudyr.png")){
            forecastImageView.setImageResource(R.drawable.mcloudyr);
            }else if(currentForecast.getWeatherIcon().equals("mcloudyrw.png")) {
            forecastImageView.setImageResource(R.drawable.mcloudyrw);
            }else if(currentForecast.getWeatherIcon().equals("mcloudys.png")){
            forecastImageView.setImageResource(R.drawable.mcloudys);
            }else if(currentForecast.getWeatherIcon().equals("pcloudy.png")){
            forecastImageView.setImageResource(R.drawable.pcloudy);
            }else if(currentForecast.getWeatherIcon().equals("pcloudyr.png")){
            forecastImageView.setImageResource(R.drawable.pcloudyr);
            }else if(currentForecast.getWeatherIcon().equals("pcloudys.png")) {
                forecastImageView.setImageResource(R.drawable.pcloudys);
            }else if(currentForecast.getWeatherIcon().equals("rain.png")){
            forecastImageView.setImageResource(R.drawable.rain);
            }else if(currentForecast.getWeatherIcon().equals("showers.png")){
            forecastImageView.setImageResource(R.drawable.showers);
            }else if(currentForecast.getWeatherIcon().equals("sleet.png")){
            forecastImageView.setImageResource(R.drawable.sleet);
            }else if(currentForecast.getWeatherIcon().equals("snow.png")) {
            forecastImageView.setImageResource(R.drawable.snow);
            }else if(currentForecast.getWeatherIcon().equals("tstorm.png")){
            forecastImageView.setImageResource(R.drawable.tstorm);
            }else if(currentForecast.getWeatherIcon().equals("wind.png")) {
            forecastImageView.setImageResource(R.drawable.wind);
            }else if(currentForecast.getWeatherIcon().equals("sunny.png")) {
            forecastImageView.setImageResource(R.drawable.sunny);
            }else if(currentForecast.getWeatherIcon().equals("pcloudyt.png")) {
            forecastImageView.setImageResource(R.drawable.pcloudyt);
            }else if(currentForecast.getWeatherIcon().equals("mcloudyt.png")) {
            forecastImageView.setImageResource(R.drawable.mcloudyt);
            }


        return forecastLayoutView;
    }

    public int getImageId(String imageName) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }

    @Override
    public int getCount() {
        return mForecasts.size();
    }

    @Override
    public Forecast getItem(int position){
        if(this.mForecasts != null){
            return this.mForecasts.get(position);
        }else{
            return null;
        }
    }
}