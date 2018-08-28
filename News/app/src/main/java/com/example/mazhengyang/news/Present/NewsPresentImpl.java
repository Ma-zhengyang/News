package com.example.mazhengyang.news.Present;

import android.widget.Toast;

import com.example.mazhengyang.news.Model.IDataToPresent;
import com.example.mazhengyang.news.Model.INewsModel;
import com.example.mazhengyang.news.Model.NewsModelImpl;
import com.example.mazhengyang.news.Bean.NewsBean;
import com.example.mazhengyang.news.NewsApplication;
import com.example.mazhengyang.news.View.INewsView;
import com.example.mazhengyang.news.util.Logger;

/**
 * Created by mazhengyang on 18-8-16.
 */

public class NewsPresentImpl implements INewsPresent, IDataToPresent {

    private final String TAG = NewsPresentImpl.class.getSimpleName();

    private INewsModel mNewsModel;
    private INewsView mNewView;

    public NewsPresentImpl(INewsView view) {
        mNewsModel = new NewsModelImpl();
        mNewView = view;
    }

    @Override
    public void loadData(final String type, final int num, final int page) {
        mNewsModel.loadData(type, num, page, this);
    }

    @Override
    public void onSuccess(NewsBean newsBean) {
        Logger.d(TAG, "onSuccess: ");
        mNewView.addNews(newsBean);
    }

    @Override
    public void onFailure(String error) {
        Toast.makeText(NewsApplication.getAppContext(), error,Toast.LENGTH_SHORT).show();
        Logger.d(TAG, "onFailure: " + error);
    }
}
