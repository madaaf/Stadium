package com.excilys.stadium.core;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import timber.log.Timber;

/**
 * Created by excilys on 12/06/15.
 */
public class Video implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id = null;
    private String path = null;

    public Video(JSONObject obj) {
        try {
            if (obj.has("id")) {
                id = obj.getString("id");
            }
            if (obj.has("path")) {
                path = obj.getString("path");
            }
        } catch (JSONException e) {
            Timber.e("Err Serialization Video Class : " + e.getMessage());
        }

    }

    public Video(String id,String path) {
        this.id = id;
        this.path = path;

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
