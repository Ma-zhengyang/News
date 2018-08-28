package com.example.mazhengyang.news.Model;

import com.example.mazhengyang.news.Bean.NewsBean;

/**
 * Created by mazhengyang on 18-8-16.
 */

public interface IDataToPresent {
    void onSuccess(NewsBean newsBean);
    void onFailure(String error);
}
