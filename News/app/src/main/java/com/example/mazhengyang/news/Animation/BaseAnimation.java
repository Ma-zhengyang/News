package com.example.mazhengyang.news.Animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;

/**
 * Created by mazhengyang on 18-8-22.
 */

public interface BaseAnimation {
    ValueAnimator[] getAnimators(View view);
}
