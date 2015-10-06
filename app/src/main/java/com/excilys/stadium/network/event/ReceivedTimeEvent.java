package com.excilys.stadium.network.event;

/**
 * Created by excilys on 12/06/15.
 */
public class ReceivedTimeEvent {

    private long timeStart;

    public ReceivedTimeEvent(long timeStart) {
        this.timeStart = timeStart;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }
}
