package com.pachalenlabs.wallp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.pachalenlabs.wallp.R;
import com.pachalenlabs.wallp.module.WPCore;
import com.pachalenlabs.wallp.ui.fragment.InformationFragment;
import com.pachalenlabs.wallp.ui.fragment.WallpaperFragment;
import com.pachalenlabs.wallp.ui.fragment.WallpaperSwtichFragment;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

@SuppressLint("Registered")
@EActivity
public class MainActivity extends AppCompatActivity {
    // Logger
    private final static Logger logger = Logger.getLogger(MainActivity.class);

    public static final int PICK_PICTURE = 1;

    @FragmentById(R.id.PictureInformationFragment)
    InformationFragment mPictureInformationFragment;
    @FragmentById(R.id.IntervalFragment)
    InformationFragment mIntervalFragment;
    @FragmentById(R.id.wallpaper_switch_fragment)
    WallpaperSwtichFragment mWallpaperSwitchFragment;
    @FragmentById(R.id.wallpeper_fragment)
    WallpaperFragment mWallpaperFragment;

    String mImageFilePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_PICTURE);
            }
        });
        */
        
        mPictureInformationFragment.setDescription(getResources().getString(R.string.picture_information));
        mPictureInformationFragment.setValue(WPCore.getAppData().getWallpaperUris().size());
        View.OnClickListener PictureOnInformationButtonClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        mPictureInformationFragment.setClickListenerToLayout(PictureOnInformationButtonClick);

        mIntervalFragment.setDescription(getResources().getString(R.string.interval_information));
        mIntervalFragment.setValue(WPCore.getAppData().getTimeGap());
        View.OnClickListener ExchangeOnInformationButtonClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputIntervalDialog();
            }
        };
        mIntervalFragment.setClickListenerToLayout(ExchangeOnInformationButtonClick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                mImageFilePath = getImagePath(uri);
                setBackGround(mImageFilePath);
                mWallpaperFragment.setLodingImageView();
                copyInBackground(mImageFilePath);
            }
        }
    }

    //***************************AsyncTask*******************************************************
    @Background
    protected void copyInBackground(String imagePath) {
        String fileUrl;
        try {
            String fileName = new File(imagePath).getName();
            File sd = Environment.getExternalStorageDirectory();
            fileUrl = sd.getAbsolutePath() + "/WallP/" + fileName;

            FileInputStream inStream = new FileInputStream(imagePath);
            FileOutputStream outStream = new FileOutputStream(sd.getAbsolutePath() + "/WallP/" + fileName);
            FileChannel inChannel = inStream.getChannel();
            FileChannel outChannel = outStream.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inStream.close();
            outStream.close();

            updateImageView(fileUrl);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @UiThread
    protected void updateImageView(String result) {
        mWallpaperFragment.setImageView(result);
        mWallpaperSwitchFragment.addWallpaper(result);
    }

    //**************************파일 복사를 위한 메소드*************************************************
    public String getImagePath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    //************************  핸드폰 배경으로 설정해주는 소스  *****************************************
    public void setBackGround(String imagePath) {
        WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        Bitmap wallPaperImage = BitmapFactory.decodeFile(imagePath);
        try {
            myWallpaperManager.setBitmap(wallPaperImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //**************************뒤로가기 눌려졌을때 ***************************************************
    @Override
    public void onBackPressed() {
        //super.onBackPressed(); //이걸 없애줘야 내가원하는 대로 이벤트 처리가됨
        moveTaskToBack(true);
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    //***************************dialog**********************************************************
    void showInputIntervalDialog() {
        // Set Custom View for dialog
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_interval_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText intervalText = (EditText) promptView.findViewById(R.id.edittext);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        WPCore.getAppData().setTimeGap(Integer.parseInt(intervalText.getText().toString()));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
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
        if (id == R.id.menu_action_add_wallpaper) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
            intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_PICTURE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
