package com.pachalenlabs.wallp.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pachalenlabs.wallp.R;
import com.pachalenlabs.wallp.module.WPCore;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Niklane on 2016-01-15.
 */

@EFragment
public class InformationFragment extends Fragment {
    @ViewById(R.id.informationFrag_description)
    TextView mDescription;
    @ViewById(R.id.informationFrag_value)
    TextView mValues;
    @ViewById(R.id.informationFrag_icon_imageButton)
    ImageButton mInformationIconImageButton;
    @ViewById(R.id.informationFragment_Layout)
    LinearLayout mInformationFragmentLayout;

    View mInflatedView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mInflatedView = inflater.inflate(R.layout.information_fragment, container, false);
        return mInflatedView;
    }

    public void setDescription(String description) {
        this.mDescription.setText(description);
    }

    @UiThread
    public void setValue(final int value){
                mValues.setText(Integer.toString(value));
    }

    public void setClickListenerToLayout(View.OnClickListener action) {
        mInformationFragmentLayout.setOnClickListener(action);
    }
}
