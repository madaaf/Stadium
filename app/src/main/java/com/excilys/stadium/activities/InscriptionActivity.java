package com.excilys.stadium.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.excilys.stadium.network.event.GetImageEvent;
import com.excilys.stadium.network.event.ReceivedImageEvent;
import com.example.excilys.busevent.R;
import com.excilys.stadium.utilities.InternetUtil;

import java.text.ParseException;

import de.greenrobot.event.EventBus;

/**
 * Created by excilys on 12/06/15.
 */
public class InscriptionActivity extends Activity {
    private InternetUtil internetUtil = null;
    private PopupActivity popup = null;
    private static String URL_USER;
    private static String URL_IMAGE;
    private TextView title = null;
    private EditText edit = null;

    private EditText x = null;
    private EditText y = null;

    private Typeface type = null;
    private Button button = null;
    private EventBus bus = EventBus.getDefault();

    @Override
    protected void onResume() {
        super.onResume();
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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.insciptionbis);
        URL_USER = getResources().getString(R.string.api_user);
        URL_IMAGE = getResources().getString(R.string.api_image);

        internetUtil = new InternetUtil(this);
        popup = new PopupActivity(this);
        type = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue-Thin.otf");
        title = (TextView) findViewById(R.id.inscriptionStadium);
        title.setTypeface(type);

        x = (EditText) findViewById(R.id.inscription_x);
        y = (EditText) findViewById(R.id.inscription_y);

        button = (Button) findViewById(R.id.inscription_button);
        button.setOnClickListener(buttonListener);
    }


    /*
        When the user click in the validate button
        Get the data of the video to save it in file
     */
    View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           // String urlImage = new StringBuilder().append(URL_IMAGE).append(x.getText().toString()).append("/").append(y.getText().toString()).append("/vixel.json").toString();
            String urlImage = new StringBuilder().append(URL_IMAGE).append(x.getText().toString()).append("/").append(y.getText().toString()).append("/vixel.json").toString();

            if (!internetUtil.internet()) {
                popup.displayPopup(getResources().getString(R.string.error_internet), new Redirection());
            } else {
                bus.post(new GetImageEvent(urlImage));
            }
        }
    };

    /*
        Intent after saving the image object in file in the Service
     */
    public void onEventMainThread(ReceivedImageEvent event) throws ParseException {
        Intent i = new Intent(InscriptionActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    /*
      If the user don't have internet
      Ask him to connect his device with PopupActivity.displayPopup()
      And redirect to the same Activity
   */
    class Redirection implements android.content.DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Intent intent = new Intent(InscriptionActivity.this, InscriptionActivity.class);
            startActivity(intent);
            finish();

        }
    }
}


