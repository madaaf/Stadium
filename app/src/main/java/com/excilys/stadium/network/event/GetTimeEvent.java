package com.excilys.stadium.network.event;

/**
 * Created by excilys on 16/06/15.
 */
public class GetTimeEvent {
    private String urlStartVideoTime;
    private String hostNtpServerTime;


    public GetTimeEvent(String urlStartVideoTime,String hostNtpServerTime) {
        this.urlStartVideoTime = urlStartVideoTime;
        this.hostNtpServerTime = hostNtpServerTime;
    }

    public String getUrlStartVideoTime() {
        return urlStartVideoTime;
    }

    public String getHostNtpServerTime() {
        return hostNtpServerTime;
    }


}
