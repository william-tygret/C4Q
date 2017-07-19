package com.williamtygret.weatherc4q;

import android.media.Image;

/**
 * Created by williamtygret on 7/19/17.
 */
public class Forecast {


        String mDay;
        int mHigh;
        int mLow;


        Image mWeatherIcon;

        public Forecast(String day, int high, int low, Image weatherIcon){
            this.mDay = day;
            this.mHigh = high;
            this.mLow = low;
            this.mWeatherIcon = weatherIcon;
        }
        public String toString(){
            return mDay;
        }

        public String getDay() {
            return mDay;
        }
        public void setDay(String day) {
            mDay = day;
        }
        public int getHigh() {
            return mHigh;
        }
        public void setHigh(int high) {
            mHigh = high;
        }
        public int getLow() {
            return mLow;
        }
        public void setLow(int low) {
            mLow = low;
        }

        public Image getWeatherIcon() {
            return mWeatherIcon;
        }

        public void setWeatherIcon(Image weatherIcon) {
            mWeatherIcon = weatherIcon;
        }

}
