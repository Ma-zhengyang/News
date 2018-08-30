package com.example.mazhengyang.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;

/**
 * Created by mazhengyang on 18-8-21.
 */

public class NewsDetailActivity extends BaseActivity {

    @BindView(R.id.iv_image)
    ImageView mImg;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.webView)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(mToolbar);

        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String picurl = i.getStringExtra("picUrl");
        String detailUrl = i.getStringExtra("detailUrl");

        mCollapsingToolbarLayout.setTitle(title);

        Glide.with(this)
                .load(picurl)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_loadfail)
                .into(mImg);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(detailUrl);

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_news_detail;
    }
}
