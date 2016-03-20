package com.pachalenlabs.wallp.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pachalenlabs.wallp.R;
import com.pachalenlabs.wallp.module.WPCore;
import com.pachalenlabs.wallp.ui.activity.MainActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.apache.log4j.Logger;

/**
 * Created by Niklane on 2016-01-20.
 */

@EFragment
public class WallpaperSwtichFragment extends Fragment {
    //Logger
    private final Logger logger = Logger.getLogger(WallpaperSwtichFragment.class);
    @ViewById(R.id.wallpaper_scroll_layout)
    LinearLayout mWallpapaerScrollLayout;

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
    void setupView() {
        showAllWallpaper();
    }

    @UiThread
    public void addWallpaper(String filePath) {
        WPCore.getAppData().addWallpaperPath(filePath);
        WPCore.getInstance().saveData();
        showWallpaper(filePath);
        mWallpapaerScrollLayout.requestLayout();
        ((MainActivity)getActivity()).mPictureInformationFragment.setValue(WPCore.getAppData().getWallpaperPaths().size());
    }

    @UiThread
    public void showWallpaper(final String filePath) {
        ImageView imgView = new ImageView(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300, LinearLayout.LayoutParams.MATCH_PARENT, 0.0F);
        int imageMargin = WPCore.getPixelValue(getActivity(), R.dimen.wallpeper_switch_margin);
        Log.d("WallP", "Pixel : " + imageMargin + " Dimen : " + getResources().getDimension(R.dimen.wallpeper_switch_margin));
        params.setMargins(imageMargin, imageMargin, imageMargin, imageMargin);
        imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgView.setLayoutParams(params);

        WPCore.setImageToView(imgView, "file://" + filePath);

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).mWallpaperFragment.setImageView(filePath);
                WPCore.getAppData().setCurrentWallpaper(filePath);
            }
        });
        logger.info("Wallpaper Added!");
        mWallpapaerScrollLayout.addView(imgView);
    }

    @UiThread
    public void showAllWallpaper() {
        for (String filePath : WPCore.getAppData().getWallpaperPaths()) {
            showWallpaper(filePath);
        }
    }

    @UiThread
    public void updateWallpaper(){
        mWallpapaerScrollLayout.removeAllViews();
        showAllWallpaper();
    }

    @UiThread
    public void removeWallpaper(){
        mWallpapaerScrollLayout.removeViewAt(WPCore.getAppData().index);
    }
}
