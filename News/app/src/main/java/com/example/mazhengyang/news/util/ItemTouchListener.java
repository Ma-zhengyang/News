package com.example.mazhengyang.news.util;

/**
 * Created by mazhengyang on 18-8-29.
 */

public interface ItemTouchListener {
    /**
     * 删除时的回调
     */
    void onRemove(int position);

    /**
     * 交换时的回调
     */
    void onSwap(int fromPosition, int toPosition);

}
