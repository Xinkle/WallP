package com.pachalenlabs.wallp.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.pachalenlabs.wallp.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Niklane on 2016-01-20.
 */

@EFragment
public class WallpaperSwtichFragment extends Fragment{

    @ViewById(R.id.wallpaper_scroll_layout)
    LinearLayout _Wallpapaer_Scroll_Layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.wallpaper_switch_fragment, container, false);
    }

    @AfterViews
    void setupView(){

    }

    public void addWallpaper(){
        Button sampleButton = new Button(getActivity());
        sampleButton.setText("Sample");
        _Wallpapaer_Scroll_Layout.addView(sampleButton);
    }

}
