package com.pachalenlabs.wallp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.pachalenlabs.wallp.R;
import com.pachalenlabs.wallp.ui.fragment.InformationFragment;
import com.pachalenlabs.wallp.ui.fragment.WallpaperSwtichFragment;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;

@EActivity
public class MainActivity extends AppCompatActivity {


    public static final int  PICK_PICTURE  = 1;
    @FragmentById(R.id.PictureInformationFragment)
    InformationFragment _pictureInformationFragment;
    @FragmentById(R.id.ExchangeRatioFragment)
    InformationFragment _exchangeRatioFragment;
    @FragmentById(R.id.wallpaper_switch_fragment)
    WallpaperSwtichFragment _wallpaperSwitchFragment;
    @ViewById(R.id.show_selected_photo_imageView)
    ImageView _selectedImageView;
    String Tag= "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplication(),"dd", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,PICK_PICTURE);
                Bitmap src = BitmapFactory.decodeResource(getResources(),R.drawable.test_wallpaper);
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                */
                _wallpaperSwitchFragment.addWallpaper();
            }
        });
        _pictureInformationFragment = (InformationFragment) getFragmentManager().findFragmentById(R.id.PictureInformationFragment);
        _pictureInformationFragment.setTitle("사진수");
        _pictureInformationFragment.setValues(20);
        View.OnClickListener PictureOnInformationButtonClick = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(Tag, "11");
            }};
        _pictureInformationFragment.setInformationButtonClick(PictureOnInformationButtonClick);

        _exchangeRatioFragment = (InformationFragment) getFragmentManager().findFragmentById(R.id.ExchangeRatioFragment);
        _exchangeRatioFragment.setTitle("교체 주기");
        _exchangeRatioFragment.setValues(30);
        View.OnClickListener ExchangeOnInformationButtonClick = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(Tag, "222222222222222222222222222");
            }};
        _exchangeRatioFragment.setInformationButtonClick(ExchangeOnInformationButtonClick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_PICTURE){
            if(resultCode == Activity.RESULT_OK){
                Uri uri = data.getData();
                _selectedImageView.setImageURI(uri);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
