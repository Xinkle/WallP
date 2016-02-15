package com.pachalenlabs.wallp.ui.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.pachalenlabs.wallp.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Niklane on 2016-01-15.
 */

@EFragment(R.layout.wallpaper_fragment)
public class WallpaperFragment extends Fragment{

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
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.test_wallpaper,options);
        //  Bitmap resized = Bitmap.createScaledBitmap( src, _selectedPhotoImageView.getWidth(),_selectedPhotoImageView.getHeight(), true );
        _selectedPhotoImageView.setImageBitmap(src);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.wallpaper_switch_fragment, container, false);
    }

    @Click(R.id.cancel_imageButton)
    void leftViewClicked(){
        Log.d(Tag,"1");
    }
    @Click(R.id.right_imageButton)
    void cancelClicked(){
        Log.d(Tag,"2");
    }
    @Click(R.id.left_imageButton)
    void rightViewClicked(){
        Log.d(Tag,"3");
    }
}
