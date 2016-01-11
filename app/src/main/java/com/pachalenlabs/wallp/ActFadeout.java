/*
package com.pachalenlabs.wallp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class ActFadeout extends Activity {

    Handler delayTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //인트로화면이므로 타이틀바를 없앤다
        setContentView(R.layout.activity_act_fadeout);

        delayTime= new Handler(); //딜래이를 주기 위해 핸들러 생성
        delayTime.postDelayed(mrun, 2000); // 딜레이 ( 런어블 객체는 mrun, 시간 2초)
    }
    Runnable mrun = new Runnable(){
        @Override
        public void run(){
            Intent i = new Intent(ActFadeout.this, ActMain_.class); //인텐트 생성(현 액티비티, 새로 실행할 액티비티)
            startActivity(i);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            //overridePendingTransition 이란 함수를 이용하여 fade in,out 효과를줌. 순서가 중요
        }
    };

}*/

package com.pachalenlabs.wallp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.UiThread;
import android.view.animation.Animation;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.NoTitle;
import org.androidannotations.annotations.res.AnimationRes;

@NoTitle
@Fullscreen
@EActivity(R.layout.activity_act_fadeout)
public class ActFadeout extends Activity {

    @AnimationRes
    Animation fadeOut;
    @Background
    void doInBackground () {
        setFadeOut();
    }

    @UiThread
    void setFadeOut () {
        Intent i = new Intent(ActFadeout.this, ActMain_.class); //인텐트 생성(현 액티비티, 새로 실행할 액티비티)
        startActivity(i);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        //overridePendingTransition 이란 함수를 이용하여 fade in,out 효과를줌. 순서가 중요
    }
}




