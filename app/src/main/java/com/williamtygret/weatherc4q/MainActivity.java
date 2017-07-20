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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView mListView;
    ArrayList<Forecast> mForecasts;
    ForecastAdapter mForecastAdapter;
    FrameLayout mFrameLayout;
    TextView mDayViewerTextView;
    TextView mHighViewerTextView;
    TextView mLowViewerTextView;
    ImageView mImageViewViewer;

    Button mButton;

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

        mButton = (Button)findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadAsyncWeather downloadAsyncWeather = new DownloadAsyncWeather();
                downloadAsyncWeather.execute(urlWeather);
            }
        });

    }

    public class DownloadAsyncWeather extends AsyncTask<String, Void, String> {

        String data;
        int maxTemp;
        int minTemp;
        String date;
        JSONObject theObject;


        ArrayList<Integer> maxArray=new ArrayList<>();
        ArrayList<Integer> minArray=new ArrayList<>();

        ArrayList<String> dateArray = new ArrayList<>();

        String dateForecast;
        int maxForecast;
        int minForecast;


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
                for(int counter=0;counter<periods.length();counter++){
                    JSONObject theObject = periods.getJSONObject(counter);
                    Log.d("theobject","this should be an array of data: "+theObject);

                    dateForecast = theObject.getString("validTime");
                    maxForecast = theObject.getInt("maxTempF");
                    minForecast = theObject.getInt("minTempF");

                    Forecast coolGuyForecast = new Forecast("",0,0,null);
                    coolGuyForecast.setDay(dateForecast);
                    coolGuyForecast.setHigh(maxForecast);
                    coolGuyForecast.setLow(minForecast);

                    mForecasts.add(coolGuyForecast);

                    Log.d("coolguy","cool guy forecast is: "+coolGuyForecast);

                }
                JSONObject firstObj = periods.getJSONObject(0);
                Log.d("zerospace","the first object in the array is:" +firstObj);
                maxTemp = firstObj.getInt("maxTempF");
                Log.d("max1","maxtemp right now is: "+maxTemp);
                minTemp = firstObj.getInt("minTempF");
                date = firstObj.getString("validTime");


                for(int counter=0;counter<itemsArray.length();counter++){
                    theObject = itemsArray.optJSONObject(counter);
                    Log.d("theObject","the object is: "+theObject);
//                    dateArray.add(theObject.getString("validTime"));
//                    maxArray.add(theObject.getInt("maxTempF"));
//                    minArray.add(theObject.getInt("minTempF"));
                    dateForecast = theObject.getString("validTime");
                    maxForecast = theObject.getInt("maxTempF");
                    minForecast = theObject.getInt("minTempF");
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
    }
}
