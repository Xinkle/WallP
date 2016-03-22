package com.pachalenlab.wallp.module;

import android.content.Context;
import android.os.Environment;

import com.pachalenlab.wallp.R;

import org.apache.log4j.Level;

import java.io.File;

import de.mindpipe.android.logging.log4j.LogConfigurator;

/**
 * Created by Niklane on 2016-01-12.
 */
public class WPLogger {
    public static void configure(Context ctx) {
        LogConfigurator configurator = new LogConfigurator();

        // path
        String appName = ctx.getString(R.string.app_name);
        String logPath = Environment.getExternalStorageDirectory() + File.separator + appName;

        // create directory, which directory is not exists
        new File(logPath).mkdirs();

        logPath += File.separator + appName + ".log";

        configurator.setFileName(logPath);
        configurator.setFilePattern("%d - [%p::%C] - %m%n");     // log pattern
        configurator.setMaxFileSize(4 * 1024 * 1024);                 // file size(byte)
        configurator.setMaxBackupSize(10);                        // number of backup file

        configurator.setRootLevel(Level.DEBUG);                  // set log level
        configurator.setUseLogCatAppender(true);                 // and use Logcat

        // set log level of a specific logger
        configurator.setLevel("org.apache", Level.ERROR);
        configurator.configure();
    }
}
