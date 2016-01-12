package com.pachalenlabs.wallp.module;

/**
 * Created by Niklane on 2016-01-12.
 * Class for core function of WallP
 * Implemented by using singleton pattern
 */
public class WPCore {
    private WPCore _WPInstance;

    private WPCore(){}

    public WPCore getWPCore(){
        if(_WPInstance == null){
            _WPInstance = new WPCore();
        }
        return _WPInstance;
    }
}
