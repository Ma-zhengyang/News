package com.example.mazhengyang.news.Animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;

/**
 * Created by mazhengyang on 18-8-22.
 */

public class AlphaInAnimation implements BaseAnimation {
    private static final float DEFAULT_ALPHA_FROM = 0f;
    private final float mFrom;

    public AlphaInAnimation() {
        this(DEFAULT_ALPHA_FROM);
    }

    public AlphaInAnimation(float from) {
        mFrom = from;
    }

    @Override
    public ValueAnimator[] getAnimators(View view) {
        return new ValueAnimator[]{ObjectAnimator.ofFloat(view, "alpha", mFrom, 1f)};
    }
}
