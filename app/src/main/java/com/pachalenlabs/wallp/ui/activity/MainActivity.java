package com.pachalenlabs.wallp.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.pachalenlabs.wallp.R;
import com.pachalenlabs.wallp.ui.fragment.InformationFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;

@EActivity(R.layout.main_activity_layout)
public class MainActivity extends AppCompatActivity {

    @FragmentById(R.id.PictureInformationFragment)
    InformationFragment _pictureInformationFragment;
    @FragmentById(R.id.ExchangeRatioFragment)
    InformationFragment _exchangeRatioFragment;
    @AfterViews
    public void setUp_UI(){
        _pictureInformationFragment = (InformationFragment) getFragmentManager().findFragmentById(R.id.PictureInformationFragment);
        _pictureInformationFragment.setTitle("사진수");
        _pictureInformationFragment.setValues(20);
        View.OnClickListener PictureOnInformationButtonClick = new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }};
        _pictureInformationFragment.setInformationButtonClick(PictureOnInformationButtonClick);

        _exchangeRatioFragment = (InformationFragment) getFragmentManager().findFragmentById(R.id.ExchangeRatioFragment);
        _exchangeRatioFragment.setTitle("교체 주기");
        _exchangeRatioFragment.setValues(30);
        View.OnClickListener ExchangeOnInformationButtonClick = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }};
        _exchangeRatioFragment.setInformationButtonClick(ExchangeOnInformationButtonClick);
    }
}
