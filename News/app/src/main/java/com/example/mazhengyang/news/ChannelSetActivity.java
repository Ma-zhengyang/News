package com.example.mazhengyang.news;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

import com.example.mazhengyang.news.Bean.ChannelBean;
import com.example.mazhengyang.news.util.Logger;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by mazhengyang on 18-8-28.
 */

public class ChannelSetActivity extends BaseActivity{

    private final String TAG = ChannelSetActivity.class.getSimpleName();

    private List<ChannelBean.Channel> selectChannels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectChannels = getChannels(this).getSelectedList();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_channelset;
    }

    private ChannelBean getChannels(Context context) {
        try {
            StringBuilder sb = new StringBuilder();
            AssetManager assetManager = context.getAssets();
            InputStreamReader inputStreamReader = new InputStreamReader(
                    assetManager.open("channels.json"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return new Gson().fromJson(sb.toString(), ChannelBean.class);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.e(TAG, "getChannels: " + e);
        }
        return null;
    }

}
