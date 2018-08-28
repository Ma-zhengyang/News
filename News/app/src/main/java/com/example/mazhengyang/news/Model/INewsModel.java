package com.example.mazhengyang.news.Model;

/**
 * Created by mazhengyang on 18-8-16.
 */

public interface INewsModel {
    void loadData(final String type, final int num, final int page, IDataToPresent listen);
}
