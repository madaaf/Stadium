package com.excilys.stadium.network.service;

import com.excilys.stadium.network.RestClient;
import com.excilys.stadium.network.event.GetUserEvent;
import com.excilys.stadium.network.event.ReceivedUserEvent;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import de.greenrobot.event.EventBus;

/**
 * Created by excilys on 16/06/15.
 */
public enum UserService {
    INSTANCE;

    public void onEventAsync(GetUserEvent event){
        String response =  RestClient.INSTANCE.executeRequest(event.getUrl());
        EventBus.getDefault().post(new ReceivedUserEvent(response));

    }
}
