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

public class football extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView weather = (TextView) findViewById(R.id.footballtext);


        new Thread(new Runnable() {
            @Override
            public void run() {

                JSONObject getJSON = null;
                StringBuffer json = null;

                URL url = null;
                String weather_api = "http://api.football-data.org/v1/competitions/398/teams";
                try {
                    url = new URL(weather_api);
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection)url.openConnection();
                    connection.addRequestProperty("X-Auth-Token", "4257c35ddd40474ebb5f2b5c63391c65");

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
                            weather.setText("Count : " + finalGetJSON.getString("count"));
                            weather.append("\n");
                            weather.append("Team name : " + finalGetJSON.getJSONArray("teams").getJSONObject(1).getString("name"));
                            weather.append("\n");

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
