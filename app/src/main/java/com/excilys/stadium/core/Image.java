package com.excilys.stadium.core;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by excilys on 15/06/15.
 */
public class Image implements Serializable {
    private static final long serialVersionUID = 1L;
    private String pixels;
    private String x;
    private String y;
    private double fps;

    public Image(JSONObject obj) {
        try {
            if (obj.has("pixels")) {
                pixels = obj.getString("pixels");
            }
            if (obj.has("x")) {
                x = obj.getString("x");
            }
            if (obj.has("y")) {
                y = obj.getString("y");
            }
            if (obj.has("fps")) {
                fps = Double.parseDouble(obj.getString("fps"));
            }
        } catch (JSONException e) {
            Timber.e("Err Serialization Video Class : " + e.getMessage());
        }

    }


    public String getPixels() {
        return pixels;
    }

    public void setPixels(String pixels) {
        this.pixels = pixels;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public double getFps() {
        return fps;
    }

    public void setFps(double fps) {
        this.fps = fps;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Image{" +
                "pixels='" + pixels + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", fps='" + fps + '\'' +
                '}';
    }
}
