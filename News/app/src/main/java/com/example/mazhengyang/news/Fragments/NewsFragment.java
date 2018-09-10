package com.example.mazhengyang.news.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mazhengyang.news.ChannelSetActivity;
import com.example.mazhengyang.news.Bean.ChannelBean;
import com.example.mazhengyang.news.R;
import com.example.mazhengyang.news.util.Logger;
import com.example.mazhengyang.news.Adapter.NewsFragmentPagerAdapter;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-8-16.
 */

public class NewsFragment extends Fragment implements View.OnClickListener {

    private final String TAG = NewsFragment.class.getSimpleName();

    private List<ChannelBean.Channel> selectChannels;

    @BindView(R.id.tab_layout)
    TabLayout mTablayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.iv_channel_add)
    ImageView mAddBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        Logger.d(TAG, "onCreateView: ");

        View view = inflater.inflate(R.layout.fragment_news, null);
        ButterKnife.bind(this, view);

        selectChannels = getChannels(getContext()).getSelectedList();

        NewsFragmentPagerAdapter adapter = new NewsFragmentPagerAdapter(getChildFragmentManager());
        adapter.setData(selectChannels);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(adapter);

        mTablayout.setupWithViewPager(mViewPager);

        mAddBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d(TAG, "onResume: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d(TAG, "onDestroy: ");
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

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ChannelSetActivity.class);
        startActivity(intent);
    }
}
