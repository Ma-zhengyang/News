package com.example.mazhengyang.news.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.mazhengyang.news.Bean.ChannelBean;
import com.example.mazhengyang.news.Fragments.ContentFragment;
import com.example.mazhengyang.news.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mazhengyang on 18-8-28.
 */

public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {

    private final String TAG = NewsFragmentPagerAdapter.class.getSimpleName();

    private FragmentManager fragmentManager;
    private List<ChannelBean.Channel> selectChannels;
    private List<Fragment> mFragments = new ArrayList<>();

    private String mTag;

    public NewsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentManager = fm;
    }

    public void setData(List<ChannelBean.Channel> channelList) {
        selectChannels = channelList;

        Logger.d(TAG, "setData: fill list");

        for (int i = 0; i < channelList.size(); i++) {
            mFragments.add(i, null);
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Fragment fragment = (Fragment) super.instantiateItem(container, position);

        if (fragment instanceof ContentFragment) {
            mTag = fragment.getTag();
            Logger.e(TAG, "mTag=" + mTag);
        }
        return fragment;
    }

    @Override
    public Fragment getItem(int position) {

        Logger.d(TAG, "getItem: position=" + position);
        Logger.d(TAG, "getItem: mFragments.size()=" + mFragments.size());

        if (mFragments.get(position) != null) {
            return mFragments.get(position);
        }

        ContentFragment fragment = new ContentFragment();
        fragment.setTitleType(selectChannels.get(position).getName(),
                selectChannels.get(position).getType());
        mFragments.set(position, fragment);
        Logger.d(TAG, "getItem: new fragment=" + fragment);
        return fragment;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return selectChannels.get(position).getName();
    }

    @Override
    public int getCount() {
        return selectChannels == null ? 0 : selectChannels.size();
    }

}
