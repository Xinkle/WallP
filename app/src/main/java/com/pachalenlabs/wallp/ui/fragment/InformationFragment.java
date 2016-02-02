package com.pachalenlabs.wallp.ui.fragment;

import android.app.Fragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pachalenlabs.wallp.R;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Niklane on 2016-01-15.
 */

@EFragment
public class InformationFragment extends Fragment{
    @ViewById(R.id.title)
    TextView _title;
    @ViewById(R.id.values)
    TextView _values;
    @ViewById(R.id.information_icon_imageButton)
    ImageButton _informationIconImageButton;
    public void setTitle(String title){this._title.setText(title);}
    public void setValues(int values){this._values.setText(String.valueOf(values));}
    public void setInformationButtonClick(View.OnClickListener action){
        _informationIconImageButton.setOnClickListener(action);}
}//class
