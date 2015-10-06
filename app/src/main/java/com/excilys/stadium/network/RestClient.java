package com.excilys.stadium.network;


import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import timber.log.Timber;

/**
 * Created by excilys on 11/06/15.
 */
public enum RestClient {
    INSTANCE;

    public String executeRequest(String path){
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String data = convert(in);
            if(data.isEmpty()) return null;
           return data;
        } catch (MalformedURLException e) {
            Timber.e(e, "Error Malformed Url");
        } catch (IOException e) {
            Timber.e(e, "Error IO Exception");
        }
        return null;
    }

    private String convert(InputStream is) {
        String line = "";
        StringBuilder builder = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = rd.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
