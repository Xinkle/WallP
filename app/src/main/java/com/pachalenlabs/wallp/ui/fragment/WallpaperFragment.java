package com.pachalenlabs.wallp.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.pachalenlabs.wallp.R;
import com.pachalenlabs.wallp.module.WPCore;
import com.pachalenlabs.wallp.ui.activity.MainActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/**
 * WallpaperFragment for WallP
 * Created by Niklane on 2016-01-15.
 */
@EFragment
public class WallpaperFragment extends Fragment {

    String Tag = "WallpaperFragment";

    @ViewById(R.id.cancel_imageButton)
    ImageButton _cancelImageButton;
    @ViewById(R.id.left_imageButton)
    ImageButton _leftImageButton;
    @ViewById(R.id.right_imageButton)
    ImageButton _rightImageButton;
    @ViewById(R.id.show_selected_photo_imageView)
    ImageView _selectedPhotoImageView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.wallpaper_fragment, container, false);
    }

    @AfterViews
    public void setupViews() {
        WPCore.setImageToView(_selectedPhotoImageView, "drawable://" + R.drawable.test_wallpaper);
    }

    public void setLodingImageView() {
        WPCore.setImageToView(_selectedPhotoImageView, "drawable://" + R.drawable.copylodingimage);
    }

    @UiThread
    public void setImageView(String imagePath) {
        WPCore.setImageToView(_selectedPhotoImageView, "file://" + imagePath);
    }

    @Click(R.id.cancel_imageButton)
    void cancelViewClicked() {
        int index = WPCore.getAppData().index;
        WPCore.getAppData().cancelButtonClicked();
        ((MainActivity)getActivity()).mWallpaperSwitchFragment.removeWallpaper(index);
    }

    @Click(R.id.right_imageButton)
    void rightClicked() {
        int index = WPCore.getAppData().index;
        if ( index+1 <WPCore.getAppData().mWallpaperPaths.size()) {
            ((MainActivity) getActivity()).mWallpaperSwitchFragment.removeWallpaper(index);
            ((MainActivity) getActivity()).mWallpaperSwitchFragment.addWallpaper(index+1);
            WPCore.getAppData().rightButtonClicked();
        }
    }

    @Click(R.id.left_imageButton)
    void leftViewClicked(){
        int index = WPCore.getAppData().index;
        if (WPCore.getAppData().index - 1 > -1) {
            ((MainActivity) getActivity()).mWallpaperSwitchFragment.addWallpaper(index-1);
            ((MainActivity) getActivity()).mWallpaperSwitchFragment.removeWallpaper(index+1);
            WPCore.getAppData().leftButtonClicked();
        }
    }
}
