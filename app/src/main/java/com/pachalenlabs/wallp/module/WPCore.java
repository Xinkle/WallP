package com.pachalenlabs.wallp.module;

/**
 * Created by Niklane on 2016-01-14.
 */
public class WPCore {
    private static WPCore ourInstance = new WPCore();

    public static WPCore getInstance() {
        return ourInstance;
    }

    private WPCore() {
    }
}
