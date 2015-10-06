package com.excilys.stadium.network.service;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.excilys.busevent.R;
import com.excilys.stadium.network.RestClient;
import com.excilys.stadium.network.SntpClient;
import com.excilys.stadium.network.event.GetTimeEvent;
import com.excilys.stadium.network.event.ReceivedTimeEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import de.greenrobot.event.EventBus;
import timber.log.Timber;

/**
 * Created by excilys on 16/06/15.
 */
public enum TimeService {
    INSTANCE;

    private SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
    private String timeStart;
    private long timeRefactoredStartVideo;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void onEventAsync(GetTimeEvent event) {

        /*
             Take video  start  time on our API
        */

        String response = null;
        response = RestClient.INSTANCE.executeRequest(event.getUrlStartVideoTime());
        if (response != null) {
            String[] separated = response.split("\\|");
            timeStart = separated[1];
        }


        /*
             Take time on NTP server
        */

        long nowAsPerDeviceTimeZone = 0;
        SntpClient sntpClient = new SntpClient();
        if (sntpClient.requestTime(event.getHostNtpServerTime(), 100000)) {
            nowAsPerDeviceTimeZone = sntpClient.getNtpTime();
            Calendar cal = Calendar.getInstance();
            TimeZone timeZoneInDevice = cal.getTimeZone();
            int differentialOfTimeZones = timeZoneInDevice.getOffset(System.currentTimeMillis());
            nowAsPerDeviceTimeZone -= differentialOfTimeZones;
        }else{
            Timber.e("error connection ntp client ");
        }

        /*
             refactor the start time video with the delay we calculate between the ntp server time & the device time
        */

        Date dateMobile = new Date();
        Date ntpDate = new Date(nowAsPerDeviceTimeZone);


        try {
            timeRefactoredStartVideo = getTimeStartVideo(getDelay(dateMobile, ntpDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // save the refacto video start time in preference
        SharedPreferences sharedPref = context.getSharedPreferences(context.getResources().getString(R.string.ntp_time),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(context.getResources().getString(R.string.ntp_time), timeRefactoredStartVideo);
        editor.commit();


        EventBus.getDefault().post(new ReceivedTimeEvent(timeRefactoredStartVideo));
    }


    public long getDelay(Date mobileDate, Date ntpDate) {
        return mobileDate.getTime() - ntpDate.getTime();
    }

    public long getTimeStartVideo(long delay) throws ParseException {
        return df.parse(timeStart).getTime() + delay;
    }

}
