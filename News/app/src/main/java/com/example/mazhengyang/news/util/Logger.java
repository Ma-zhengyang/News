package com.example.mazhengyang.news.util;

import android.util.Log;

/**
 * Created by mazhengyang on 18-8-20.
 */

public class Logger {

    public static void d(String TAG, String msg) {
        if(msg != null){
            Log.d("News." + TAG, msg);
        }
    }

    public static void e(String TAG, String msg) {
        if(msg != null){
            Log.e("News." + TAG, msg);
        }
    }
}
