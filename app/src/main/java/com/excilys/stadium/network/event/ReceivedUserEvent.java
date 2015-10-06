package com.excilys.stadium.network.event;

/**
 * Created by excilys on 12/06/15.
 */
public class ReceivedUserEvent {
    private String data;

    public ReceivedUserEvent(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
