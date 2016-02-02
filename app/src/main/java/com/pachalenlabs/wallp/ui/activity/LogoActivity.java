
package com.pachalenlabs.wallp.ui.activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.pachalenlabs.wallp.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;

@Fullscreen
@EActivity
public class LogoActivity extends Activity {
    Handler _delayHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo_activity_layout);
        _delayHandler = new Handler();
        _delayHandler.postDelayed(mrun, 2000);
    }
    Runnable mrun = new Runnable(){
        @Override
        public void run() {
            MainActivity_.intent(getApplicationContext()).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);}
    };
}


