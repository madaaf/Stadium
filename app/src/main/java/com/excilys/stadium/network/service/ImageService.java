package com.excilys.stadium.network.service;

import android.content.Context;

import com.example.excilys.busevent.R;
import com.excilys.stadium.core.Image;
import com.excilys.stadium.network.RestClient;
import com.excilys.stadium.network.event.GetImageEvent;
import com.excilys.stadium.network.event.ReceivedImageEvent;
import com.excilys.stadium.utilities.SerializerUtil;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;
import timber.log.Timber;

/**
 * Created by excilys on 16/06/15.
 */
public enum ImageService {
    INSTANCE;
    private Context context;
    private SerializerUtil<Image> serializeurImage = null;

    public void setContext(Context context) {
        this.context = context;
    }

    public void onEventAsync(GetImageEvent event) {
        String response = null;
        response = RestClient.INSTANCE.executeRequest(event.getUrl());
        if (response != null) {
            serializeurImage = new SerializerUtil<Image>(context.getResources().getString(R.string.sdcard_image));
            Image image = null;
            try {
                JSONObject imageResponse = new JSONObject(response);
                image = new Image(imageResponse);
                serializeurImage.setObjet(image);
            } catch (JSONException e) {
                Timber.e(e, "Error parse Image in Json");
            }
            EventBus.getDefault().post(new ReceivedImageEvent(response));
        }
    }
}
