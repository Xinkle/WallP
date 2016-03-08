package com.pachalenlabs.wallp.ui.fragment;

import android.app.Fragment;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.pachalenlabs.wallp.R;
import com.pachalenlabs.wallp.module.WPCore;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Niklane on 2016-01-15.
 */

@EFragment
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
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.wallpaper_fragment, container, false);
    }

    @AfterViews
    public void setupViews(){
        WPCore.setImageToView(_selectedPhotoImageView, "drawable://"+R.drawable.test_wallpaper);
    }
    public void setLodingImageView(){
        WPCore.setImageToView(_selectedPhotoImageView, "drawable://"+R.drawable.copylodingimage);
    }

    @UiThread
    public void setImageView(String imagePath){
        WPCore.setImageToView(_selectedPhotoImageView, "file://" + imagePath);
    }

    public Uri resourceToUri (int resID) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + getResources().getResourcePackageName(resID)
                + '/' + getResources().getResourceTypeName(resID) + '/' +
                getResources().getResourceEntryName(resID));
        return  imageUri;
    }
    @Click(R.id.cancel_imageButton)
    void leftViewClicked() { Log.d(Tag,"1"); }
    @Click(R.id.right_imageButton)
    void cancelClicked(){
        Log.d(Tag,"2");
    }
    @Click(R.id.left_imageButton)
    void rightViewClicked(){
        Log.d(Tag,"3");
    }
}
