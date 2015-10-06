package com.excilys.stadium.network.event;

/**
 * Created by excilys on 16/06/15.
 */
public class GetUserEvent {
    private String url;

    public GetUserEvent(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
