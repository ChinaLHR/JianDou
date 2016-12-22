package com.lhr.jiandou.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by ChinaLHR on 2016/12/22.
 * Email:13435500980@163.com
 */

public class MyScrollView extends HorizontalScrollView{
    public MyScrollView(Context context) {
        this(context,null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
