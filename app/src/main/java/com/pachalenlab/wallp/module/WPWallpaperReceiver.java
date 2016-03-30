package com.pachalenlab.wallp.module;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.apache.log4j.Logger;


/**
 * Created by niklane on 2016. 3. 28..
 */
public class WPWallpaperReceiver extends BroadcastReceiver {
    private final Logger logger = Logger.getLogger(WPWallpaperReceiver.class);

    public static final int REQ_CODE = 31923;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(WPCore.getAppData().mWallpaperPaths.size() != 0) {
            WPCore.setBackGround("file://" + WPCore.getAppData().getWallpaperPaths()
                            .get(WPCore.getAppData().getNextWallpaper()),context);
            WPCore.getAppData().setNextWallpaper();
            logger.info("Wallpaper Changed!");
        }
    }
}
