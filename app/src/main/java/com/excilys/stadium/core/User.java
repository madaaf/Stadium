package com.excilys.stadium.core;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by excilys on 12/06/15.
 */
public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id = null;
    private String placeId = null;
    private String video = null;


    public User(JSONObject obj) {
        try {
            if (obj.has("id")) {
                id = obj.getString("id");
            }
            if (obj.has("placeId")) {
                placeId = obj.getString("placeId");
            }
            if (obj.has("video")) {
                video = obj.getString("video");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", placeId='" + placeId + '\'' +
                ", video=" + video +
                '}';
    }
}
