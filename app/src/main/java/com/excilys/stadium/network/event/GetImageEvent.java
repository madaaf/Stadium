package com.excilys.stadium.network.event;

/**
 * Created by excilys on 16/06/15.
 */
public class GetImageEvent {
    private String url;

    public GetImageEvent(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
