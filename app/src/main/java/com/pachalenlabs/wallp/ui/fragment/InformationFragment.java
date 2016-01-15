package com.pachalenlabs.wallp.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.TextView;

import com.pachalenlabs.wallp.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Taeike on 2016-01-15.
 */

@EFragment(R.layout.fragment_information)
public class InformationFragment extends Fragment{

    @ViewById
    TextView textView1_;
    @ViewById
    TextView textView2_;
    @AfterViews
    public void setText(){
        Bundle extra = getArguments();
        String id = extra.getString("title");
        textView1_.setText(id);
    }
}
