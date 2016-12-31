package com.lhr.jiandou.view.animation;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by ChinaLHR on 2016/12/31.
 * Email:13435500980@163.com
 */

public class DepthPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();
        float scaleFactor = MIN_SCALE
                + (1 - MIN_SCALE) * (1 - Math.abs(position));
        if (position < -1) {
            page.setAlpha(0);
        } else if (position <= 0) {
            page.setAlpha(1);
            page.setTranslationX(0);
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        } else if (position <= 1) { // (0,1]
            page.setAlpha(1 - position);
            page.setTranslationX(pageWidth * -position);
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

        } else {
            page.setAlpha(0);
        }
    }
}
