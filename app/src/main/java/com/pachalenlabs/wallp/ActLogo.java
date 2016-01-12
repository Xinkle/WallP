
package com.pachalenlabs.wallp;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
@Fullscreen
@EActivity(R.layout.activity_act_logo)
public class ActLogo extends Activity {
    Handler ActLogSetDelay_;
    @AfterViews
    void setDelay(){
        ActLogSetDelay_ = new Handler();
        ActLogSetDelay_.postDelayed(mrun, 2000);}
    Runnable mrun = new Runnable(){
        @Override
        public void run() {
            ActMain_.intent(getApplicationContext()).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };
}


