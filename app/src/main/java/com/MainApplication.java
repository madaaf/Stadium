package com;

import android.app.Application;
import android.content.Context;

import com.excilys.stadium.network.service.ImageService;
import com.excilys.stadium.network.service.TimeService;

import de.greenrobot.event.EventBus;
import timber.log.Timber;

/**
 * Created by excilys on 11/06/15.
 */
public class MainApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus bus = EventBus.getDefault();
        context = this;
        TimeService.INSTANCE.setContext(context);
        ImageService.INSTANCE.setContext(context);
        bus.register(TimeService.INSTANCE);
        bus.register(ImageService.INSTANCE);


        Timber.plant(new Timber.DebugTree());
    }

}
