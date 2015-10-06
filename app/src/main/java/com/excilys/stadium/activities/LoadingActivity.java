package com.excilys.stadium.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.excilys.stadium.network.event.GetTimeEvent;
import com.excilys.stadium.network.event.ReceivedTimeEvent;
import com.excilys.stadium.utilities.InternetUtil;
import com.example.excilys.busevent.R;

import java.io.File;
import java.text.ParseException;

import de.greenrobot.event.EventBus;
import timber.log.Timber;

/**
 * Created by excilys on 12/06/15.
 */
public class LoadingActivity extends Activity {
    private static String URL_TIME_START;
    private static String URL_TIME_NTP_SERVER;
    private EventBus bus = EventBus.getDefault();
    private TextView title = null;
    private Typeface type = null;
    private PopupActivity popup = null;
    private InternetUtil internetUtil = null;
    private Intent i = null;

    @Override
    protected void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.chargement);

        type = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue-Thin.otf");
        title = (TextView) findViewById(R.id.chargementStadium);
        title.setTypeface(type);
        popup = new PopupActivity(this);
        internetUtil = new InternetUtil(this);
        URL_TIME_START = getResources().getString(R.string.api_time_start);
        URL_TIME_NTP_SERVER = getResources().getString(R.string.ntp_host);

        File file = new File(getResources().getString(R.string.sdcard_path));
        file.mkdir();

        Thread thread = new ReadFileThread();
        thread.start();
    }

    /*
         the time is save in the service
         and after that we redirect to InscriptionActivity
         because we use the start time in the InscriptionActivity
     */

    public void onEventMainThread(ReceivedTimeEvent event) throws ParseException {
        i = new Intent(LoadingActivity.this, InscriptionActivity.class);
        startActivity(i);
        finish();
    }


    class ReadFileThread extends Thread {
        public void run() {
            File fichier = new File(getResources().getString(R.string.sdcard_image));

            //delete files if they are empty
            boolean exist = fichier.exists();
            fichier.delete();
//            if (fichier.length() == 0) {
//                fichier.delete();
//                exist = false;
//            }

            /*
                Check if files exist
                If yes redirect to the MainActivity
                If not add get the time of launching the video from the api
            */

            if (false) {
                Timber.d("old connection");
                i = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            } else {
                Timber.d("first connection");
                if (!internetUtil.internet()) {
                    popup.displayPopup(getResources().getString(R.string.error_internet), new Redirection());
                }else{
                   bus.post(new GetTimeEvent(URL_TIME_START,URL_TIME_NTP_SERVER));
                }

            }
        }
    }
    /*
        If the user don't have internetUtil
        Ask him to connect his device with PopupActivity.displayPopup()
        And redirect to the same Activity
     */
    class Redirection implements android.content.DialogInterface.OnClickListener{
        public void onClick(DialogInterface dialog, int which) {
            Intent intent = new Intent(LoadingActivity.this, LoadingActivity.class);
            startActivity(intent);
            finish();

        }
    }
}
