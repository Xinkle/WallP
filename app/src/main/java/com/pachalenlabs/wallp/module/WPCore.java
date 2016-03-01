package com.pachalenlabs.wallp.module;

import android.app.Notification;
import android.app.NotificationManager;
import android.net.Uri;
import android.os.Environment;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Niklane on 2016-01-14.
 */
public class WPCore {
    //Singleton Instance
    private static WPCore ourInstance = new WPCore();
    //Notification
    private static Notification WPNotification;
    //Notification Key
    public static final int NOTIFICATION_KEY = 28913;
    //Notimanager
    private static NotificationManager notiManager;
    public static void setNotiManager(NotificationManager notiManager) {
        WPCore.notiManager = notiManager;
    }
    public static NotificationManager getNotiManager() {
        return notiManager;
    }
    //App Data
    private WPData appData;
    //Datafile Name
    private static final String FILE_NAME = "WallP_Data.json";
    public static WPCore getInstance() {
        return ourInstance;
    }

    private WPCore() {
        appData = new WPData();
        appData.wallpaperUris.add("Sample");
        saveData();
        loadData();
    }

    public void saveData(){
        Gson gson = new Gson();
        String jsonData = gson.toJson(appData);
        WriteTextFile(FILE_NAME, jsonData);
    }

    public void loadData(){
        String jsonData = ReadTextFile(FILE_NAME);
        Gson gson = new Gson();
        appData = gson.fromJson(jsonData, WPData.class);
    }

    public String ReadTextFile(String strFileName) {
        String read_text = null;
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
            e.printStackTrace();
        }

        return read_text;
    }

    public boolean WriteTextFile(String strFileName, String strBuf) {
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WallP/" + strFileName);
            FileOutputStream fos = new FileOutputStream(file);
            Writer out = new OutputStreamWriter(fos, "UTF-8");
            out.write(strBuf);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
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

    private class WPData{
        ArrayList<String> wallpaperUris;

        public WPData(){
            wallpaperUris = new ArrayList<String>();
        }
    }
}
