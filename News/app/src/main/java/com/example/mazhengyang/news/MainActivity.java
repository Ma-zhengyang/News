package com.example.mazhengyang.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.mazhengyang.news.Fragments.NewsFragment;

/**
 * Created by mazhengyang on 18-8-16.
 */

public class MainActivity extends BaseActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private long mExitTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NewsFragment fragment = new NewsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_content, fragment)
                .show(fragment)
                .commit();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {

        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

}
