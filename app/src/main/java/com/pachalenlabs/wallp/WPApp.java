package com.pachalenlabs.wallp;

import android.app.Application;
import android.util.Log;

import com.pachalenlabs.wallp.module.WPLogger;

import org.androidannotations.annotations.EApplication;
import org.apache.log4j.Logger;

/**
 * Created by Niklane on 2016-01-12.
 */

@EApplication
public class WPApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        setLogger();
    }

    void setLogger(){
        try {
            WPLogger.configure(getApplicationContext());

            Logger logger = Logger.getLogger(WPApp_.class);
            logger.info("Initialize Logger");
        } catch (Exception e) {
            Log.e("android-log4j", e.getMessage());
        }
    }
}
