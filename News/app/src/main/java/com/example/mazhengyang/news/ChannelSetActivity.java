package com.example.mazhengyang.news;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.mazhengyang.news.Adapter.ChannelAdapter;
import com.example.mazhengyang.news.Bean.ChannelBean;
import com.example.mazhengyang.news.util.ChannelItemTouchCallback;
import com.example.mazhengyang.news.util.ItemTouchListener;
import com.example.mazhengyang.news.util.Logger;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * Created by mazhengyang on 18-8-28.
 */

public class ChannelSetActivity extends BaseActivity implements ItemTouchListener {

    private final String TAG = ChannelSetActivity.class.getSimpleName();

    @BindView(R.id.rv_selected)
    RecyclerView selectedRecyclerView;
    @BindView(R.id.rv_unselected)
    RecyclerView unselectedRecyclerView;

    private List<ChannelBean.Channel> selectedChannels;
    private List<ChannelBean.Channel> unselectedChannels;

    private ChannelAdapter selectedChannelAdapter;
    private ChannelAdapter unselectedChannelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ChannelBean cb = getChannels(this);
        selectedChannels = cb.getSelectedList();
        unselectedChannels = cb.getUnSelectedList();

        selectedChannelAdapter = new ChannelAdapter(this, selectedChannels, true);
        unselectedChannelAdapter = new ChannelAdapter(this, unselectedChannels,false);
        selectedChannelAdapter.setOnDataChangedListener(new ChannelAdapter.OnDataChangedListener() {
            @Override
            public void onAdd(ChannelBean.Channel channel) {

            }

            @Override
            public void onDelete(ChannelBean.Channel channel) {
                unselectedChannelAdapter.add(channel);
            }
        });
        unselectedChannelAdapter.setOnDataChangedListener(new ChannelAdapter.OnDataChangedListener() {
            @Override
            public void onAdd(ChannelBean.Channel channel) {

            }

            @Override
            public void onDelete(ChannelBean.Channel channel) {
                selectedChannelAdapter.add(channel);
            }
        });


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ChannelItemTouchCallback(this));
        itemTouchHelper.attachToRecyclerView(selectedRecyclerView);

        selectedRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        unselectedRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        selectedRecyclerView.setAdapter(selectedChannelAdapter);
        unselectedRecyclerView.setAdapter(unselectedChannelAdapter);
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

    @Override
    public void onRemove(int position) {

    }

    @Override
    public void onSwap(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(selectedChannelAdapter.getList(), i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(selectedChannelAdapter.getList(), i, i - 1);
            }
        }
        selectedChannelAdapter.notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onBackPressed() {
        if(selectedChannelAdapter.isEditing()){
            selectedChannelAdapter.setEditing(false);
            return;
        }
        super.onBackPressed();
    }
}
