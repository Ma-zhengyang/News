package com.example.mazhengyang.news.Model;

import com.example.mazhengyang.news.NetApi.RetrofitService;
import com.example.mazhengyang.news.Bean.NewsBean;
import com.example.mazhengyang.news.util.Logger;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mazhengyang on 18-8-16.
 */

public class NewsModelImpl implements INewsModel {

    private final String TAG = NewsModelImpl.class.getSimpleName();

    @Override
    public void loadData(final String type, final int num, final int page, final IDataToPresent listen) {
        Logger.d(TAG, "loadData: type " + type);

        RetrofitService.Builder().getNewsList(type, num, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Logger.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(NewsBean newsBean) {
                        Logger.d(TAG, "onNext: ");
                        listen.onSuccess(newsBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(TAG, "onError: " + e);

                       String msg = e.toString();

                        if (e != null) {
                            if (e instanceof SocketTimeoutException) {
                                msg = "SocketTimeoutException";
                            } else if (e instanceof UnknownHostException) {
                                msg = "UnknownHostException";
                            }

                        }

                        listen.onFailure(msg);
                    }

                    @Override
                    public void onComplete() {
                        Logger.d(TAG, "onComplete: ");
                    }
                });

    }
}
