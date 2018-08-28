package com.example.mazhengyang.news.NetApi;

import com.example.mazhengyang.news.Bean.NewsBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by mazhengyang on 18-8-16.
 */

public interface NewsApi {

    /**
     * https://api.tianapi.com/social/?key=1a4cc37dc9ba13bc37616a47b61130e6&num=10
     * @return
     */

    @GET("{type}/")
    Observable<NewsBean> getNewsList(
            @Header("Cache-Control") String cacheControl,
            @Path("type") String type,
            @Query("key")String key,
            @Query("num")int num,
            @Query("page")int page);

}
