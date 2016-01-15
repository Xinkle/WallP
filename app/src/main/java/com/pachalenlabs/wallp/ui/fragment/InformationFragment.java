package com.pachalenlabs.wallp.ui.fragment;

import android.app.Fragment;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pachalenlabs.wallp.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Niklane on 2016-01-15.
 */

@EFragment(R.layout.fragment_information)
public class InformationFragment extends Fragment{
    @ViewById
    TextView title;
    @ViewById
    TextView values;
    @ViewById
    ImageButton information_image_button;

    public void setTitle(String title){
        this.title.setText(title);}

    public void setValues(int values){
        this.values.setText(String.valueOf(values));}

    @Click
    void information_image_button(){

    }
}
