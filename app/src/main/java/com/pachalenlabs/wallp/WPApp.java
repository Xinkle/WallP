package com.pachalenlabs.wallp;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import com.pachalenlabs.wallp.module.WPCore;
import com.pachalenlabs.wallp.module.WPLogger;
import com.pachalenlabs.wallp.module.WPService;
import com.pachalenlabs.wallp.module.WPService_;

import org.androidannotations.annotations.EApplication;
import org.apache.log4j.Logger;

/**
 * Application class for WallP
 * Created by Niklane on 2016-01-12.
 */
@SuppressLint("Registered")
@EApplication
public class WPApp extends Application {
    private final Logger logger = Logger.getLogger(WPApp.class);

    @Override
    public void onCreate() {
        super.onCreate();
        WPLogger.configure(getApplicationContext());
        WPCore.getInstance();
        WPCore.imageLoaderConfig(this);
        WPCore.getInstance().loadData();
        WPService_.intent(getApplicationContext())
                .extra("runState", WPService.START)
                .start();
        logger.info("App Initialized!!");
    }
}
