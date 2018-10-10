package com.example.serkan.hackernewsfeed;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;

public class FetchAPI extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {
        String result = "";
        try {
            URL sourceURL = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) sourceURL.openConnection();
            connection.connect();
            InputStream input = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(input);
            int data = reader.read();
            while (data != -1) {
                result += (char) data;
                data = reader.read();
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed";
        }
    }
}
