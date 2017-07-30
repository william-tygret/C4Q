package com.williamtygret.weatherc4q;

import android.media.Image;

/**
 * Created by williamtygret on 7/19/17.
 */
public class Forecast {


        String mDay;
        int mHigh;
        int mLow;
        String mWeatherIcon;
        String mDesc;



    public Forecast(String day, int high, int low, String weatherIcon, String desc){
            this.mDay = day;
            this.mHigh = high;
            this.mLow = low;
            this.mWeatherIcon = weatherIcon;
            this.mDesc = desc;

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

        public String getWeatherIcon() {
            return mWeatherIcon;
        }

        public void setWeatherIcon(String weatherIcon) {
            mWeatherIcon = weatherIcon;
        }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String desc) {
        mDesc = desc;
    }

}
