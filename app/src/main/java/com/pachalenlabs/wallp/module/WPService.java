package com.pachalenlabs.wallp.module;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.androidannotations.annotations.EService;

/**
 * Created by Niklane on 2016-01-12.
 */

@EService
public class WPService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
