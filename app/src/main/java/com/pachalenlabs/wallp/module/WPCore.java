package com.pachalenlabs.wallp.module;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.pachalenlabs.wallp.R;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;

/**
 * BootupReceiver for WallP
 * Created by Niklane on 2016-01-14.
 */
public class WPCore {
    // Logger
    private final Logger logger = Logger.getLogger(WPCore.class);
    // Singleton Instance
    private static WPCore ourInstance = new WPCore();
    // App Data
    private static WPData appData;

    public static WPData getAppData() {
        return ourInstance.appData;
    }

    // Datafile Name
    private static final String FILE_NAME = "WallP_Data.json";

    public static WPCore getInstance() {
        return ourInstance;
    }

    /**
     * intialize Core
     */
    private WPCore() {
        logger.info("Core Created!");
    }

    /**
     * save data to json
     */
    public void saveData() {
        logger.debug("Time : " + ourInstance.appData.getTimeGap() + " Count : " + ourInstance.appData.getWallpaperUris().size());
        Gson gson = new Gson();
        String jsonData = gson.toJson(appData);
        WriteTextFile(FILE_NAME, jsonData);
    }

    /**
     * load data from json file
     */
    public void loadData() {
        String jsonData = ReadTextFile(FILE_NAME);
        Gson gson = new Gson();
        if ("".equals(jsonData)) {
            logger.error("File Unavailable");
            appData = new WPData();
            logger.debug("Time : " + appData.getTimeGap() + " Count : " + appData.getWallpaperUris().size());
            saveData();
        } else {
            logger.info("File Loaded");
            try {
                appData = gson.fromJson(jsonData, WPData.class);
            }catch(JsonSyntaxException e){
                logger.error("File Syntax Corrupted!");
                appData = new WPData();
                saveData();
            }
        }
    }

    /**
     * read text file by stream
     *
     * @param strFileName filename
     * @return text of file
     */
    public String ReadTextFile(String strFileName) {
        String read_text = "";
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WallP/" + strFileName);
            FileInputStream fis = new FileInputStream(file);
            Reader in = new InputStreamReader(fis);
            int size = fis.available();
            char[] buffer = new char[size];
            in.read(buffer);
            in.close();

            read_text = new String(buffer);
        } catch (IOException e) {
            logger.error(e.toString());
        }
        logger.debug(read_text);
        return read_text;
    }

    /**
     * write text to jsonfile
     *
     * @param strFileName filename
     * @param strBuf      string to write
     * @return write sucess or not
     */
    public boolean WriteTextFile(String strFileName, String strBuf) {
        try {
            logger.debug("Write : " + strBuf);
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WallP/" + strFileName);
            FileOutputStream fos = new FileOutputStream(file);
            Writer out = new OutputStreamWriter(fos, "UTF-8");
            out.write(strBuf);
            out.close();
        } catch (IOException e) {
            logger.error(e.toString());
            return false;
        }
        return true;
    }

    public static int getPixelValue(Context context, int dimenId) {
        Resources resources = context.getResources();
        Log.d("WPCORE", "Metric : " + resources.getDisplayMetrics());
        return Double.valueOf(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                resources.getDimension(dimenId),
                resources.getDisplayMetrics()))
                .intValue();
    }

    public static void setImageToView(ImageView imgView, String imgUri) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.copylodingimage) // 로딩중 이미지 설정
                .showImageForEmptyUri(R.color.colorPrimary) // Uri주소가 잘못되었을경우(이미지없을때)
                .showImageOnFail(R.color.colorPrimaryDark) // 로딩 실패시
                // .decodingOptions(resizeOptions)
                .resetViewBeforeLoading(false)  // 로딩전에 뷰를 리셋하는건데 false로 하세요 과부하!
                .cacheInMemory(false) // 메모리케시 사용여부
                .considerExifParams(false) // 사진이미지의 회전률 고려할건지
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT) // 스케일타입설정
                .bitmapConfig(Bitmap.Config.ARGB_8888) // 이미지 컬러방식
                .build();

        setImageToView(imgView, imgUri, options);
    }

    public static void setImageToView(ImageView imgView, String imgUri, DisplayImageOptions imgOptions) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        ImageAware imageAware = new ImageViewAware(imgView, false); //ImageView속성을 따르기 위해서
        imageLoader.displayImage(imgUri, imageAware, imgOptions);
    }

    public static void imageLoaderConfig(Context context) {
        ImageLoaderConfiguration imlConfig;
        imlConfig = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();

        ImageLoader.getInstance().init(imlConfig);
    }

    /**
     * Data Class
     */
    public class WPData {
        // List of Wallpaper
        public ArrayList<Uri> wallpaperUris;

        public ArrayList<Uri> getWallpaperUris() {
            return wallpaperUris;
        }

        public void addWallpaperUri(Uri wallpaperUri) {
            wallpaperUris.add(wallpaperUri);
        }

        // Timegap(Miniute)
        public int timeGap;

        public int getTimeGap() {
            return timeGap;
        }

        public void setTimeGap(int timeGap) {
            this.timeGap = timeGap;
        }

        public WPData() {
            logger.info("File Initialized");
            wallpaperUris = new ArrayList<>();
            timeGap = 10;
        }
    }
}
