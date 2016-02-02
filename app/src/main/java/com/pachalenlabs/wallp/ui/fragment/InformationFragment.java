package com.pachalenlabs.wallp.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.information_fragment, container, false);
    }

    public void setTitle(String title){this._title.setText(title);}
    public void setValues(int values){this._values.setText(String.valueOf(values));}
    public void setInformationButtonClick(View.OnClickListener action){
        _informationIconImageButton.setOnClickListener(action);}
}//class
