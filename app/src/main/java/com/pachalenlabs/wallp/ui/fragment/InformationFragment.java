package com.pachalenlabs.wallp.ui.fragment;

import android.app.Fragment;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pachalenlabs.wallp.R;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Taeike on 2016-01-15.
 */

@EFragment(R.layout.fragment_information)
public class InformationFragment extends Fragment{
    @ViewById
    TextView textView1_,textView2_;
    @ViewById
    ImageButton informationIcon;

    public void setText(String title,int value){
        textView1_.setText(title);
        textView2_.setText(value);
    }
}
