
package com.pachalenlabs.wallp;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.animation.Animation;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.res.AnimationRes;


@Fullscreen
@EActivity(R.layout.activity_act_logo)
public class ActLogo extends Activity {

    Handler delayTime;
    @AnimationRes Animation fadeOut;
    @AnimationRes Animation fadeIn;

    @AfterViews
    void setDelay(){
        delayTime = new Handler();
        delayTime.postDelayed(mrun, 2000); // 딜레이 ( 런어블 객체는 mrun, 시간 2초)
    }
    Runnable mrun = new Runnable(){
        @Override
        public void run() {
            ActMain_.intent(getApplicationContext()).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };
}


