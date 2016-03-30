package com.pachalenlab.wallp.module;

import android.content.Context;
import android.content.Intent;

import org.androidannotations.api.support.content.AbstractBroadcastReceiver;
import org.apache.log4j.Logger;

/**
 * BootupReciever for WallP
 * Receive "android.intent.action.BOOT_COMPLETED" broadcast when boot completed
 * Created by Niklane on 2016-03-12.
 */
public class WPBootUpReceiver extends AbstractBroadcastReceiver {
    private final Logger logger = Logger.getLogger(WPBootUpReceiver.class);

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        logger.info("Boot Up");
        /*
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            logger.info("Boot Received!");
            WPService_.intent(context)
                    .extra("runState", WPService.INIT)
                    .start();


        }
        */
    }
}
