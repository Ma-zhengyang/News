package com.example.mazhengyang.news;

import android.app.Application;
import android.content.Context;

import com.beardedhen.androidbootstrap.TypefaceProvider;

/**
 * Created by mazhengyang on 18-8-21.
 */

public class NewsApplication extends Application{

    private static NewsApplication baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        //androidbootstrap
        TypefaceProvider.registerDefaultIconSets();
        baseApplication = this;
    }

    public static Context getAppContext() {
        return baseApplication;
    }
}
