package com.pachalenlabs.wallp.ui.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pachalenlabs.wallp.R;
import com.pachalenlabs.wallp.module.WPCore;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;
import org.apache.log4j.Logger;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by Niklane on 2016-01-20.
 */

@EFragment
public class WallpaperSwtichFragment extends Fragment{
    //Logger
    private final Logger logger = Logger.getLogger(WallpaperSwtichFragment.class);
    @ViewById(R.id.wallpaper_scroll_layout)
    LinearLayout _WallpapaerScrollLayout;

    WallpaperFragment _WallpaperFragment;
    ArrayList<Uri> _ImageUris = new ArrayList<Uri>();

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
        _WallpaperFragment = (WallpaperFragment)getFragmentManager().findFragmentById(R.id.wallpeper_fragment);
    }

    public void addWallpaper(Uri uri){
        ImageView imgView = new ImageView(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                0.0F
        );
        int imageMargin = WPCore.getPixelValue(getActivity(), R.dimen.wallpeper_switch_margin);
        Log.d("WallP", "Pixel : " + imageMargin + " Dimen : " + getResources().getDimension(R.dimen.wallpeper_switch_margin));
        params.setMargins(imageMargin,imageMargin,imageMargin,imageMargin);
        imgView.setScaleType(ImageView.ScaleType.FIT_START);
        imgView.setLayoutParams(params);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 10;
        Bitmap bitmapImage = BitmapFactory.decodeResource(getResources(),R.drawable.liver,options);
        Bitmap resized = Bitmap.createScaledBitmap(bitmapImage,500,700, true );
        imgView.setImageBitmap(resized);

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView iv = (ImageView)v;
                _WallpaperFragment.setImage(iv.getDrawable());
            }
        });
        logger.info("Wallpaper Added!");
        _WallpapaerScrollLayout.addView(imgView);
    }

    private void setImageToWallpaperFragment(Uri imageUri){
        _WallpaperFragment.setImage(imageUri);
    }

}
