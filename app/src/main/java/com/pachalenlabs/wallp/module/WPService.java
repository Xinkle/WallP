package com.pachalenlabs.wallp.module;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.pachalenlabs.wallp.R;

import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.UiThread;
import org.apache.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Service for WallP
 * Set wallpaper by interval
 * Created by Niklane on 2016-01-12.
 */


@SuppressLint("Registered")
@EService
public class WPService extends Service {
    private final Logger logger = Logger.getLogger(WPService.class);

    /* Constants for Control rpm service status */
    public static final int INIT = 1;
    public static final int START = 2;
    public static final int STOP = 3;

    /* Service Running Indicator*/
    public static boolean IS_SERVICE_RUNNING = false;

    private Timer mWallpaperTimer;
    private WallpaperTask mWallpaperTask;

    class WallpaperTask extends TimerTask {
        @Override
        public void run() {
            logger.info("Wallpaper Changed!");
        }
    }

    private void startWPService(Intent intent){
        logger.info("Start Service");
        final int INTERVEL_IN_MINIUTE = intent.getIntExtra("wallpaperInterval", 60);

        mWallpaperTimer = new Timer();
        mWallpaperTask = new WallpaperTask();
        mWallpaperTimer.schedule(mWallpaperTask, 0, INTERVEL_IN_MINIUTE * 60 * 1000);

        logger.info("Run Timer, Interval = + " + INTERVEL_IN_MINIUTE + "min");

        IS_SERVICE_RUNNING = true;
    }

    private void stopWPService(){
        logger.info("Stop Service");
        mWallpaperTimer.cancel();

        IS_SERVICE_RUNNING = false;
    }

    private void initWPService(){
        logger.info("Init Service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        logger.info("onStartCommand Start");

        int runStatus = intent.getIntExtra("runStatus", INIT);
        logger.info("Command = " + runStatus);
        switch (runStatus){
            case INIT:
                serviceToast("Service Init");
                initWPService();
                break;
            case START:
                if(!IS_SERVICE_RUNNING){
                    serviceToast("Service ON");
                }
                else{
                    serviceToast("Service Restart");
                    stopWPService();
                }
                startWPService(intent);

                break;
            case STOP:
                serviceToast("Service Stop");
                stopWPService();
                break;
            default:
                serviceToast("Invalid Service Command");
                break;
        }

        logger.info("onStartCommand End");
        /**
         START_STICKY : 재생성과 onStartCommand() 호출(with null intent)
         START_NOT_STICKY : 서비스 재 실행하지 않음
         START_REDELIVER_INTENT : 재생성과 onStartCommand() 호출(with same intent)
         */
        return START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @UiThread
    public void serviceToast(String string){
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        logger.info("onCreate Start");
        serviceToast("WP Service Created!!");
        logger.info("onCreate End");
    }

    @Override
    public void onDestroy() {
        logger.info("onDestroy");
        serviceToast("WP Service Destroyed!!");
        super.onDestroy();
    }
}
