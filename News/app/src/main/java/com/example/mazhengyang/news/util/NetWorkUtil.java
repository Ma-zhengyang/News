package com.example.mazhengyang.news.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by mazhengyang on 18-8-21.
 */

public class NetWorkUtil {

    public static boolean isNetWorkConnected(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info != null){
            return info.isAvailable();
        }
        return false;
    }
}
