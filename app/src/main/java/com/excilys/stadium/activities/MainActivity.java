package com.excilys.stadium.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.excilys.busevent.R;
import com.excilys.stadium.core.Image;
import com.excilys.stadium.utilities.SerializerUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import timber.log.Timber;


public class MainActivity extends Activity {

    private Context context;
    private TextView labelTimer;
    // timer count down
    private Timer timer;
    private TimerTask timerTask;

    // timer display image
    private Timer timerImg;
    private TimerTask timerTaskImg;
    private int i = 0;
    private List<String> colorsHexa = null;
    private int size = 0;

    private Image image = null;
    private long timeRefactoredStartVideo = 0;
    private SerializerUtil<Image> serializeurImage;

    private Boolean booli = false;

    private ArrayList<MyView> views = null;

    private View view1 = null;
    private View view2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setBrightness(100);
        //full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // keep the screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);
        context = this;

        serializeurImage = new SerializerUtil<Image>(getResources().getString(R.string.sdcard_image));
        labelTimer = (TextView) findViewById(R.id.myText);
        /*
        view1 = (View) findViewById(R.id.view1);
        view2 = (View) findViewById(R.id.view2);
*/
        image = serializeurImage.getObject();

        SharedPreferences sharedPref = getSharedPreferences(getResources().getString(R.string.ntp_time), Context.MODE_PRIVATE);
        timeRefactoredStartVideo = sharedPref.getLong(getResources().getString(R.string.ntp_time), 0L);


        colorsHexa = new ArrayList<>();
        for (String pixel : getImageList()) {
            colorsHexa.add('#' + Integer.toHexString(Integer.parseInt(pixel) + 0xFF000000));
        }

        size = getImageList().size();
        views = new ArrayList<MyView>();
        for (int i = 0; i < size; i++) {
            views.add(new MyView(this, colorsHexa.get(i)));
        }
        reScheduleTimer();
    }

    public void reScheduleTimerImg() {
        timerImg = new Timer();
        timerTaskImg = new myTimerTaskImg();
        timerImg.schedule(timerTaskImg, 0, (long) (1000 / image.getFps()));
    }

    public void reScheduleTimer() {
        /*
            Wait the number of milliseconds before each time we start the countdown
            because the timer ticks each second and not each milliseconds
        */

        Date dEnd = new Date(timeRefactoredStartVideo);
        Date dNow = new Date();
        long diff = dEnd.getTime() - dNow.getTime();
        long diffMilliseconds = diff % 1000;
        synchronized (this) {
            try {
                wait(diffMilliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Timber.d("wait synchronized error");
            }
        }
        timer = new Timer();
        timerTask = new myTimerTask();
        timer.schedule(timerTask, 0, 1000);
    }


    private class myTimerTaskImg extends TimerTask {
        @Override
        public void run() {
            updateLabelImg.sendEmptyMessage(0);
        }
    }


    private class myTimerTask extends TimerTask {
        @Override
        public void run() {
            updateLabel.sendEmptyMessage(0);
        }
    }


    public Handler updateLabelImg = new Handler() {

        public void handleMessage(Message msg) {
            //when video finish
            if (i >= size) {
                timerImg.cancel();
                setContentView(R.layout.activity_main);
                labelTimer = (TextView) findViewById(R.id.myText);
                reScheduleTimer();
                booli = false;
                i = 0;
                return;
            }
/*
            if (i % 2 == 0) {
                if(i==0)
                    view1.setBackgroundColor((Color.parseColor(colorsHexa.get(i))));
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.GONE);

                if (i+1 != size)
                    view2.setBackgroundColor(Color.parseColor(colorsHexa.get(i)));


            } else if (i % 2 == 1) {
                view2.setVisibility(View.VISIBLE);
                view1.setVisibility(View.GONE);
                if (i+1 != size)
                    view1.setBackgroundColor(Color.parseColor(colorsHexa.get(i)));
            }
*/
            // set Images of Video
            setContentView(views.get(i));
            i++;

        }
    };

    public Handler updateLabel = new Handler() {
        public void handleMessage(Message msg) {
            String day = "";
            String hours = "";
            String minutes = "";
            String seconds = "";
            Date dEnd = new Date(timeRefactoredStartVideo);
            Date dNow = new Date();
            // Get msec from each, and subtract.
            long diff = dEnd.getTime() - dNow.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDay = diff / (60 * 60 * 1000 * 24);
            if (booli == false && diffSeconds == 0L) {
               // timer.cancel();
                reScheduleTimerImg();
                booli = true;
                return;
            }

            if (diffDay > 0) {
                day = String.valueOf(diffDay) + "j ";
            }
            if (diffHours < 10) {
                hours = "0" + diffHours;
            } else {
                hours = String.valueOf(diffHours);
            }
            if (diffMinutes < 10) {
                minutes = "0" + diffMinutes;
            } else {
                minutes = String.valueOf(diffMinutes);
            }
            if (diffSeconds < 10) {
                seconds = "0" + diffSeconds;
            } else {
                seconds = String.valueOf(diffSeconds);
            }
            String displayTimer = new StringBuilder().append(day).append(hours).append(":").append(minutes).append(":").append(seconds).toString();
            labelTimer.setText(displayTimer);





        }
    };


    public List<String> getImageList() {
        String reponse = image.getPixels();
        //remove first and last character
        reponse = reponse.substring(1, reponse.length() - 1);
        String[] is = reponse.split(",");
        List<String> images = Arrays.asList(is);
        return images;
    }

    private void setBrightness(int brightness) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = brightness / 100.0f;
        getWindow().setAttributes(layoutParams);
    }

    public class MyView extends View {
        private String color;

        public MyView(Context context, String color) {
            super(context);
            this.color = color;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            super.onDraw(canvas);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor(color));
            canvas.drawPaint(paint);
        }
    }
}