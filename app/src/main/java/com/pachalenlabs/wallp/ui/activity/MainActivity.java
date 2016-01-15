package com.pachalenlabs.wallp.ui.activity;

import android.support.v4.app.FragmentActivity;

import com.pachalenlabs.wallp.R;
import com.pachalenlabs.wallp.ui.fragment.InformationFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;

@EActivity(R.layout.activity_act_main)
public class MainActivity extends FragmentActivity {
    @FragmentById
    InformationFragment pictures_;
    @FragmentById
    InformationFragment exchangingPeriods_;

    @AfterViews
    public void setUp_UI(){
        pictures_ = (InformationFragment) getFragmentManager().findFragmentById(R.id.pictures_);
        pictures_.setText("사진 수",4);
        exchangingPeriods_ = (InformationFragment) getFragmentManager().findFragmentById(R.id.exchangingPeriods_);
        exchangingPeriods_.setText("교체 주기",5);
    }



}
