package com.excilys.stadium.network.event;

/**
 * Created by excilys on 15/06/15.
 */
public class ReceivedImageEvent {
    private String data;

    public ReceivedImageEvent(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
