package com.pachalenlabs.wallp.ui.activity;

import android.support.v7.app.AppCompatActivity;

import com.pachalenlabs.wallp.R;
import com.pachalenlabs.wallp.ui.fragment.InformationFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;

@EActivity(R.layout.activity_act_main)
public class MainActivity extends AppCompatActivity {

    @FragmentById
    InformationFragment PictureInformationFragment;
    @FragmentById
    InformationFragment ExchangeRatioFragment;

    @AfterViews
    public void setUp_UI(){
        PictureInformationFragment = (InformationFragment) getFragmentManager().findFragmentById(R.id.PictureInformationFragment);
        PictureInformationFragment.setTitle("사진수");
        PictureInformationFragment.setValues(20);

        ExchangeRatioFragment = (InformationFragment) getFragmentManager().findFragmentById(R.id.ExchangeRatioFragment);
        ExchangeRatioFragment.setTitle("교체 주기");
        ExchangeRatioFragment.setValues(30);
    }
}
