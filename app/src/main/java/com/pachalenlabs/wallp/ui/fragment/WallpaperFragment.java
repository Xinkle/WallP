package com.pachalenlabs.wallp.ui.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.pachalenlabs.wallp.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;

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
    void setupViews(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 10;
        Bitmap bitmapImage = BitmapFactory.decodeResource(getResources(),R.drawable.test_wallpaper,options);
        Bitmap resized = Bitmap.createScaledBitmap( bitmapImage,500,700, true );
        _selectedPhotoImageView.setImageBitmap(resized);
    }

    public void setImage(Uri imageURI){
        _selectedPhotoImageView.setImageURI(imageURI);
    }
    public void setImage(Bitmap imageBitmap){ _selectedPhotoImageView.setImageBitmap(imageBitmap); }
    public void setImage(Drawable imageDrawable){ _selectedPhotoImageView.setImageDrawable(imageDrawable);}

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
