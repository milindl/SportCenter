package com.iitk.sportcenter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class weather extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final TextView weather = (TextView) findViewById(R.id.kitty);


        new Thread(new Runnable() {
            @Override
            public void run() {

                JSONObject getJSON = null;
                StringBuffer json = null;

                URL url = null;
                String weather_api = "http://api.openweathermap.org/data/2.5/weather?q=Kanpur&appid=d2c631d5dea854eb3b95317435053147";
                try {
                    url = new URL(weather_api);
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection)url.openConnection();
                    connection.addRequestProperty("x-api-key", "d2c631d5dea854eb3b95317435053147");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    json = new StringBuffer(1024);
                    String tmp = "";
                    while ((tmp=reader.readLine())!=null){
                        json.append(tmp).append('\n');
                    }

                    reader.close();

                    getJSON = new JSONObject(json.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final JSONObject finalGetJSON = getJSON;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            weather.setText("COD : " + finalGetJSON.getString("cod"));
                            weather.append("\n");
                            weather.append("Temperature : " + finalGetJSON.getJSONObject("main").getString("temp"));
                            weather.append("\n");
                            weather.append("City : " + finalGetJSON.getString("name"));
                            weather.append("\n");
                            weather.append("Pressure : " + finalGetJSON.getJSONObject("main").getString("pressure"));
                            weather.append("\n" + "Humidity : " + finalGetJSON.getJSONObject("main").getString("humidity"));
                            weather.append("\n" + "Weather : " + finalGetJSON.getJSONArray("weather").getJSONObject(0).getString("description"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

}
