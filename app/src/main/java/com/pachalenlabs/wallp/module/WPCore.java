package com.pachalenlabs.wallp.module;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;

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

    public static int getPixelValue(Context context, int dimenId) {
        Resources resources = context.getResources();
        Log.d("WPCORE", "Metric : " + resources.getDisplayMetrics());
        return new Double(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                resources.getDimension(dimenId),
                resources.getDisplayMetrics()
        )).intValue();
    }

}
