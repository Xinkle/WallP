package com.pachalenlab.wallp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import com.pachalenlab.wallp.R;
import com.pachalenlab.wallp.module.WPCore;
import com.pachalenlab.wallp.module.WPService;
import com.pachalenlab.wallp.module.WPService_;
import com.pachalenlab.wallp.ui.fragment.InformationFragment;
import com.pachalenlab.wallp.ui.fragment.WallpaperFragment;
import com.pachalenlab.wallp.ui.fragment.WallpaperSwtichFragment;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

@SuppressLint("Registered")
@EActivity
public class MainActivity extends AppCompatActivity {
    // Logger
    private final static Logger logger = Logger.getLogger(MainActivity.class);

    public static final int PICK_PICTURE = 1;

    @FragmentById(R.id.picture_information_fragment)
    public InformationFragment mPictureInformationFragment;
    @FragmentById(R.id.interval_information_fragment)
    public InformationFragment mIntervalFragment;
    @FragmentById(R.id.wallpaper_switch_fragment)
    public WallpaperSwtichFragment mWallpaperSwitchFragment;
    @FragmentById(R.id.wallpeper_fragment)
    public WallpaperFragment mWallpaperFragment;

    String mImageFilePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPictureInformationFragment.setDescription(getResources().getString(R.string.picture_information));
        mPictureInformationFragment.setValue(WPCore.getAppData().getWallpaperPaths().size());
        View.OnClickListener PictureOnInformationButtonClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        mPictureInformationFragment.setClickListenerToLayout(PictureOnInformationButtonClick);

        mIntervalFragment.setDescription(getResources().getString(R.string.interval_information));
        mIntervalFragment.setValue(WPCore.getAppData().getTimeInterval());
        View.OnClickListener ExchangeOnInformationButtonClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputIntervalDialog();
            }
        };
        mIntervalFragment.setClickListenerToLayout(ExchangeOnInformationButtonClick);

        // Start Service
        WPService_.intent(getApplicationContext())
                .extra("runState", WPService.START)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                mImageFilePath = getImagePath(uri);
                //setBackGround(mImageFilePath);
                //mWallpaperFragment.setLodingImageView();
                copyInBackground(mImageFilePath);
            }
        }
    }

    //***************************AsyncTask*******************************************************
    @Background
    protected void copyInBackground(String imagePath) {
        String outputFilePath;
        try {
            boolean check = false;
            File sd = Environment.getExternalStorageDirectory();
            check = checkCopyImageFile(sd.getAbsolutePath() + "/WallP", imagePath);
            String fileName = new File(imagePath).getName();
            outputFilePath = sd.getAbsolutePath() + "/WallP/" + fileName;

            if (!check) {
                FileInputStream inStream = new FileInputStream(imagePath);
                FileOutputStream outStream = new FileOutputStream(outputFilePath);
                FileChannel inChannel = inStream.getChannel();
                FileChannel outChannel = outStream.getChannel();
                inChannel.transferTo(0, inChannel.size(), outChannel);
                inStream.close();
                outStream.close();
            }

            updateImageView(outputFilePath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @UiThread
    protected void updateImageView(String result) {
        //mWallpaperFragment.setImageView(result);
        mWallpaperSwitchFragment.addWallpaper(result);
        //mPictureInformationFragment.setValue(WPCore.getAppData().getWallpaperPaths().size());
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

        final EditText intervalText = (EditText) promptView.findViewById(R.id.interval_dialog_input);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        WPCore.getAppData().setTimeInterval(Integer.parseInt(intervalText.getText().toString()));
                        logger.debug("Interval set to " + intervalText.getText().toString());
                        WPCore.getInstance().saveData();
                        mIntervalFragment.setValue(WPCore.getAppData().getTimeInterval());
                        WPService_.intent(getApplicationContext())
                                .extra("runState", WPService.START)
                                .start();
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

    //************************중복된 파일이 있는지 확인을 한담*****************************************
    private boolean checkCopyImageFile(String filePath, String imagePath) {
        try {
            boolean imageNameOverlap = false;
            boolean sizeOverlap = false;
            String filename = new File(imagePath).getName();

            //****************이미지이름 중복검사를 확인한다********************************************
            File file = new File(filePath + "/" + filename);
            imageNameOverlap = file.exists();
            //****************이미지크기를 검사한다***************************************************
            if (imageNameOverlap) {
                int firImageWidthSize = 0, firImageHeightSize = 0;
                int secImageWidthSize = 0, secImageHeightSize = 0;

                Bitmap firImage = BitmapFactory.decodeFile(imagePath);
                firImageWidthSize = firImage.getWidth();
                firImageHeightSize = firImage.getHeight();

                Bitmap secImage = BitmapFactory.decodeFile(filePath + "/" + filename);
                secImageWidthSize = firImage.getWidth();
                secImageHeightSize = firImage.getHeight();

                if (firImageWidthSize == secImageWidthSize && firImageHeightSize == secImageHeightSize)
                    sizeOverlap = true; //사이즈도 같음
                return sizeOverlap && imageNameOverlap;
            }
            return false;
        } catch (Exception e) {
            return false;
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
