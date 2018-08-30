package com.example.mazhengyang.news.Animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;

/**
 * Created by mazhengyang on 18-8-22.
 */

public class SlideInRightAnimation implements BaseAnimation{

    @Override
    public ValueAnimator[] getAnimators(View view) {
        return new ValueAnimator[]{
                ObjectAnimator.ofFloat(view, "translationX", view.getRootView().getWidth(), 0)
        };
    }
}
