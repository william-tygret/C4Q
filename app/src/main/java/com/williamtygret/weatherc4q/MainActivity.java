package com.williamtygret.weatherc4q;


import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ListView mListView;
    ArrayList<Forecast> mForecasts;
    ForecastAdapter mForecastAdapter;
    FrameLayout mFrameLayout;
    TextView mDayViewerTextView;
    TextView mHighViewerTextView;
    TextView mLowViewerTextView;
    ImageView mImageViewViewer;
    TextView mDescText;

    ImageView mImageView;


    private String urlWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView)findViewById(R.id.listView);

        mFrameLayout = (FrameLayout)findViewById(R.id.frameLayout);
        mDayViewerTextView = (TextView)findViewById(R.id.textViewDayViewer);
        mHighViewerTextView = (TextView)findViewById(R.id.textViewHighViewer);
        mLowViewerTextView = (TextView)findViewById(R.id.textViewLowViewer);
        mImageViewViewer = (ImageView)findViewById(R.id.imageViewViewer);
        mDescText = (TextView)findViewById(R.id.descText);

        mImageView = (ImageView)findViewById(R.id.imageView);

        urlWeather = "http://api.aerisapi.com/forecasts/11101?client_id=5k40NwxrzVaGeYSxkLlQ1&client_secret=Nrx4miNPZg8KKsA6zPjUeMZWDWBrrHMLpyqXwmm4";

        mForecasts = new ArrayList<Forecast>();


        mForecastAdapter = new ForecastAdapter(this, mForecasts);

        mListView.setAdapter(mForecastAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDayViewerTextView.setText(mForecasts.get(position).getDay());
                mLowViewerTextView.setText("" + mForecasts.get(position).getLow());
                mHighViewerTextView.setText("" + mForecasts.get(position).getHigh());
            }
        });

        DownloadAsyncWeather downloadAsyncWeather = new DownloadAsyncWeather();
        downloadAsyncWeather.execute(urlWeather);


    }

    public class DownloadAsyncWeather extends AsyncTask<String, Void, String> {

        String data;
        int maxTemp;
        int minTemp;
        String date;
        String icon;
        JSONObject theObject;
        String desc;

        String dateForecast;
        int maxForecast;
        int minForecast;
        String iconForecast;
        String descForecast;




        @Override
        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inStream = connection.getInputStream();

                data = getInputData(inStream);
                Log.d("StupidGuy", "we got the data " + data);

            } catch (Throwable thro) {
                thro.printStackTrace();
            }


            try {
                JSONObject dataObject = new JSONObject(data);
                JSONArray itemsArray = dataObject.getJSONArray("response");
                Log.d("responseeee","The response is: "+itemsArray);
                JSONObject zero = itemsArray.getJSONObject(0);
                Log.d("zeroooo","zero value is:" +zero);
                JSONArray periods = zero.getJSONArray("periods");
                Log.d("periods","The periods array is: "+periods);
                for(int counter=1;counter<periods.length();counter++){
                    JSONObject theObject = periods.getJSONObject(counter);
                    Log.d("theobject","this should be an array of data: "+theObject);

                    dateForecast = theObject.getString("validTime");
                    maxForecast = theObject.getInt("maxTempF");
                    minForecast = theObject.getInt("minTempF");
                    iconForecast = theObject.getString("icon");
                    descForecast = theObject.getString("weather");

                    if(dateForecast.length()>10){
                        dateForecast = dateForecast.substring(0,10);
                    }





                    Forecast coolGuyForecast = new Forecast("",0,0,null,"");
                    coolGuyForecast.setDay(dateForecast);
                    coolGuyForecast.setHigh(maxForecast);
                    coolGuyForecast.setLow(minForecast);
                    coolGuyForecast.setWeatherIcon(iconForecast);
                    coolGuyForecast.setDesc(descForecast);


                    mForecasts.add(coolGuyForecast);

                    Log.d("coolguy","cool guy forecast is: "+coolGuyForecast);

                }
                JSONObject firstObj = periods.getJSONObject(0);
                Log.d("zerospace","the first object in the array is:" +firstObj);
                maxTemp = firstObj.getInt("maxTempF");
                Log.d("max1","maxtemp right now is: "+maxTemp);
                minTemp = firstObj.getInt("minTempF");
                date = firstObj.getString("validTime");
                icon = firstObj.getString("icon");
                desc = firstObj.getString("weather");
                if(date.length()>10){
                    date = date.substring(0,10);
                }





                for(int counter=0;counter<itemsArray.length();counter++){
                    theObject = itemsArray.optJSONObject(counter);
                    Log.d("theObject","the object is: "+theObject);
                    dateForecast = theObject.getString("validTime");
                    maxForecast = theObject.getInt("maxTempF");
                    minForecast = theObject.getInt("minTempF");
                    iconForecast = theObject.getString("icon");
                    //mForecasts.add(dateForecast,maxForecast,minForecast,null);


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return data;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            mHighViewerTextView.setText("High:" + maxTemp);
            mLowViewerTextView.setText("Low:" + minTemp);
            mDayViewerTextView.setText(date);
            //mImageViewViewer.setImageResource(R.drawable.sunny);
            mDescText.setText(desc);

            Log.d("iconnnnn","the icon is: "+icon);


            if(icon.equals("am_pcloudy.png")){
                mImageViewViewer.setImageResource(R.drawable.am_pcloudy);
            }else if(icon.equals("blizzard.png")) {
                mImageViewViewer.setImageResource(R.drawable.blizzard);
            }else if(icon.equals("blowingsnow.png")){
                mImageViewViewer.setImageResource(R.drawable.blowingsnow);
            }else if(icon.equals("chancetstorm.png")){
                mImageViewViewer.setImageResource(R.drawable.chancetstorm);
            }else if(icon.equals("clear.png")) {
                mImageViewViewer.setImageResource(R.drawable.clear);
            }else if(icon.equals("cloudy.png")){
                mImageViewViewer.setImageResource(R.drawable.cloudy);
            }else if(icon.equals("drizzle.png")){
                mImageViewViewer.setImageResource(R.drawable.drizzle);
            }else if(icon.equals("dust.png")) {
                mImageViewViewer.setImageResource(R.drawable.dust);
            }else if(icon.equals("fair.png")){
                mImageViewViewer.setImageResource(R.drawable.fair);
            }else if(icon.equals("flurries.png")){
                mImageViewViewer.setImageResource(R.drawable.flurries);
            }else if(icon.equals("fog.png")){
                mImageViewViewer.setImageResource(R.drawable.fog);
            }else if(icon.equals("freezingrain.png")) {
                mImageViewViewer.setImageResource(R.drawable.freezingrain);
            }else if(icon.equals("hazy.png")){
                mImageViewViewer.setImageResource(R.drawable.hazy);
            }else if(icon.equals("mcloudy.png")){
                mImageViewViewer.setImageResource(R.drawable.mcloudy);
            }else if(icon.equals("mcloudyr.png")){
                mImageViewViewer.setImageResource(R.drawable.mcloudyr);
            }else if(icon.equals("mcloudyrw.png")) {
                mImageViewViewer.setImageResource(R.drawable.mcloudyrw);
            }else if(icon.equals("mcloudys.png")){
                mImageViewViewer.setImageResource(R.drawable.mcloudys);
            }else if(icon.equals("pcloudy.png")){
                mImageViewViewer.setImageResource(R.drawable.pcloudy);
            }else if(icon.equals("pcloudyr.png")){
                mImageViewViewer.setImageResource(R.drawable.pcloudyr);
            }else if(icon.equals("pcloudys.png")) {
                mImageViewViewer.setImageResource(R.drawable.pcloudys);
            }else if(icon.equals("rain.png")){
                mImageViewViewer.setImageResource(R.drawable.rain);
            }else if(icon.equals("showers.png")){
                mImageViewViewer.setImageResource(R.drawable.showers);
            }else if(icon.equals("sleet.png")){
                mImageViewViewer.setImageResource(R.drawable.sleet);
            }else if(icon.equals("snow.png")) {
                mImageViewViewer.setImageResource(R.drawable.snow);
            }else if(icon.equals("tstorm.png")){
                mImageViewViewer.setImageResource(R.drawable.tstorm);
            }else if(icon.equals("wind.png")) {
                mImageViewViewer.setImageResource(R.drawable.wind);
            }else if(icon.equals("sunny.png")) {
                mImageViewViewer.setImageResource(R.drawable.sunny);
            }else if(icon.equals("pcloudyt.png")) {
                mImageViewViewer.setImageResource(R.drawable.pcloudyt);
            }else if(icon.equals("mcloudyt.png")) {
                mImageViewViewer.setImageResource(R.drawable.mcloudyt);
            }


            mForecastAdapter.notifyDataSetChanged();

        }

        private String getInputData(InputStream inStream) throws IOException {
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));

            String data;

            while((data = reader.readLine()) != null){
                builder.append(data);
            }

            reader.close();

            return builder.toString();
        }

        public int getResId(String resName, Class<?> c) {

            try {
                Field idField = c.getDeclaredField(resName);
                return idField.getInt(idField);
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }

    }
}
