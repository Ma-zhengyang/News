package com.example.mazhengyang.news.NetApi;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.mazhengyang.news.Bean.NewsBean;
import com.example.mazhengyang.news.NewsApplication;
import com.example.mazhengyang.news.util.Logger;
import com.example.mazhengyang.news.util.NetWorkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by mazhengyang on 18-8-17.
 */

public class RetrofitFactory {

    private static final String TAG = RetrofitFactory.class.getSimpleName();

    public static final String APIKEY = "1a4cc37dc9ba13bc37616a47b61130e6";

    private static final String NEWS_HOST = "https://api.tianapi.com/";

    /**
     * one day
     */
    private static final long CACHE_TIME = 60 * 60 * 24 * 1;

    /**
     * 20M
     */
    private static final long CACHE_SIZE = 1024 * 1024 * 20;


    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_TIME;
    private static final String CACHE_CONTROL_AGE = "max-age=0";

    private static NewsApi newsApi;
    private static RetrofitFactory mRetrofitFactory;

    public static RetrofitFactory Builder() {
        if (mRetrofitFactory == null) {
            synchronized (RetrofitFactory.class) {
                mRetrofitFactory = new RetrofitFactory();
            }
        }

        if (newsApi == null) {

            String external = NewsApplication.getAppContext().getExternalCacheDir().toString();
            String cacheDir = NewsApplication.getAppContext().getCacheDir().toString();
            String dataDir = NewsApplication.getAppContext().getDataDir().toString();

            Logger.d(TAG, "external=" + external);
            Logger.d(TAG, "cacheDir=" + cacheDir);
            Logger.d(TAG, "dataDir=" + dataDir);

            File cacheFile = new File(external, "cache");
            Cache cache = new Cache(cacheFile, CACHE_SIZE);

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(8, TimeUnit.SECONDS)
                    .writeTimeout(8, TimeUnit.SECONDS)
                    .readTimeout(8, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .cache(cache)
                    .addInterceptor(sLoggingInterceptor)
                    .addNetworkInterceptor(sCacheInterceptor);


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(NEWS_HOST)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            newsApi = retrofit.create(NewsApi.class);
        }

        return mRetrofitFactory;
    }

    public static Observable<NewsBean> getNewsList(final String type, final int num, final int page) {
        return newsApi.getNewsList(getCacheControl(), type, APIKEY, num, page);
    }

    @NonNull
    public static String getCacheControl() {
        return NetWorkUtil.isNetWorkConnected(NewsApplication.getAppContext()) ? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE;
    }

    private static final Interceptor sLoggingInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            Logger.d(TAG, String.format("request: %s", request.url()));

            Response response = chain.proceed(request);

            return response;
        }
    };

    private static final Interceptor sCacheInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {

            boolean available = NetWorkUtil.isNetWorkConnected(NewsApplication.getAppContext());
            Logger.d(TAG, "available=" + available);

            Request request = chain.request();
            String cacheControl = request.cacheControl().toString();
            Logger.d(TAG, "cacheControl=" + cacheControl);

            if (!available) {
                request = request.newBuilder()
                        .cacheControl(TextUtils.isEmpty(cacheControl) ? CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE)
                        .build();
                Response response = chain.proceed(request);
                return response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_TIME)
                        .removeHeader("Pragma")
                        .build();
            } else {
                Response response = chain.proceed(request);
                return response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };
}
