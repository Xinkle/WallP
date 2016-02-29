package com.pachalenlabs.wallp.ui.activity;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.pachalenlabs.wallp.R;
import com.pachalenlabs.wallp.ui.fragment.InformationFragment;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@EActivity
public class MainActivity extends AppCompatActivity {


    public static final int  PICK_PICTURE  = 1;
    @FragmentById(R.id.PictureInformationFragment)
    InformationFragment _pictureInformationFragment;
    @FragmentById(R.id.ExchangeRatioFragment)
    InformationFragment _exchangeRatioFragment;
    @ViewById(R.id.show_selected_photo_imageView)
    ImageView _selectedImageView;
    String _imageFilePath;
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
                _imageFilePath = getImagePath(uri);
                copyFile(_imageFilePath);
                setBackGround(_imageFilePath);
            }
        }
    }
    //**************************파일 복사를 위한 메소드*************************************************
    public String getImagePath(Uri uri){
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }
    public static boolean copyFile(String sourceLocation) {
        try {
            String fileName = new File(sourceLocation).getName();
            File sd = Environment.getExternalStorageDirectory();
            if(sd.canWrite()){
                FileInputStream fis = new FileInputStream(sourceLocation);
                FileOutputStream fos = new FileOutputStream(sd.getAbsolutePath()+"/WallP/"+fileName);
                int data = 0;
                while((data=fis.read())!=-1) fos.write(data);
                fis.close();
                fos.close();
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    //************************  핸드폰 배경으로 설정해주는 소스  *****************************************
    public void setBackGround(String imagePath){
        //바탕화면 관리자 호출
        WallpaperManager myWallpaperManager
                = WallpaperManager.getInstance(getApplicationContext());
        //화면의 크기를 구함
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        Bitmap wallPaperImage = BitmapFactory.decodeFile(imagePath);
        Bitmap resized = Bitmap.createScaledBitmap( wallPaperImage, width, height, true );
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
