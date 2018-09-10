package com.example.mazhengyang.news;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.example.mazhengyang.news.util.Logger;

/**
 * Created by mazhengyang on 18-8-21.
 */

public class NewsApplication extends Application {

    private final String TAG = NewsApplication.class.getSimpleName();

    private static NewsApplication baseApplication;

    private static String APIKEY;

    @Override
    public void onCreate() {
        super.onCreate();
        //androidbootstrap
        TypefaceProvider.registerDefaultIconSets();
        baseApplication = this;

        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(getPackageName(),
                    PackageManager.GET_META_DATA);
            Bundle bundle = applicationInfo.metaData;
            APIKEY = bundle.getString("API_KEY");
            Logger.d(TAG, "onCreate: APIKEY=" + APIKEY);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Context getAppContext() {
        return baseApplication;
    }

    public static String getApikey() {
        return APIKEY;
    }
}
